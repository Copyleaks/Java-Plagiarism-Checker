package models.response.aidetection;

import com.google.gson.annotations.SerializedName;

public class Summary {
    /** Share of human-written text, 0.0 to 1.0. Sandbox scans send the key as {@code Human}. */
    @SerializedName(value = "human", alternate = {"Human"})
    private double human;

    /** Share of AI-generated text, 0.0 to 1.0. Sandbox scans send the key as {@code Ai}. */
    @SerializedName(value = "ai", alternate = {"Ai"})
    private double ai;

    public double getHuman() {
        return human;
    }

    public double getAi() {
        return ai;
    }
}
