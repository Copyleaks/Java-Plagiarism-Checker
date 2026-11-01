package models.submissions.Webhooks.HelperModels.NotificationsModels;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import models.constants.CopyleaksAlertCodes;
import models.response.aidetection.AIDetectionResponse;

public class AlertsModel {

    /**
     * Holds the Gson for additionalData. A nested holder, not a static field of this model, so callers whose
     * Gson does not exclude static fields can still (de)serialize the model.
     */
    private static final class Parser {
        static final Gson GSON = new Gson();
    }

    private int category;

    private String code;
    
    private String title;
    
    private String message;
    
    private String helpLink;
    
    private int severity;
    
    private String additionalData;

    public int getCategory() {
        return category;
    }

    /** Alert code, unique per alert type; see {@link CopyleaksAlertCodes}. */
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

    /** Alert severity, 0 (lowest) to 4. */
    public int getSeverity() {
        return severity;
    }

    /** Extra alert data, as a JSON string. */
    public String getAdditionalData() {
        return additionalData;
    }

    /**
     * Decodes the AI Text Detection result of a {@link CopyleaksAlertCodes#SUSPECTED_AI_TEXT} alert. Trailing NUL and
     * ASCII whitespace are stripped, and the string is parsed on every call. A computed helper, not a wire field:
     * exclude it when re-serializing with a bean-based serializer such as Jackson (Gson is unaffected).
     * @return null when the code differs, {@code additionalData} is empty, or it is JSON but not an object.
     * @throws com.google.gson.JsonSyntaxException if {@code additionalData} is not valid JSON.
     */
    public AIDetectionResponse getAIDetectionResult() {
        if (!CopyleaksAlertCodes.SUSPECTED_AI_TEXT.equals(code) || additionalData == null) {
            return null;
        }
        int end = additionalData.length();
        while (end > 0) {
            char c = additionalData.charAt(end - 1);
            if (c != '\u0000' && c != '\t' && c != '\n' && c != '\u000B' && c != '\f' && c != '\r' && c != ' ') {
                break;
            }
            end--;
        }
        if (end == 0) {
            return null;
        }
        String json = additionalData.substring(0, end);
        try {
            JsonReader reader = new JsonReader(new StringReader(json));
            if (reader.peek() == JsonToken.BEGIN_OBJECT) {
                return Parser.GSON.fromJson(json, AIDetectionResponse.class);
            }
            reader.skipValue();
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Unexpected content after the JSON value in additionalData");
            }
            return null;
        } catch (IOException e) {
            throw new JsonSyntaxException(e);
        }
    }

}
