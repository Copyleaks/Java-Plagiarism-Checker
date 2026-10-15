package models.response;

/** The file types supported by AI Text Detection. */
public class SupportedAiTextDetectionFileTypesResponse {

    private String[] supportedAiFileTypes;

    /** File extensions, for example "pdf" and "docx". */
    public String[] getSupportedAiFileTypes() {
        return supportedAiFileTypes;
    }
}
