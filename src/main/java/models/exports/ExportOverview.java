package models.exports;

public class ExportOverview extends ExportOptions {

    public ExportOverview() {}

    public ExportOverview(String endpoint, String verb, String[][] headers) {
        super(endpoint, verb, headers);
    }
}
