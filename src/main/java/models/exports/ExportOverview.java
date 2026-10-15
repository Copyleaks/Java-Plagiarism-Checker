package models.exports;

/** Where to upload the overview report. */
public class ExportOverview extends ExportOptions {

    /** Creates an empty target; set the endpoint and verb with the setters. */
    public ExportOverview() {}

    /** Arguments as in the {@link ExportOptions} setters; headers may be null. */
    public ExportOverview(String endpoint, String verb, String[][] headers) {
        super(endpoint, verb, headers);
    }
}
