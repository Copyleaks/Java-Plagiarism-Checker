package models.response;

public class SupportedFileTypesResponse {
    private String[] textual;
    private String[] ocr;

    /** File types supported for textual scans. */
    public String[] getTextual() {
        return textual;
    }

    /**
     * Misspelled name of {@link #getTextual()}.
     * @deprecated Use {@link #getTextual()}.
     */
    @Deprecated
    public String[] getTextural() {
        return textual;
    }

    public String[] getOcr() {
        return ocr;
    }

}
