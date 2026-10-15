package models.response.AiVideoDetection;

/** Overall AI ratios of a video, 0.0 to 1.0. */
public class CopyleaksAiVideoDetectionSummaryModel {

    private double audioAIRatio;

    private double visualAIRatio;

    private double overallAIRatio;

    /** AI-detected audio duration over the total audible duration. */
    public double getAudioAIRatio() {
        return audioAIRatio;
    }

    /** AI-detected visual duration over the total visible duration. */
    public double getVisualAIRatio() {
        return visualAIRatio;
    }

    /** AI ratio across the audio and visual tracks. */
    public double getOverallAIRatio() {
        return overallAIRatio;
    }
}
