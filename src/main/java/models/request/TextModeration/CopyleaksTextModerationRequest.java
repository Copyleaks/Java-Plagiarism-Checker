package models.request.TextModeration;

/** Request body for Text Moderation. */
public class CopyleaksTextModerationRequest {

    /** The most labels the server accepts in one request. */
    public static final int MAX_LABELS = 16;

    private final String text;
    private final boolean sandbox;
    private final String language;
    private final Object[] labels;

    /**
     * @param text     the text to moderate.
     * @param sandbox  return mock results without using credits; null means false.
     * @param language see {@link models.constants.CopyleaksTextModerationLanguages}; must be a supported language.
     *                 Null detects the language.
     * @param labels   the labels to check, 1 to {@value #MAX_LABELS}.
     * @throws IllegalArgumentException if text is null or labels has no items or more than {@value #MAX_LABELS}.
     */
    public CopyleaksTextModerationRequest(
            String text,
            Boolean sandbox,
            String language,
            CopyleaksTextModerationLabel[] labels) {

        if (text == null) {
            throw new IllegalArgumentException("Text field is required!");
        }
        this.text = text;
        this.sandbox = (sandbox != null) ? sandbox : false;
        this.language = language;

        if (labels == null || labels.length == 0) {
            throw new IllegalArgumentException("Labels array must have at least 1 element");
        }
        if (labels.length > MAX_LABELS) {
            throw new IllegalArgumentException("Labels array must not contain more than " + MAX_LABELS + " elements");
        }

        this.labels = labels;
    }

    /** @return the text to moderate. */
    public String getText() {
        return text;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** @return the language code, or null. */
    public String getLanguage() {
        return language;
    }

    /** @return the labels to check. */
    public Object[] getLabels() {
        return labels;
    }
}
