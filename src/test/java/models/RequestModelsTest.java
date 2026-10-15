package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import models.constants.CopyleaksAiImageMaskTypes;
import models.request.CopyleaksAsyncWebhooksModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionAsyncRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionBaseRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionFileRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.request.AiImageDetection.CopyleaksObjectDetectionItem;
import models.request.TextModeration.CopyleaksTextModerationLabel;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.submissions.aidetection.CopyleaksAIDetectionSubmissionModel;
import models.submissions.properties.SubmissionAIGeneratedText;
import models.submissions.properties.SubmissionActions;
import models.submissions.properties.SubmissionAssignment;
import models.submissions.properties.SubmissionAuthor;
import models.submissions.properties.SubmissionCourse;
import models.submissions.properties.SubmissionCustomMetadata;
import models.submissions.properties.SubmissionFilter;
import models.submissions.properties.SubmissionFilterDomainsMode;
import models.submissions.properties.SubmissionIndexing;
import models.submissions.properties.SubmissionIndexingRepository;
import models.submissions.properties.SubmissionInstitution;
import models.submissions.properties.SubmissionInternalAiSourceMatch;
import models.submissions.properties.SubmissionMaskingPolicy;
import models.submissions.properties.SubmissionPDF;
import models.submissions.properties.SubmissionPdfColors;
import models.submissions.properties.SubmissionPdfVersion;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionReferences;
import models.submissions.properties.SubmissionScanMethodAlgorithm;
import models.submissions.properties.SubmissionScanning;
import models.submissions.properties.SubmissionScanningExclude;
import models.submissions.properties.SubmissionScanningInclude;
import models.submissions.properties.SubmissionWebhooks;
import models.submissions.properties.SubmissionWritingFeedback;
import models.submissions.writingassistant.ScoreWeights;

/** Checks that request models serialize to the JSON the API documents. */
class RequestModelsTest {

    private static final Gson GSON = new Gson();

    private static JsonElement toJson(Object o) {
        return JsonParser.parseString(GSON.toJson(o));
    }

    private static JsonElement json(String s) {
        return JsonParser.parseString(s);
    }

    private static SubmissionProperties props() {
        return new SubmissionProperties(new SubmissionWebhooks("https://example.com/{STATUS}"));
    }

    // Enums are sent as the documented integers.

    @Test
    void submissionActionsAreIntegers() {
        SubmissionProperties p = props();
        p.setAction(SubmissionActions.IndexOnly);
        assertEquals(json("2"), toJson(p).getAsJsonObject().get("action"));
        p.setAction(SubmissionActions.CheckCredits);
        assertEquals(json("1"), toJson(p).getAsJsonObject().get("action"));
        p.setAction(SubmissionActions.Scan);
        assertEquals(json("0"), toJson(p).getAsJsonObject().get("action"));
    }

    @Test
    void allRequestEnumsAreIntegers() {
        SubmissionProperties p = props();
        p.setScanMethodAlgorithm(SubmissionScanMethodAlgorithm.MaximumResults);
        SubmissionFilter f = new SubmissionFilter();
        f.setDomainsMode(SubmissionFilterDomainsMode.Include);
        p.setFilters(f);
        SubmissionPDF pdf = new SubmissionPDF();
        pdf.setVersion(SubmissionPdfVersion.V3);
        p.setPdf(pdf);
        SubmissionIndexingRepository repo = new SubmissionIndexingRepository();
        repo.setId("r1");
        repo.setMaskingPolicy(SubmissionMaskingPolicy.MaskAllFiles);
        SubmissionIndexing indexing = new SubmissionIndexing();
        indexing.setRepositories(new SubmissionIndexingRepository[] { repo });
        p.setIndexing(indexing);

        JsonElement j = toJson(p);
        assertEquals(json("1"), j.getAsJsonObject().get("scanMethodAlgorithm"));
        assertEquals(json("0"), j.getAsJsonObject().getAsJsonObject("filters").get("domainsMode"));
        assertEquals(json("3"), j.getAsJsonObject().getAsJsonObject("pdf").get("version"));
        assertEquals(json("[{\"maskingPolicy\":2,\"id\":\"r1\"}]"), j.getAsJsonObject().getAsJsonObject("indexing").get("repositories"));
    }

    @Test
    void enumValuesMatchTheApi() {
        assertEquals(0, SubmissionActions.Scan.getValue());
        assertEquals(1, SubmissionActions.CheckCredits.getValue());
        assertEquals(2, SubmissionActions.IndexOnly.getValue());
        assertEquals(0, SubmissionScanMethodAlgorithm.MaximumCoverage.getValue());
        assertEquals(1, SubmissionScanMethodAlgorithm.MaximumResults.getValue());
        assertEquals(0, SubmissionFilterDomainsMode.Include.getValue());
        assertEquals(1, SubmissionFilterDomainsMode.Exclude.getValue());
        assertEquals(0, SubmissionMaskingPolicy.NoMask.getValue());
        assertEquals(1, SubmissionMaskingPolicy.MaskOtherUsersFiles.getValue());
        assertEquals(2, SubmissionMaskingPolicy.MaskAllFiles.getValue());
        assertEquals(1, SubmissionPdfVersion.V1.getValue());
        assertEquals(2, SubmissionPdfVersion.V2.getValue());
        assertEquals(3, SubmissionPdfVersion.V3.getValue());
    }

    @Test
    void enumsReadIntegersAndNames() {
        assertEquals(SubmissionActions.IndexOnly, GSON.fromJson("2", SubmissionActions.class));
        assertEquals(SubmissionActions.IndexOnly, GSON.fromJson("\"IndexOnly\"", SubmissionActions.class));
        assertEquals(SubmissionPdfVersion.V2, GSON.fromJson("\"2\"", SubmissionPdfVersion.class));
        assertNull(GSON.fromJson("null", SubmissionActions.class));
        assertThrows(JsonParseException.class, () -> GSON.fromJson("9", SubmissionActions.class));
    }

    @Test
    void customMetadataIsAnArrayOfKeyValuePairs() {
        SubmissionProperties p = props();
        p.setCustomMetadata(new SubmissionCustomMetadata[] { new SubmissionCustomMetadata("Test1", "Value1"),
                new SubmissionCustomMetadata("Test2", "Value2") });
        assertEquals(json("[{\"key\":\"Test1\",\"value\":\"Value1\"},{\"key\":\"Test2\",\"value\":\"Value2\"}]"),
                toJson(p).getAsJsonObject().get("customMetadata"));
    }

    @Test
    void scoreWeightsDefaultToOne() {
        ScoreWeights w = new ScoreWeights();
        assertEquals(json("{\"grammarScoreWeight\":1.0,\"mechanicsScoreWeight\":1.0,\"sentenceStructureScoreWeight\":1.0,\"wordChoiceScoreWeight\":1.0}"),
                toJson(w));
        w.setWordChoiceScoreWeight(0.25);
        assertEquals(json("{\"grammarScoreWeight\":1.0,\"mechanicsScoreWeight\":1.0,\"sentenceStructureScoreWeight\":1.0,\"wordChoiceScoreWeight\":0.25}"),
                toJson(w));
        assertEquals(json("{\"grammarScoreWeight\":0.1,\"mechanicsScoreWeight\":0.2,\"sentenceStructureScoreWeight\":0.3,\"wordChoiceScoreWeight\":0.4}"),
                toJson(new ScoreWeights(0.1, 0.2, 0.3, 0.4)));
    }

    @Test
    void scanLevelWritingFeedbackUsesTheSameDefaults() {
        SubmissionWritingFeedback wf = new SubmissionWritingFeedback();
        wf.setEnable(true);
        ScoreWeights w = new ScoreWeights();
        w.setGrammarScoreWeight(0.0);
        wf.setScore(w);
        assertEquals(json("{\"enable\":true,\"score\":{\"grammarScoreWeight\":0.0,\"mechanicsScoreWeight\":1.0,\"sentenceStructureScoreWeight\":1.0,\"wordChoiceScoreWeight\":1.0}}"),
                toJson(wf));
    }

    @Test
    void misspelledExperationSetterWritesExpiration() {
        SubmissionProperties p = props();
        p.setExperation(48);
        JsonElement j = toJson(p);
        assertEquals(json("48"), j.getAsJsonObject().get("expiration"));
        assertFalse(j.getAsJsonObject().has("experation"));
        assertEquals(Integer.valueOf(48), p.getExpiration());
    }

    @Test
    void newScanPropertiesAreSent() {
        SubmissionProperties p = props();
        p.setScanTimeZone("America/New_York");
        p.setReferences(new SubmissionReferences(true));
        p.setInternalAiSourceMatch(new SubmissionInternalAiSourceMatch(true));
        p.setAuthor(new SubmissionAuthor("author-1"));
        p.setCourse(new SubmissionCourse("course-1"));
        SubmissionAssignment assignment = new SubmissionAssignment();
        assignment.setId("assignment-1");
        p.setAssignment(assignment);
        p.setInstitution(new SubmissionInstitution("institution-1"));
        SubmissionAIGeneratedText ai = new SubmissionAIGeneratedText();
        ai.setDetect(true);
        p.setAiGeneratedText(ai);

        SubmissionWebhooks hooks = new SubmissionWebhooks("https://example.com/{STATUS}");
        hooks.setNewResult("https://example.com/new");
        hooks.setStatusHeaders(new String[][] { { "a", "1" } });
        hooks.setNewResultHeaders(new String[][] { { "b", "2" } });
        p.setWebhooks(hooks);

        SubmissionScanningExclude exclude = new SubmissionScanningExclude();
        exclude.setIdPattern("abc*");
        exclude.setBacklinksDomains(new String[] { "example.com" });
        exclude.setText(new String[] { "lorem ipsum" });
        SubmissionScanningInclude include = new SubmissionScanningInclude();
        include.setIdPattern("ab..");
        SubmissionScanning scanning = new SubmissionScanning();
        scanning.setExclude(exclude);
        scanning.setInclude(include);
        p.setScanning(scanning);

        SubmissionPdfColors colors = new SubmissionPdfColors();
        colors.setAiDetection("#FE5758");
        colors.setWritingFeedback("#FF9A02");
        SubmissionPDF pdf = new SubmissionPDF();
        pdf.setCreate(true);
        pdf.setReportVersion("latest");
        pdf.setColors(colors);
        p.setPdf(pdf);

        assertEquals(json("{"
                + "\"webhooks\":{\"status\":\"https://example.com/{STATUS}\",\"newResult\":\"https://example.com/new\","
                + "\"statusHeaders\":[[\"a\",\"1\"]],\"newResultHeaders\":[[\"b\",\"2\"]]},"
                + "\"author\":{\"id\":\"author-1\"},\"course\":{\"id\":\"course-1\"},\"assignment\":{\"id\":\"assignment-1\"},"
                + "\"institution\":{\"id\":\"institution-1\"},"
                + "\"scanning\":{\"exclude\":{\"idPattern\":\"abc*\",\"backlinksDomains\":[\"example.com\"],\"text\":[\"lorem ipsum\"]},"
                + "\"include\":{\"idPattern\":\"ab..\"}},"
                + "\"pdf\":{\"create\":true,\"rtl\":false,\"colors\":{\"aiDetection\":\"#FE5758\",\"writingFeedback\":\"#FF9A02\"},\"reportVersion\":\"latest\"},"
                + "\"aiGeneratedText\":{\"detect\":true,\"explain\":{\"enable\":false},\"sensitivity\":2},"
                + "\"scanTimeZone\":\"America/New_York\",\"internalAiSourceMatch\":{\"enable\":true},\"references\":{\"validate\":true}}"),
                toJson(p));
    }

    @Test
    void aiImageOptionsComeFromOneBaseClassWithTheSameJsonKeys() {
        CopyleaksAiImageDetectionBaseRequestModel[] requests = {
            new CopyleaksAiImageDetectionRequestModel("aW1n", "a.png", "m"),
            new CopyleaksAiImageDetectionFileRequestModel(new byte[] { 1 }, "a.png", "m"),
            new CopyleaksAiImageDetectionAsyncRequestModel("https://example.com/a.png", "a.png", "m",
                    new CopyleaksAsyncWebhooksModel("https://example.com/w")) };
        for (CopyleaksAiImageDetectionBaseRequestModel r : requests) {
            assertNull(r.getMaskType());
            assertNull(r.getObjectDetection());
            assertFalse(toJson(r).getAsJsonObject().has("maskType"));
            r.setMaskType(CopyleaksAiImageMaskTypes.BINARY);
            r.setObjectDetection(new CopyleaksObjectDetectionItem[] { new CopyleaksObjectDetectionItem("face") });
            if (r instanceof CopyleaksAiImageDetectionFileRequestModel) {
                continue; // sent as multipart fields, see CopyleaksEndpointsTest
            }
            JsonElement body = toJson(r);
            assertEquals(json("\"binary\""), body.getAsJsonObject().get("maskType"));
            assertEquals(json("[{\"object\":\"face\"}]"), body.getAsJsonObject().get("objectDetection"));
        }
        assertTrue(toJson(requests[0]).getAsJsonObject().has("filename"));
        assertTrue(toJson(requests[2]).getAsJsonObject().has("fileName"));
    }

    @Test
    void aiTextSensitivityIsValidated() {
        CopyleaksAIDetectionSubmissionModel m = new CopyleaksAIDetectionSubmissionModel("t");
        assertThrows(IllegalArgumentException.class, () -> m.setSensitivity(0));
        assertThrows(IllegalArgumentException.class, () -> m.setSensitivity(4));
        m.setSensitivity(1);
        m.setSensitivity(null);
        assertNull(m.getSensitivity());
    }

    @Test
    void textModerationAcceptsUpTo16Labels() {
        CopyleaksTextModerationLabel[] sixteen = labels(16);
        new CopyleaksTextModerationRequest("t", true, "en", sixteen);
        assertThrows(IllegalArgumentException.class, () -> new CopyleaksTextModerationRequest("t", true, "en", labels(17)));
        assertThrows(IllegalArgumentException.class, () -> new CopyleaksTextModerationRequest("t", true, "en", labels(0)));
        assertThrows(IllegalArgumentException.class, () -> new CopyleaksTextModerationRequest("t", true, "en", null));
        assertEquals(16, CopyleaksTextModerationRequest.MAX_LABELS);
    }

    private static CopyleaksTextModerationLabel[] labels(int n) {
        CopyleaksTextModerationLabel[] l = new CopyleaksTextModerationLabel[n];
        for (int i = 0; i < n; i++) {
            l[i] = new CopyleaksTextModerationLabel("label-" + i);
        }
        return l;
    }

    @Test
    void noSpringOrJacksonOnTheClasspath() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("org.springframework.boot.SpringApplication"));
        assertThrows(ClassNotFoundException.class, () -> Class.forName("com.fasterxml.jackson.annotation.JsonProperty"));
    }
}
