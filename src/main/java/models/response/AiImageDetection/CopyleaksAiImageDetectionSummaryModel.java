package models.response.AiImageDetection;

public class CopyleaksAiImageDetectionSummaryModel {

    /** Share of pixels suspected to be human-created, 0.0 to 1.0. */
    private float human;

    /** Share of pixels suspected to be AI-generated, 0.0 to 1.0. */
    private float ai;

    public CopyleaksAiImageDetectionSummaryModel() {
    }

    public CopyleaksAiImageDetectionSummaryModel(float human, float ai) {
        this.human = human;
        this.ai = ai;
    }

    public float getHuman() {
        return human;
    }

    public void setHuman(float human) {
        this.human = human;
    }

    public float getAi() {
        return ai;
    }

    public void setAi(float ai) {
        this.ai = ai;
    }
}
