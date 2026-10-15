package models.response.aidetection;

/** Patterns found by AI Logic, with their statistics and positions. */
public class Patterns {
    /** Aligned with the pattern positions. */
    private PatternStatistics statistics;

    /** Positions in the plain text. {@code chars} may be null; {@code words} is always sent. */
    private Text text;

    /** Positions in the HTML version; null for non-HTML sources. */
    private Text html;

    public PatternStatistics getStatistics() {
        return statistics;
    }

    public Text getText() {
        return text;
    }

    public Text getHtml() {
        return html;
    }
}
