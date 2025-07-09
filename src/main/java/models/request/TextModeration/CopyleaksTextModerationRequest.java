package models.request.TextModeration;

public class CopyleaksTextModerationRequest {
    /**
     * Text to produce Text Moderation report for.
     */
    private final String text;

    /**
     * Use sandbox mode to test your integration with the Copyleaks API
     * without consuming any credits. Default value is false.
     */
    private final boolean sandbox;

    /**
     * The language code of your content. The selected language should be on the Supported Languages list.
     * If not provided, the system will automatically detect the language of the content.
     */
    private final String language;

    /**
     * A list of label configurations to be used for the moderation process.
     * The array must have at least 1 element and at most 32 elements.
     */
    private final Object[] labels;

    public CopyleaksTextModerationRequest(
            String text,
            Boolean sandbox,
            String language,
            Object[] labels) {

        if (text == null) {
            throw new IllegalArgumentException("Text field is required!");
        }
        this.text = text;
        this.sandbox = (sandbox != null) ? sandbox : false;
        this.language = language;

        // Validation for labels
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