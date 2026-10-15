package models.response.aidetection;

import java.util.List;

/** Per-pattern statistics of an AI Logic explanation. All lists are aligned with the patterns. */
public class PatternStatistics {
    /** How often each pattern appears in AI-generated text. */
    private List<Double> aiCount;

    /** How often each pattern appears in human-written text. */
    private List<Double> humanCount;

    /** Ratio of the AI to the human frequency. */
    private List<Double> proportion;

    /** 1 = AI, 2 = humanizer. */
    private List<Integer> source;

    public List<Double> getAiCount() {
        return aiCount;
    }

    public List<Double> getHumanCount() {
        return humanCount;
    }

    public List<Double> getProportion() {
        return proportion;
    }

    public List<Integer> getSource() {
        return source;
    }
}
