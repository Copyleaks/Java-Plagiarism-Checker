package models.response.textModeration.submodules;

public class ModerationsModel {

    private final Text text; 

    public ModerationsModel( Text text) {

        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null in ModerationsModel");
        }
        this.text = text;
    }

    /** Labelled segments of the submitted text. */
    public Text getText() {
        return text;
    }
}
