package models.response.textModeration.submodules;

import java.util.Arrays;

/** Labelled segments as parallel arrays: one entry per segment. */
public class TextModerationChars {
    /** Label index of each segment; resolve it with the response {@code legend}. */
    private final int[] labels;

    /** Start character of each segment. */
    private final int[] starts;

    /** Length in characters. */
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
