
package example.functions;

import classes.Copyleaks;
import models.response.CopyleaksAuthToken;
import models.submissions.writingassistant.ScoreWeights;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;
import models.response.writingassitant.WritingAssistantResponse;

/** Submits a text to the Grammar Checker and prints the grammar score. */
public class WritingAssistantExample {

    public static void run(CopyleaksAuthToken token, String scanId) {

        String writingFeedbackText = "Lions are the only cat that live in groups, called pride. A prides typically consists of a few adult males, several feales, and their offspring. This social structure is essential for hunting and raising young cubs. Female lions, or lionesses are the primary hunters of the prid. They work together in cordinated groups to take down prey usually targeting large herbiores like zbras, wildebeest and buffalo. Their teamwork and strategy during hunts highlight the intelligence and coperation that are key to their survival.";

        ScoreWeights scoreWeights = new ScoreWeights();
        scoreWeights.setGrammarScoreWeight(0.1);
        scoreWeights.setMechanicsScoreWeight(0.2);
        scoreWeights.setSentenceStructureScoreWeight(0.3);
        scoreWeights.setWordChoiceScoreWeight(0.4);

        CopyleaksWritingAssistantSubmissionModel writingAssistantSubmissionModel = new CopyleaksWritingAssistantSubmissionModel(writingFeedbackText);
        writingAssistantSubmissionModel.setScore(scoreWeights);
        writingAssistantSubmissionModel.setSandbox(true);

        try {
            WritingAssistantResponse writingAssistantResponse = Copyleaks.writingAssistantClient.submitText(token, scanId, writingAssistantSubmissionModel);

            System.out.println("\nText scanned for writing assistant.");
            System.out.println("Grammar Score: " + writingAssistantResponse.getScore().getCorrections().getGrammarCorrectionsScore());
        } catch (Exception e) {
            System.out.println("Error during writing assistant submission: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}

