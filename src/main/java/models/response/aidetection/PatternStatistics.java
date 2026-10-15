package models.response.aidetection;

import java.util.List;

/** Per-pattern statistics of an AI Logic explanation. All lists are aligned with the patterns. */
public class PatternStatistics {
    private List<Double> aiCount;

    private List<Double> humanCount;

    private List<Double> proportion;

    private List<Integer> source;

    /** How often each pattern appears in AI-generated text. */
    public List<Double> getAiCount() {
        return aiCount;
    }

    /** How often each pattern appears in human-written text. */
    public List<Double> getHumanCount() {
        return humanCount;
    }

    /** Ratio of the AI to the human frequency. */
    public List<Double> getProportion() {
        return proportion;
    }

    /** Pattern source: 1 = AI, 2 = humanizer. */
    public List<Integer> getSource() {
        return source;
    }
}
