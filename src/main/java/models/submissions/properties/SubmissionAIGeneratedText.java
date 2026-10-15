package models.submissions.properties;

public class SubmissionAIGeneratedText {
    private boolean detect;
    private SubmissionExplain explain;
    private int sensitivity;
    
    public SubmissionAIGeneratedText() {
        this.detect = false;
        this.explain = new SubmissionExplain();
        this.sensitivity = 2;
    }
    
    public SubmissionAIGeneratedText(boolean detect, SubmissionExplain explain, int sensitivity) {
        this.detect = detect;
        this.explain = explain;
        setSensitivity(sensitivity); 
    }
    
    public boolean getDetect() {
        return detect;
    }
    
    public void setDetect(boolean detect) {
        this.detect = detect;
    }
    
    public SubmissionExplain getExplain() {
        return explain;
    }
    
    public void setExplain(SubmissionExplain explain) {
        this.explain = explain;
    }
    
    public int getSensitivity() {
        return sensitivity;
    }
    
    public void setSensitivity(int sensitivity) {
        if (sensitivity < 1 || sensitivity > 3) {
            throw new IllegalArgumentException("Sensitivity must be between 1 and 3 (inclusive)");
        }
        this.sensitivity = sensitivity;
    }
}

