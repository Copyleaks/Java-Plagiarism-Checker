package models.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * An error response from the Copyleaks API. Catch it to handle {@link CommandException},
 * {@link RateLimitException} and {@link UnderMaintenanceException} together.
 * <p>The {@code getError...} getters return the fields of the {@code error} object of the response body, parsed once
 * when the exception is created. A field that the body does not have, or that has the wrong JSON type, is null.
 */
public abstract class CopyleaksApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;
    private final String errorType;
    private final String errorId;
    private final Integer errorCode;
    private final String errorMessage;
    private final String errorUrl;
    @SuppressWarnings("serial") // Always an unmodifiable list from java.util.Collections, which is serializable.
    private final List<CopyleaksErrorDetail> errorDetails;

    /**
     * @param message      the exception message.
     * @param statusCode   the HTTP status code of the response, or 0 when not known.
     * @param responseBody the response body text, or null.
     */
    protected CopyleaksApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        JsonObject error = errorObject(responseBody);
        this.errorType = string(error, "type");
        this.errorId = string(error, "id");
        this.errorCode = code(error);
        this.errorMessage = string(error, "message");
        this.errorUrl = string(error, "url");
        this.errorDetails = details(error);
    }

    /** @return the HTTP status code, or 0 when not known. */
    public int getStatusCode() {
        return statusCode;
    }

    /** @return the response body, or null when not known. */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @return {@code error.type}, for example "invalid_request_error" (see
     *         {@link models.constants.CopyleaksErrorTypes}), or null.
     */
    public String getErrorType() {
        return errorType;
    }

    /** @return {@code error.id}, a machine-readable identifier such as "insufficient_credits", or null. */
    public String getErrorId() {
        return errorId;
    }

    /** @return {@code error.code}, sent as a JSON integer or a numeric string, or null. */
    public Integer getErrorCode() {
        return errorCode;
    }

    /** @return {@code error.message}, a human-readable description of the error, or null. */
    public String getErrorMessage() {
        return errorMessage;
    }

    /** @return {@code error.url}, a link to the documentation of the error, or null. */
    public String getErrorUrl() {
        return errorUrl;
    }

    /**
     * @return {@code error.details}, the request parameters that caused the error. An unmodifiable list; empty, never
     *         null, when the response has none.
     */
    public List<CopyleaksErrorDetail> getErrorDetails() {
        // Null only in an exception serialized by an SDK version without this field.
        return errorDetails != null ? errorDetails : Collections.emptyList();
    }

    private static JsonObject errorObject(String body) {
        if (body == null || body.isEmpty()) {
            return null;
        }
        try {
            JsonElement root = JsonParser.parseString(body);
            if (!root.isJsonObject()) {
                return null;
            }
            JsonElement error = root.getAsJsonObject().get("error");
            return error != null && error.isJsonObject() ? error.getAsJsonObject() : null;
        } catch (RuntimeException e) {
            return null; // Not a JSON error envelope. The raw body is still in getResponseBody().
        }
    }

    private static String string(JsonObject object, String name) {
        JsonElement value = object == null ? null : object.get(name);
        return value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()
                ? value.getAsString() : null;
    }

    private static Integer code(JsonObject error) {
        JsonElement value = error == null ? null : error.get("code");
        if (value == null || !value.isJsonPrimitive()
                || !(value.getAsJsonPrimitive().isNumber() || value.getAsJsonPrimitive().isString())) {
            return null;
        }
        try {
            return Integer.valueOf(value.getAsString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<CopyleaksErrorDetail> details(JsonObject error) {
        JsonElement value = error == null ? null : error.get("details");
        if (value == null || !value.isJsonArray()) {
            return Collections.emptyList();
        }
        List<CopyleaksErrorDetail> list = new ArrayList<>();
        for (JsonElement item : value.getAsJsonArray()) {
            if (item.isJsonObject()) {
                JsonObject detail = item.getAsJsonObject();
                list.add(new CopyleaksErrorDetail(string(detail, "param"), string(detail, "message")));
            }
        }
        return Collections.unmodifiableList(list);
    }
}
