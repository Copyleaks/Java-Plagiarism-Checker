package models.request.TextModeration;

public class CopyleaksTextModerationLabel {
    private String id;

    /** @param id a label ID from {@link models.constants.CopyleaksTextModerationConstants}. */
    public CopyleaksTextModerationLabel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}