package models.response.textModeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import models.response.textModeration.submodules.ModerationsModel;
import models.response.textModeration.submodules.TextModerationScannedDocument;
import models.response.textModeration.submodules.TextModerationsLegend;

public class CopyleaksTextModerationResponseModel {
    private final ModerationsModel moderations;
    private final TextModerationsLegend[] legend;
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
