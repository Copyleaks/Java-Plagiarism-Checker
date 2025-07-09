package models.response.textModeration.submodules;

public class Text {
    /**
     * An object that groups together several arrays detailing the properties of labelled segments.
     */
    private final TextModerationChars chars;
    
    public Text(TextModerationChars chars) {

        this.chars = chars;
    }

    public TextModerationChars getChars() {
        return chars;
    }
}
