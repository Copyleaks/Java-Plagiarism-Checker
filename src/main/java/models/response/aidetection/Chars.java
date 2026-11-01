package models.response.aidetection;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Chars {
    @SerializedName(value = "starts", alternate = {"Starts"})
    private List<Integer> starts;

    @SerializedName(value = "lengths", alternate = {"Lengths"})
    private List<Integer> lengths;

    private List<Integer> groupIds;

    /** Start of each section, in characters. Sandbox scans send the key as {@code Starts}. */
    public List<Integer> getStarts() {
        return starts;
    }

    /**
     * Length of each section in characters, aligned with {@code starts}.
     * Sandbox scans send the key as {@code Lengths}.
     */
    public List<Integer> getLengths() {
        return lengths;
    }

    /** HTML group IDs, aligned with {@code starts}; null for non-HTML positions. */
    public List<Integer> getGroupIds() {
        return groupIds;
    }
}
