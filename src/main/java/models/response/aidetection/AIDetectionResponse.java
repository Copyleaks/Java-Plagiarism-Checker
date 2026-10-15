package models.response.aidetection;

import java.util.List;

import models.response.writingassitant.ScannedDocument;

public class AIDetectionResponse {
    private String scanType;

    private String modelVersion;

    /** Classified sections, with their positions. */
    private List<Result> results;

    private Summary summary;

    private ScannedDocument scannedDocument;

    /** Machine-translation provider used before detection; 0 = not translated, null when absent. */
    private Integer translationProvider;

    /** The text translated to English; null unless it was translated. */
    private String translation;

    /** AI Logic explanation; null unless {@code explain} was enabled. */
    private Explain explain;

    public String getScanType() {
        return scanType;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public List<Result> getResults() {
        return results;
    }

    public Summary getSummary() {
        return summary;
    }

    public ScannedDocument getScannedDocument() {
        return scannedDocument;
    }

    public Integer getTranslationProvider() {
        return translationProvider;
    }

    public String getTranslation() {
        return translation;
    }

    public Explain getExplain() {
        return explain;
    }
}
