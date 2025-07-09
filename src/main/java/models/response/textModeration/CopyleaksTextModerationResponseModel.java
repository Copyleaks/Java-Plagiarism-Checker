package models.response.textModeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import models.response.textModeration.submodules.ModerationsModel;
import models.response.textModeration.submodules.TextModerationScannedDocument;
import models.response.textModeration.submodules.TextModerationsLegend;

public class CopyleaksTextModerationResponseModel {
    /**
     * Moderated text segments detected in the input text.
     */
    private final ModerationsModel moderations;

    /**
     * An array that provides a lookup for the labels referenced by their numerical indices in the text.chars.labels array.
     * Each object within this legend array defines a specific label that was used in the scan.
     */
    private final TextModerationsLegend[] legend;

    /**
     * General information about the scanned document.
     */
    private final TextModerationScannedDocument scannedDocument;

    public CopyleaksTextModerationResponseModel(
            ModerationsModel moderations,
            TextModerationsLegend[] legend,
            TextModerationScannedDocument scannedDocument) {

        this.moderations = moderations;
        this.legend = (legend != null) ? Arrays.copyOf(legend, legend.length) : null;
        this.scannedDocument = scannedDocument;
    }

    public ModerationsModel getModerations() {
        return moderations;
    }

    public TextModerationsLegend[] getLegend() {

        return legend;
    }

    public TextModerationScannedDocument getScannedDocument() {
        return scannedDocument;
    }
}
