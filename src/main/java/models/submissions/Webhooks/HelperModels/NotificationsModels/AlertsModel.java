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
package models.submissions.Webhooks.HelperModels.NotificationsModels;

import com.google.gson.Gson;

import models.constants.CopyleaksAlertCodes;
import models.response.aidetection.AIDetectionResponse;

public class AlertsModel {

    /* Parser for additionalData. Static fields are not (de)serialized by Gson. */
    private static final Gson GSON = new Gson();

    /*Scan alert category. */
    private int category;

    /*Scan alert code. The code is unique for each scan alert. */
    private String code;
    
    /*Scan alert human-readable title. */
    private String title;
    
    /*Provides human-readable information about the scan alert. */
    private String message;
    
    /*Url to a resource describing the specific scan alert. */
    private String helpLink;
    
    /*Specifies the importance of the scan alert. */
    private int severity;
    
    /*Additional data about the scan alert. Supplied as a json string. */
    private String additionalData;

    public int getCategory() {
        return category;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getHelpLink() {
        return helpLink;
    }

    public int getSeverity() {
        return severity;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    /**
     * Decodes the AI text detection result carried by a
     * {@link CopyleaksAlertCodes#SUSPECTED_AI_TEXT} alert.
     * <p>
     * The server sends {@code additionalData} as a JSON-encoded string. Trailing NUL
     * characters and whitespace are removed before parsing. The string is parsed on
     * every call. The raw value stays available through {@link #getAdditionalData()}.
     *
     * @return the decoded result, or null when this alert's code is not
     *         {@link CopyleaksAlertCodes#SUSPECTED_AI_TEXT} or {@code additionalData}
     *         is missing or empty.
     * @throws com.google.gson.JsonSyntaxException if {@code additionalData} is not valid JSON.
     */
    public AIDetectionResponse getAIDetectionResult() {
        if (!CopyleaksAlertCodes.SUSPECTED_AI_TEXT.equals(code) || additionalData == null) {
            return null;
        }
        int end = additionalData.length();
        while (end > 0) {
            char c = additionalData.charAt(end - 1);
            if (c != '\0' && !Character.isWhitespace(c)) {
                break;
            }
            end--;
        }
        if (end == 0) {
            return null;
        }
        return GSON.fromJson(additionalData.substring(0, end), AIDetectionResponse.class);
    }

}
