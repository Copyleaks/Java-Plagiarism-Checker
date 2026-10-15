package models.response.AiImageDetection;

/** Summary statistics of the AI detection analysis. */
public class CopyleaksAiImageDetectionSummaryModel {

    private float human;

    private float ai;

    public CopyleaksAiImageDetectionSummaryModel() {
    }

    public CopyleaksAiImageDetectionSummaryModel(float human, float ai) {
        this.human = human;
        this.ai = ai;
    }

    /** Share of pixels suspected to be human-created, 0.0 to 1.0. */
    public float getHuman() {
        return human;
    }

    public void setHuman(float human) {
        this.human = human;
    }

    /** Share of pixels suspected to be AI-generated, 0.0 to 1.0. */
    public float getAi() {
        return ai;
    }

    public void setAi(float ai) {
        this.ai = ai;
    }
}
