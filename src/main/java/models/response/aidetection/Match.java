package models.response.aidetection;

public class Match {
    /** Positions in the plain text. */
    private Text text;

    /** Positions in the HTML version; null for non-HTML sources. */
    private Text html;

    public Text getText() {
        return text;
    }

    public Text getHtml() {
        return html;
    }
}
