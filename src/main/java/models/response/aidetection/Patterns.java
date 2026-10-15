package models.response.aidetection;

/** Patterns found by AI Logic, with their statistics and positions. */
public class Patterns {
    private PatternStatistics statistics;

    private Text text;

    private Text html;

    /** Statistics of each pattern, aligned with the pattern positions. */
    public PatternStatistics getStatistics() {
        return statistics;
    }

    /** Positions in the plain text. {@code chars} may be null; {@code words} is always sent. */
    public Text getText() {
        return text;
    }

    /** Positions in the HTML version; null for non-HTML sources. */
    public Text getHtml() {
        return html;
    }
}
