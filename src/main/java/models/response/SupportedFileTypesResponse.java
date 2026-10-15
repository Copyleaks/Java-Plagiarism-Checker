package models.response;

public class SupportedFileTypesResponse {
    private String[] textual;
    private String[] ocr;

    public String[] getTextual() {
        return textual;
    }

    /** @deprecated Misspelled. Use {@link #getTextual()}. */
    @Deprecated
    public String[] getTextural() {
        return textual;
    }

    public String[] getOcr() {
        return ocr;
    }

}
