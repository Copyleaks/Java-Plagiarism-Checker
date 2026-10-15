package models.response.AiVideoDetection;

/** Overall AI ratios of a video, 0.0 to 1.0. */
public class CopyleaksAiVideoDetectionSummaryModel {

    /** AI-detected audio duration over the total audible duration. */
    private double audioAIRatio;

    /** AI-detected visual duration over the total visible duration. */
    private double visualAIRatio;

    /** Across the audio and visual tracks. */
    private double overallAIRatio;

    public double getAudioAIRatio() {
        return audioAIRatio;
    }

    public double getVisualAIRatio() {
        return visualAIRatio;
    }

    public double getOverallAIRatio() {
        return overallAIRatio;
    }
}
