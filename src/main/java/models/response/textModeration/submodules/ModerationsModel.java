package models.response.textModeration.submodules;

public class ModerationsModel {

    /** Labelled segments of the submitted text. */
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
