package models.response.aidetection;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Words {
    /** In words. Sandbox scans send the key as {@code Starts}. */
    @SerializedName(value = "starts", alternate = {"Starts"})
    private List<Integer> starts;

    /** In words, aligned with {@code starts}. Sandbox scans send the key as {@code Lengths}. */
    @SerializedName(value = "lengths", alternate = {"Lengths"})
    private List<Integer> lengths;

    /** HTML group IDs, aligned with {@code starts}; null for non-HTML positions. */
    private List<Integer> groupIds;

    public List<Integer> getStarts() {
        return starts;
    }

    public List<Integer> getLengths() {
        return lengths;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }
}
