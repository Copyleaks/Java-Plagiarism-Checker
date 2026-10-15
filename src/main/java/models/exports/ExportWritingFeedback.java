package models.exports;

public class ExportWritingFeedback extends ExportOptions {

    public ExportWritingFeedback() {}

    public ExportWritingFeedback(String endpoint, String verb, String[][] headers) {
        super(endpoint, verb, headers);
    }
}
