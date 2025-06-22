package models.response.textModeration.submodules;

import java.util.Arrays;

public class TextModerationChars {
     private final int[] labels;
    private final int[] starts;
    private final int[] lengths;

    public TextModerationChars(int[] labels,int[] starts,int[] lengths) {
        
        // It's good practice to make defensive copies of arrays passed into an immutable object
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
