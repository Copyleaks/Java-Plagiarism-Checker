package models;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import models.response.AiVideoDetection.CopyleaksAiVideoDetectionResponseModel;
import models.response.aidetection.AIDetectionResponse;
import models.submissions.Webhooks.CompletedWebhookModel;
import models.submissions.Webhooks.CreditsCheckedWebhookModel;
import models.submissions.Webhooks.ErrorWebhookModel;
import models.submissions.Webhooks.ExportCompletedWebhookModel;
import models.submissions.Webhooks.NewResultWebhookModel;
import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.InternalAIDataModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.RepositoryMetadataModel;

/**
 * Checks that responses and webhooks keep every documented field.
 */
class ResponseModelsTest {

    private static final Gson GSON = new Gson();

    private static final String METADATA = "{\"finalUrl\":\"https://example.com/f\",\"author\":\"Ann\",\"filename\":\"a.docx\",\"submittedBy\":\"user@example.com\"}";

    @Test
    void repositoryResultMetadataNoLongerThrowsClassCastException() {
        String completed = "{\"status\":0,\"results\":{"
                + "\"repositories\":[{\"id\":\"r1\",\"repositoryId\":\"repo\",\"matchedWords\":3,\"metadata\":" + METADATA + "}],"
                + "\"internet\":[{\"id\":\"i1\",\"url\":\"https://example.com\",\"metadata\":{\"finalUrl\":\"https://example.com/x\"}}],"
                + "\"database\":[{\"id\":\"d1\",\"scanId\":\"s\",\"metadata\":{\"author\":\"Bob\"}}],"
                + "\"batch\":[{\"id\":\"b1\"}]}}";
        CompletedWebhookModel m = GSON.fromJson(completed, CompletedWebhookModel.class);
        RepositoryMetadataModel meta = m.getResults().getRepositories()[0].getMetadata();
        assertEquals("user@example.com", meta.getSubmittedBy());
        assertEquals("Ann", meta.getAuthor());
        assertEquals("a.docx", meta.getFilename());
        assertEquals("repo", m.getResults().getRepositories()[0].getRepositoryId());
        assertEquals("https://example.com/x", m.getResults().getInternet()[0].getMetadata().getFinalUrl());
        assertEquals("Bob", m.getResults().getDatabase()[0].getMetadata().getAuthor());
        assertNull(m.getResults().getBatch()[0].getMetadata());

        String newResult = "{\"developerPayload\":\"dp\",\"score\":{\"aggregatedScore\":12.5},"
                + "\"repositories\":[{\"id\":\"r2\",\"repositoryId\":\"repo2\",\"metadata\":" + METADATA + "}],"
                + "\"database\":[{\"id\":\"d2\",\"metadata\":{\"author\":\"Cy\"}}]}";
        NewResultWebhookModel n = GSON.fromJson(newResult, NewResultWebhookModel.class);
        assertEquals("user@example.com", n.getRepositories()[0].getMetadata().getSubmittedBy());
        assertEquals("Cy", n.getDatabase()[0].getMetadata().getAuthor());
        assertEquals("dp", n.getDeveloperPayload());
    }

    @Test
    void errorWebhookKeepsTheWholeErrorObject() {
        String body = "{\"status\":1,\"developerPayload\":\"p\",\"error\":{\"type\":\"invalid_request_error\",\"id\":\"missing_parameter\","
                + "\"code\":1,\"message\":\"Bad request.\",\"url\":\"https://docs.copyleaks.com/using-the-apis/api-errors\","
                + "\"details\":[{\"param\":\"filename\",\"message\":\"File Type not Supported\"}]}}";
        ErrorWebhookModel m = GSON.fromJson(body, ErrorWebhookModel.class);
        ErrorModel e = m.getError();
        assertEquals(1, m.getStatus());
        assertEquals("p", m.getDeveloperPayload());
        assertEquals("invalid_request_error", e.getType());
        assertEquals("missing_parameter", e.getId());
        assertEquals(1, e.getCode());
        assertEquals("Bad request.", e.getMessage());
        assertEquals("https://docs.copyleaks.com/using-the-apis/api-errors", e.getUrl());
        assertEquals("filename", e.getDetails()[0].getParam());
        assertEquals("File Type not Supported", e.getDetails()[0].getMessage());
    }

    @Test
    void completedWebhookKeepsReferencesValidationAndInternalAiData() {
        String body = "{\"status\":0,\"scannedDocument\":{\"scanId\":\"s\",\"credits\":2},"
                + "\"referencesValidation\":{\"summary\":{\"total\":10,\"academic\":6,\"academicValidated\":5,\"nonAcademicValidated\":3}},"
                + "\"results\":{\"internalAIData\":[{\"id\":\"a1\",\"title\":\"T\",\"introduction\":\"I\",\"matchedWords\":40,"
                + "\"identicalWords\":20,\"similarWords\":10,\"paraphrasedWords\":10,\"totalWords\":300,"
                + "\"tags\":[{\"code\":\"internal-ai-source-match\",\"title\":\"AI-Generated\",\"description\":\"d\"}]}]}}";
        CompletedWebhookModel m = GSON.fromJson(body, CompletedWebhookModel.class);
        assertEquals(10, m.getReferencesValidation().getSummary().getTotal());
        assertEquals(6, m.getReferencesValidation().getSummary().getAcademic());
        assertEquals(5, m.getReferencesValidation().getSummary().getAcademicValidated());
        assertEquals(3, m.getReferencesValidation().getSummary().getNonAcademicValidated());
        InternalAIDataModel d = m.getResults().getInternalAIData()[0];
        assertEquals("a1", d.getId());
        assertEquals("T", d.getTitle());
        assertEquals("I", d.getIntroduction());
        assertEquals(40, d.getMatchedWords());
        assertEquals(20, d.getIdenticalWords());
        assertEquals(10, d.getSimilarWords());
        assertEquals(10, d.getParaphrasedWords());
        assertEquals(300, d.getTotalWords());
        assertEquals("internal-ai-source-match", d.getTags()[0].getCode());
        assertNull(GSON.fromJson("{\"status\":0}", CompletedWebhookModel.class).getReferencesValidation());
    }

    @Test
    @SuppressWarnings("deprecation")
    void exportCompletedTaskEndpointIsRead() {
        ExportCompletedWebhookModel m = GSON.fromJson("{\"completed\":true,\"developerPayload\":\"x\",\"tasks\":[{\"endpoint\":"
                + "\"https://example.com/export/pdf\",\"isHealthy\":true,\"httpStatusCode\":200}]}", ExportCompletedWebhookModel.class);
        assertTrue(m.isCompleted());
        assertEquals("https://example.com/export/pdf", m.getTasks()[0].getEndpoint());
        assertEquals("https://example.com/export/pdf", m.getTasks()[0].getEndpont());
        assertTrue(m.getTasks()[0].isIsHealthy());
        assertEquals(200, m.getTasks()[0].getHttpStatusCode());
    }

    @Test
    void creditsCheckedKeepsCreditsAndMetadata() {
        CreditsCheckedWebhookModel m = GSON.fromJson("{\"status\":2,\"credits\":4,\"scannedDocument\":{\"scanId\":\"s\",\"totalWords\":900,"
                + "\"totalExcluded\":5,\"credits\":4,\"creationTime\":\"2026-01-01T00:00:00Z\",\"metadata\":{\"filename\":\"f.pdf\"}}}",
                CreditsCheckedWebhookModel.class);
        assertEquals(4, m.getCredits());
        assertEquals(4, m.getScannedDocument().getCredits());
        assertEquals(900, m.getScannedDocument().getTotalWords());
        assertEquals("f.pdf", m.getScannedDocument().getMetadata().getFilename());
    }

    @Test
    @SuppressWarnings("deprecation")
    void deprecatedProbabilityIsStillRead() {
        AIDetectionResponse r = GSON.fromJson("{\"results\":[{\"classification\":2,\"probability\":0.97,\"matches\":[]}]}", AIDetectionResponse.class);
        assertEquals(0.97, r.getResults().get(0).getProbability(), 1e-9);
        assertEquals(2, r.getResults().get(0).getClassification());
    }

    @Test
    void aiImageResponseWithoutOptionalParts() {
        CopyleaksAiImageDetectionResponseModel r = GSON.fromJson("{\"model\":\"m\",\"summary\":{\"human\":1.0,\"ai\":0.0},"
                + "\"isAiDetected\":false,\"scannedDocument\":{\"scanId\":\"s\",\"actualCredits\":1}}", CopyleaksAiImageDetectionResponseModel.class);
        assertFalse(r.getIsAiDetected());
        assertNull(r.getResult(), "maskType none omits result");
        assertNull(r.getDetectedObjects());
        assertEquals(1, r.getScannedDocument().getActualCredits());
    }

    @Test
    void aiVideoWebhookIsParsed() {
        String body = "{\"model\":\"ai-video-1-pro\","
                + "\"audioResult\":{\"starts\":[13000,45000],\"lengths\":[14000,1000],\"exclude\":{\"starts\":[0],\"lengths\":[2950]}},"
                + "\"visualResult\":{\"starts\":[11566],\"lengths\":[6134],\"exclude\":{\"starts\":[],\"lengths\":[]}},"
                + "\"summary\":{\"audioAIRatio\":0.4902,\"visualAIRatio\":0.5817,\"overallAIRatio\":0.7487},\"isAiDetected\":true,"
                + "\"videoInfo\":{\"metadata\":{\"issuedTime\":\"2026-03-17T13:14:57+00:00\",\"issuedBy\":\"OpenAI\",\"appOrDeviceUsed\":\"Sora\","
                + "\"contentSummary\":\"Created using generative AI\"},\"duration\":55.7},"
                + "\"scannedVideo\":{\"scanId\":\"vid1\",\"actualCredits\":1,\"expectedCredits\":1,\"creationTime\":\"2026-05-05T12:37:50Z\"}}";
        CopyleaksAiVideoDetectionResponseModel r = GSON.fromJson(body, CopyleaksAiVideoDetectionResponseModel.class);
        assertEquals("ai-video-1-pro", r.getModel());
        assertArrayEquals(new long[] { 13000, 45000 }, r.getAudioResult().getStarts());
        assertArrayEquals(new long[] { 14000, 1000 }, r.getAudioResult().getLengths());
        assertArrayEquals(new long[] { 0 }, r.getAudioResult().getExclude().getStarts());
        assertArrayEquals(new long[] { 2950 }, r.getAudioResult().getExclude().getLengths());
        assertArrayEquals(new long[] { 11566 }, r.getVisualResult().getStarts());
        assertEquals(0, r.getVisualResult().getExclude().getStarts().length);
        assertEquals(0.4902, r.getSummary().getAudioAIRatio(), 1e-9);
        assertEquals(0.5817, r.getSummary().getVisualAIRatio(), 1e-9);
        assertEquals(0.7487, r.getSummary().getOverallAIRatio(), 1e-9);
        assertTrue(r.getIsAiDetected());
        assertEquals(55.7, r.getVideoInfo().getDuration(), 1e-9);
        assertEquals("Sora", r.getVideoInfo().getMetadata().getAppOrDeviceUsed());
        assertEquals("OpenAI", r.getVideoInfo().getMetadata().getIssuedBy());
        assertEquals("2026-03-17T13:14:57+00:00", r.getVideoInfo().getMetadata().getIssuedTime());
        assertEquals("Created using generative AI", r.getVideoInfo().getMetadata().getContentSummary());
        assertEquals("vid1", r.getScannedVideo().getScanId());
        assertEquals(1, r.getScannedVideo().getActualCredits());
        assertEquals(1, r.getScannedVideo().getExpectedCredits());
        assertEquals("2026-05-05T12:37:50Z", r.getScannedVideo().getCreationTime());
        assertNull(r.getError());
        assertNull(r.getDeveloperPayload());
    }

    private static final String ASYNC_ERROR = "{\"error\":{\"type\":\"invalid_request_error\",\"id\":\"unsupported_file\",\"code\":67,"
            + "\"message\":\"The file could not be processed.\",\"url\":\"https://docs.copyleaks.com/using-the-apis/api-errors\","
            + "\"details\":[{\"param\":\"url\",\"message\":\"Unsupported format\"}]},\"developerPayload\":\"order-1\"}";

    private static void assertAsyncError(ErrorModel e) {
        assertEquals("invalid_request_error", e.getType());
        assertEquals("unsupported_file", e.getId());
        assertEquals(67, e.getCode());
        assertEquals("The file could not be processed.", e.getMessage());
        assertEquals("https://docs.copyleaks.com/using-the-apis/api-errors", e.getUrl());
        assertEquals("url", e.getDetails()[0].getParam());
        assertEquals("Unsupported format", e.getDetails()[0].getMessage());
    }

    @Test
    void aiVideoWebhookKeepsDeveloperPayloadAndError() {
        CopyleaksAiVideoDetectionResponseModel ok = GSON.fromJson("{\"model\":\"ai-video-1-pro\",\"isAiDetected\":false,"
                + "\"summary\":{\"overallAIRatio\":0.1},\"developerPayload\":\"order-1\"}", CopyleaksAiVideoDetectionResponseModel.class);
        assertNull(ok.getError());
        assertEquals("order-1", ok.getDeveloperPayload());
        assertFalse(ok.getIsAiDetected());

        CopyleaksAiVideoDetectionResponseModel failed = GSON.fromJson(ASYNC_ERROR, CopyleaksAiVideoDetectionResponseModel.class);
        assertAsyncError(failed.getError());
        assertEquals("order-1", failed.getDeveloperPayload());
        assertNull(failed.getSummary());
        assertNull(failed.getIsAiDetected());
    }

    @Test
    void aiImageAsyncWebhookKeepsDeveloperPayloadAndError() {
        CopyleaksAiImageDetectionResponseModel ok = GSON.fromJson("{\"model\":\"m\",\"summary\":{\"human\":1.0,\"ai\":0.0},"
                + "\"isAiDetected\":false,\"developerPayload\":\"order-1\"}", CopyleaksAiImageDetectionResponseModel.class);
        assertNull(ok.getError());
        assertEquals("order-1", ok.getDeveloperPayload());

        CopyleaksAiImageDetectionResponseModel failed = GSON.fromJson(ASYNC_ERROR, CopyleaksAiImageDetectionResponseModel.class);
        assertAsyncError(failed.getError());
        assertEquals("order-1", failed.getDeveloperPayload());
        assertNull(failed.getSummary());
        assertNull(failed.getIsAiDetected());

        CopyleaksAiImageDetectionResponseModel minimal = GSON.fromJson("{\"error\":{\"code\":67,\"message\":\"m\"}}",
                CopyleaksAiImageDetectionResponseModel.class);
        assertEquals(67, minimal.getError().getCode());
        assertNull(minimal.getError().getUrl());
        assertNull(minimal.getError().getDetails());
        assertNull(minimal.getDeveloperPayload());
    }
}
