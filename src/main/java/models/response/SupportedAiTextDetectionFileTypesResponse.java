package models.response;

public class SupportedAiTextDetectionFileTypesResponse {

    /** File extensions, for example "pdf" and "docx". */
    private String[] supportedAiFileTypes;

    public String[] getSupportedAiFileTypes() {
        return supportedAiFileTypes;
    }
}
