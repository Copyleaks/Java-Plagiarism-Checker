/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package classes.clients;

import java.io.Console;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.google.gson.Gson;

import classes.Consts;
import helpers.CopyleaksClientHelper;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.UnderMaintenanceException;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.response.CopyleaksAuthToken;
import models.response.textModeration.CopyleaksTextModerationResponseModel;

public class TextModerationClient {

    private final HttpClient httpClient;
    private final Semaphore semaphore;
    private static final Gson gson = new Gson();

    public TextModerationClient(HttpClient httpClient, Semaphore semaphore) {
        this.httpClient = httpClient;
        this.semaphore = semaphore;
    }

    public CopyleaksTextModerationResponseModel submitText(CopyleaksAuthToken authToken, String scanId,
            CopyleaksTextModerationRequest submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, CommandException,
            ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert submission != null : "submission is null";

        CopyleaksClientHelper.verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(submission)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v1/text-moderation/" + scanId + "/check"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try {
            response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();

            if (CopyleaksClientHelper.ensureHttpSuccess(statusCode)) {
                CopyleaksTextModerationResponseModel responseModel = gson.fromJson(body,
                        CopyleaksTextModerationResponseModel.class);
                return responseModel;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException(
                        "command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        } finally {
            semaphore.release();
        }
    }
}
