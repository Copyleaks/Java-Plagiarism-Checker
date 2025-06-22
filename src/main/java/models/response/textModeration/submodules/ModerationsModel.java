package models.response.textModeration.submodules;

public class ModerationsModel {
      private final Text text; 

    // Constructor to initialize the 'text' property
    public ModerationsModel( Text text) {

        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null in ModerationsModel");
        }
        this.text = text;
    }

    // Getter for the 'text' property
    public Text getText() {
        return text;
    }
}
