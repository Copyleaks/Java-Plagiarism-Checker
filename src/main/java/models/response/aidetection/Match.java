package models.response.aidetection;

public class Match {
    private Text text;

    private Text html;

    /** Positions in the plain text. */
    public Text getText() {
        return text;
    }

    /** Positions in the HTML version; null for non-HTML sources. */
    public Text getHtml() {
        return html;
    }
}
