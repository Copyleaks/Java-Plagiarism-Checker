package models.request.TextModeration;

import java.util.List;

public class CopyleaksTextModerationRequest {

    private final String text; // Text to produce Text Moderation report for.
    private final boolean sandbox; // default value is set to false
    private final String language; // if not provided then our system will automatically detect the language of the
                                   // content.
    private final Object[] labels;

    public CopyleaksTextModerationRequest(
            String text,
            Boolean sandbox,
            String language,
            Object[] labels) {

        this.text = text;
        this.sandbox = (sandbox != null) ? sandbox : false; // Handle default value for sandbox
        this.language = language;

        // Validation for labels, similar to the MinLength attribute in C#
        if (labels == null) {
            throw new IllegalArgumentException("Labels array must have at least 1 element");
        }
        this.labels = labels;
    }

    public String getText() {
        return text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public String getLanguage() {
        return language;
    }

    public Object[] getLabels() {
        return labels;
    }
}