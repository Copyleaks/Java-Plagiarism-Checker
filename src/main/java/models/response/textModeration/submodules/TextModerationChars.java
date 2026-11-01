package models.response.textModeration.submodules;

import java.util.Arrays;

/** Labelled segments as parallel arrays: one entry per segment. */
public class TextModerationChars {
    private final int[] labels;

    private final int[] starts;

    private final int[] lengths;

    public TextModerationChars(int[] labels,int[] starts,int[] lengths) {
        
        this.labels = (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
        this.starts = (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
        this.lengths = (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }

    /** Label index of each segment; resolve it with the response {@code legend}. */
    public int[] getLabels() {
        
        return (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
    }

    /** Start character of each segment. */
    public int[] getStarts() {
        
        return (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
    }

    /** Length of each segment in characters. */
    public int[] getLengths() {
        
        return (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }
}
