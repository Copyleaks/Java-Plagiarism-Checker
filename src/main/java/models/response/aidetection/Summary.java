package models.response.aidetection;

import com.google.gson.annotations.SerializedName;

public class Summary {
    @SerializedName(value = "human", alternate = {"Human"})
    private double human;

    @SerializedName(value = "ai", alternate = {"Ai"})
    private double ai;

    /** Share of human-written text, 0.0 to 1.0. Sandbox scans send the key as {@code Human}. */
    public double getHuman() {
        return human;
    }

    /** Share of AI-generated text, 0.0 to 1.0. Sandbox scans send the key as {@code Ai}. */
    public double getAi() {
        return ai;
    }
}
