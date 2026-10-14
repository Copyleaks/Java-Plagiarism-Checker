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

package helpers;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

import models.exceptions.AuthExpiredException;
import models.response.CopyleaksAuthToken;

public class CopyleaksClientHelper {

    /** A token is treated as expired when less than this many seconds of validity are left. */
    private static final long EXPIRY_MARGIN_SECONDS = 5 * 60;

    /** The last parsed expiry date, so the same token is not parsed on every call. */
    private static volatile ParsedExpiry lastExpiry;

    private static final class ParsedExpiry {
        final String text;
        final Instant instant;

        ParsedExpiry(String text, Instant instant) {
            this.text = text;
            this.instant = instant;
        }
    }

    /**
     * Verify that Copyleaks authentication token is exists and not expired.
     * A token that expires within the next 5 minutes is treated as expired.
     *
     * @param authToken Copyleaks authentication token
     * @throws AuthExpiredException if the token has expired. Log in again to get a new one.
     */
    public static void verifyAuthToken(CopyleaksAuthToken authToken) throws AuthExpiredException {
        Objects.requireNonNull(authToken, "authToken is null");
        String expires = authToken.getExpires();
        Objects.requireNonNull(expires, "authToken has no expiry date");
        ParsedExpiry cached = lastExpiry;
        Instant expireDate;
        if (cached != null && cached.text.equals(expires)) {
            expireDate = cached.instant;
        } else {
            expireDate = Instant.from(ZonedDateTime.parse(expires));
            lastExpiry = new ParsedExpiry(expires, expireDate);
        }
        if (!expireDate.isAfter(Instant.now().plusSeconds(EXPIRY_MARGIN_SECONDS))) {
            throw new AuthExpiredException();
        }
    }

    /**
     * @param statusCode an HTTP status code.
     * @return true for a 2xx status code.
     */
    public static boolean ensureHttpSuccess(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }
}
