package models.response.textModeration.submodules;

public class Text {
    private final TextModerationChars chars;

    public Text(TextModerationChars chars) {

        this.chars = chars;
    }

    public TextModerationChars getChars() {
        return chars;
    }
}
