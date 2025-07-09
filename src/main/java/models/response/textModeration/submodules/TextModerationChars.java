package models.response.textModeration.submodules;

import java.util.Arrays;

public class TextModerationChars {
    /**
     * Predicted label index for the corresponding segment.
     * The index can be resolved to its ID using the supplied legend.
     */
    private final int[] labels;

    /**
     * Start character position of the labelled segment.
     */
    private final int[] starts;

    /**
     * Labelled segment character length.
     */
    private final int[] lengths;

    public TextModerationChars(int[] labels,int[] starts,int[] lengths) {
        
        this.labels = (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
        this.starts = (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
        this.lengths = (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }

    public int[] getLabels() {
        
        return (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
    }

    public int[] getStarts() {
        
        return (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
    }

    public int[] getLengths() {
        
        return (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }
}
