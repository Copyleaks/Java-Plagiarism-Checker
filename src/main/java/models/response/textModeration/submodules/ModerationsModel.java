package models.response.textModeration.submodules;

public class ModerationsModel {

    /**
     * Moderated text segments corresponding to the submitted text. Each position in the inner arrays corresponds to a single segment in the textual version
     */
    private final Text text; 

    public ModerationsModel( Text text) {

        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null in ModerationsModel");
        }
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
