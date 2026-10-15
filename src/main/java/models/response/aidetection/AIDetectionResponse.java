package models.response.aidetection;

import java.util.List;

import models.response.writingassitant.ScannedDocument;

public class AIDetectionResponse {
    private String scanType;

    private String modelVersion;

    private List<Result> results;

    private Summary summary;

    private ScannedDocument scannedDocument;

    private Integer translationProvider;

    private String translation;

    private Explain explain;

    public String getScanType() {
        return scanType;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    /** Classified sections, with their positions. */
    public List<Result> getResults() {
        return results;
    }

    public Summary getSummary() {
        return summary;
    }

    public ScannedDocument getScannedDocument() {
        return scannedDocument;
    }

    /** Machine-translation provider used before detection; 0 = not translated, null when absent. */
    public Integer getTranslationProvider() {
        return translationProvider;
    }

    /** The text translated to English; null unless it was translated. */
    public String getTranslation() {
        return translation;
    }

    /** AI Logic explanation; null unless {@code explain} was enabled. */
    public Explain getExplain() {
        return explain;
    }
}
