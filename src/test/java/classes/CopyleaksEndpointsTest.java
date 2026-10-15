package classes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import helpers.CopyleaksRequestExecutor;
import models.constants.CopyleaksAiImageDetectionModels;
import models.constants.CopyleaksAiImageMaskTypes;
import models.constants.CopyleaksAiVideoDetectionModels;
import models.constants.CopyleaksTextModerationConstants;
import models.exceptions.AuthExpiredException;
import models.exceptions.RateLimitException;
import models.exports.CopyleaksExportModel;
import models.exports.ExportAiDetection;
import models.exports.ExportCrawledVersion;
import models.exports.ExportOverview;
import models.exports.ExportPdfReport;
import models.exports.ExportResults;
import models.exports.ExportWritingFeedback;
import models.request.CopyleaksAsyncWebhooksModel;
import models.request.CopyleaksDeleteRequest;
import models.request.CopyleaksStartRequest;
import models.request.ScansRequests;
import models.request.AiImageDetection.CopyleaksAiImageDetectionAsyncRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionFileRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.request.AiImageDetection.CopyleaksObjectDetectionItem;
import models.request.AiVideoDetection.CopyleaksAiVideoDetectionRequestModel;
import models.request.ImagePlagiarism.CopyleaksImagePlagiarismRequestModel;
import models.request.TextModeration.CopyleaksTextModerationLabel;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.response.CopyleaksAuthToken;
import models.response.CopyleaksStartResponse;
import models.response.CreditsBalanceResponse;
import models.response.CrossLanguagesResponse;
import models.response.RepositoryInfoResponse;
import models.response.SupportedAiTextDetectionFileTypesResponse;
import models.response.SupportedFileTypesResponse;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismInternetMatchModel;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismResponseModel;
import models.response.aidetection.AIDetectionCreditsResponse;
import models.submissions.CopyleaksFileOcrSubmissionModel;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.CopyleaksURLSubmissionModel;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;
import models.submissions.properties.SubmissionActions;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;
import models.submissions.writingassistant.ScoreWeights;
import testutil.MockApiServer;
import testutil.MockApiServer.Recorded;
import testutil.Multipart;
import testutil.TestTokens;

/** Checks the exact method, path, headers and body of every SDK call against a local server. */
class CopyleaksEndpointsTest {

    private static final String UA = "java-sdk/6.0.0";
    private static final String BEARER = "Bearer " + TestTokens.ACCESS_TOKEN;
    private static final String WEBHOOK = "https://example.com/webhook/{STATUS}";

    private MockApiServer server;
    private CopyleaksAuthToken token;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockApiServer();
        Copyleaks.setApiUri(server.url());
        Copyleaks.setIdentityUri(server.url());
        token = TestTokens.valid();
    }

    @AfterEach
    void tearDown() {
        server.close();
        Copyleaks.setApiUri("https://api.copyleaks.com");
        Copyleaks.setIdentityUri("https://id.copyleaks.com");
        Copyleaks.setRequestTimeout(CopyleaksRequestExecutor.DEFAULT_REQUEST_TIMEOUT);
        Copyleaks.setConnectTimeout(CopyleaksRequestExecutor.DEFAULT_CONNECT_TIMEOUT);
        Copyleaks.setMaxConcurrentRequests(CopyleaksRequestExecutor.DEFAULT_MAX_CONCURRENT_REQUESTS);
        Copyleaks.setMaxRetries(0);
        Copyleaks.setHttpClient(null);
    }

    private static JsonElement json(String s) {
        return JsonParser.parseString(s);
    }

    private Recorded assertRequest(String method, String path, boolean authenticated, String contentType) {
        Recorded r = server.only();
        assertEquals(method, r.method);
        assertEquals(path, r.rawPath);
        assertEquals(UA, r.header("User-Agent"));
        if (authenticated) {
            assertEquals(BEARER, r.header("Authorization"));
        } else {
            assertNull(r.header("Authorization"));
        }
        if (contentType == null) {
            assertNull(r.header("Content-Type"));
        } else if (contentType.endsWith("boundary=")) {
            assertTrue(r.header("Content-Type").startsWith(contentType), r.header("Content-Type"));
        } else {
            assertEquals(contentType, r.header("Content-Type"));
        }
        return r;
    }

    private Recorded assertJsonRequest(String method, String path, String expectedJson) {
        Recorded r = assertRequest(method, path, true, "application/json");
        assertEquals(json(expectedJson), r.json(), r.bodyText());
        return r;
    }

    private static SubmissionProperties sandboxProperties() {
        SubmissionProperties p = new SubmissionProperties(new SubmissionWebhooks(WEBHOOK));
        p.setSandbox(true);
        return p;
    }

    @Test
    void login() throws Exception {
        server.reply(200, "{\"access_token\":\"abc\",\".issued\":\"2026-10-14T10:00:00Z\",\".expires\":\"2026-10-16T10:00:00Z\"}");
        CopyleaksAuthToken t = Copyleaks.login("me@example.com", "key-1");
        Recorded r = assertRequest("POST", "/v3/account/login/api", false, "application/json");
        assertEquals(json("{\"email\":\"me@example.com\",\"key\":\"key-1\"}"), r.json());
        assertEquals("abc", t.getAccessToken());
        assertEquals("2026-10-16T10:00:00Z", t.getExpires());
    }

    @Test
    void submitFile() throws Exception {
        server.reply(201, "");
        SubmissionProperties p = sandboxProperties();
        p.setAction(SubmissionActions.IndexOnly);
        Copyleaks.submitFile(token, "scan1", new CopyleaksFileSubmissionModel("aGVsbG8=", "a.txt", p));
        assertJsonRequest("PUT", "/v3/scans/submit/file/scan1",
                "{\"base64\":\"aGVsbG8=\",\"filename\":\"a.txt\",\"properties\":{\"webhooks\":{\"status\":\"" + WEBHOOK
                        + "\"},\"sandbox\":true,\"action\":2}}");
    }

    @Test
    void submitFileOcr() throws Exception {
        server.reply(201, "");
        Copyleaks.submitFileOcr(token, "scan2", new CopyleaksFileOcrSubmissionModel("en", "aW1n", "i.png", sandboxProperties()));
        assertJsonRequest("PUT", "/v3/scans/submit/ocr/scan2",
                "{\"langCode\":\"en\",\"base64\":\"aW1n\",\"filename\":\"i.png\",\"properties\":{\"webhooks\":{\"status\":\""
                        + WEBHOOK + "\"},\"sandbox\":true}}");
    }

    @Test
    void submitUrlWithVerbAndHeaders() throws Exception {
        server.reply(201, "");
        CopyleaksURLSubmissionModel m = new CopyleaksURLSubmissionModel("https://example.com/", sandboxProperties());
        m.setVerb("POST");
        m.setHeaders(new String[][] { { "Cookie", "a=b" } });
        Copyleaks.submitUrl(token, "scan3", m);
        assertJsonRequest("PUT", "/v3/scans/submit/url/scan3",
                "{\"url\":\"https://example.com/\",\"verb\":\"POST\",\"headers\":[[\"Cookie\",\"a=b\"]],\"properties\":{\"webhooks\":{\"status\":\""
                        + WEBHOOK + "\"},\"sandbox\":true}}");
    }

    @Test
    void scanIdIsEncodedInThePath() throws Exception {
        server.reply(201, "");
        Copyleaks.submitFile(token, "a b/c", new CopyleaksFileSubmissionModel("eA==", "a.txt", sandboxProperties()));
        assertEquals("/v3/scans/submit/file/a%20b%2Fc", server.only().rawPath);
    }

    @Test
    void exportWithAllTargets() throws Exception {
        server.reply(204, "");
        String[][] h = { { "Authorization", "x" } };
        CopyleaksExportModel m = new CopyleaksExportModel("https://example.com/export/done",
                new ExportResults[] { new ExportResults("r1", "https://example.com/r1", "POST", h) },
                new ExportCrawledVersion("https://example.com/crawled", "POST", h));
        m.setCompletionWebhookHeaders(new String[][] { { "X-Hook", "1" } });
        m.setMaxRetries(3);
        m.setDeveloperPayload("dp");
        m.setPdfReport(new ExportPdfReport("https://example.com/pdf", "POST", null));
        m.setAiDetection(new ExportAiDetection("https://example.com/ai", "POST", null));
        m.setWritingFeedback(new ExportWritingFeedback("https://example.com/wf", "PUT", null));
        m.setOverview(new ExportOverview("https://example.com/ov", "POST", h));
        Copyleaks.export(token, "scan4", "exp1", m);
        assertJsonRequest("POST", "/v3/downloads/scan4/export/exp1", "{"
                + "\"completionWebhook\":\"https://example.com/export/done\","
                + "\"completionWebhookHeaders\":[[\"X-Hook\",\"1\"]],"
                + "\"results\":[{\"id\":\"r1\",\"endpoint\":\"https://example.com/r1\",\"verb\":\"POST\",\"headers\":[[\"Authorization\",\"x\"]]}],"
                + "\"crawledVersion\":{\"headers\":[[\"Authorization\",\"x\"]],\"endpoint\":\"https://example.com/crawled\",\"verb\":\"POST\"},"
                + "\"maxRetries\":3,\"developerPayload\":\"dp\","
                + "\"pdfReport\":{\"endpoint\":\"https://example.com/pdf\",\"verb\":\"POST\"},"
                + "\"aiDetection\":{\"endpoint\":\"https://example.com/ai\",\"verb\":\"POST\"},"
                + "\"writingFeedback\":{\"endpoint\":\"https://example.com/wf\",\"verb\":\"PUT\"},"
                + "\"overview\":{\"endpoint\":\"https://example.com/ov\",\"verb\":\"POST\",\"headers\":[[\"Authorization\",\"x\"]]}}");
    }

    @Test
    void exportNeedsOnlyTheCompletionWebhook() throws Exception {
        server.reply(204, "");
        CopyleaksExportModel m = new CopyleaksExportModel("https://example.com/done");
        m.setAiDetection(new ExportAiDetection("https://example.com/ai", "POST", null));
        Copyleaks.export(token, "scan5", "exp2", m);
        assertJsonRequest("POST", "/v3/downloads/scan5/export/exp2",
                "{\"completionWebhook\":\"https://example.com/done\",\"aiDetection\":{\"endpoint\":\"https://example.com/ai\",\"verb\":\"POST\"}}");
    }

    @Test
    void start() throws Exception {
        server.reply(200, "{\"success\":[{\"id\":\"a\"}],\"failed\":[{\"id\":\"b\"}]}");
        CopyleaksStartResponse res = Copyleaks.start(token, new CopyleaksStartRequest(new String[] { "a", "b" }, 1));
        assertJsonRequest("PATCH", "/v3/scans/start", "{\"trigger\":[\"a\",\"b\"],\"errorHandling\":1}");
        assertEquals("a", res.getSuccess()[0].getId());
        assertEquals("b", res.getFailed()[0].getId());
    }

    @Test
    void deleteSendsCompletionWebhookAndHeaders() throws Exception {
        server.reply(202, "");
        CopyleaksDeleteRequest d = new CopyleaksDeleteRequest(
                new ScansRequests[] { new ScansRequests("a"), new ScansRequests("b") }, true, "https://example.com/deleted");
        d.setHeaders(new String[][] { { "header-key", "header-value" } });
        Copyleaks.delete(token, d);
        Recorded r = assertJsonRequest("PATCH", "/v3.1/scans/delete",
                "{\"scans\":[{\"id\":\"a\"},{\"id\":\"b\"}],\"purge\":true,\"completionWebhook\":\"https://example.com/deleted\","
                        + "\"headers\":[[\"header-key\",\"header-value\"]]}");
        assertFalse(r.bodyText().contains("\"uri\""));
        assertEquals("https://example.com/deleted", d.getCompletionWebhook());
        assertEquals("https://example.com/deleted", d.getUri());
    }

    @Test
    void deleteWithoutWebhookOmitsIt() throws Exception {
        server.reply(202, "");
        Copyleaks.delete(token, new CopyleaksDeleteRequest(new ScansRequests[] { new ScansRequests("a") }, false, null));
        assertJsonRequest("PATCH", "/v3.1/scans/delete", "{\"scans\":[{\"id\":\"a\"}],\"purge\":false}");
    }

    @Test
    void resendWebhook() throws Exception {
        server.reply(200, "");
        Copyleaks.resendWebhook(token, "scan6");
        Recorded r = assertRequest("POST", "/v3/scans/scan6/webhooks/resend", true, "application/json");
        assertEquals(0, r.body.length);
    }

    @Test
    void creditsBalance() throws Exception {
        server.reply(200, "{\"Amount\":42}");
        CreditsBalanceResponse r = Copyleaks.getCreditsBalance(token);
        assertRequest("GET", "/v3/scans/credits", true, null);
        assertEquals(42, r.getAmount());
    }

    @Test
    void usagesHistory() throws Exception {
        server.reply(200, "a,b\n1,2\n", "Content-Type", "text/csv");
        String csv = Copyleaks.getUsagesHistoryCsv(token, "01-09-2026", "30-09-2026");
        Recorded r = assertRequest("GET", "/v3/scans/usages/history", true, null);
        assertEquals("start=01-09-2026&end=30-09-2026", r.rawQuery);
        assertEquals("a,b\n1,2\n", csv);
    }

    @Test
    void repositoryInfo() throws Exception {
        server.reply(200, "{\"id\":\"repo 1\",\"name\":\"Hub\",\"description\":\"Desc\",\"permission\":4,\"status\":0,"
                + "\"maxCredits\":1000,\"currentCredits\":900,\"maskingPolicy\":2,\"creationTime\":\"2024-09-09T10:43:52\"}");
        RepositoryInfoResponse r = Copyleaks.getRepositoryInfo(token, "repo 1");
        assertRequest("GET", "/v3/repositories/repository/repo%201/info", true, null);
        assertEquals("repo 1", r.getId());
        assertEquals("Hub", r.getName());
        assertEquals("Desc", r.getDescription());
        assertEquals(4, r.getPermission());
        assertEquals(0, r.getStatus());
        assertEquals(1000, r.getMaxCredits());
        assertEquals(900, r.getCurrentCredits());
        assertEquals(2, r.getMaskingPolicy());
        assertEquals("2024-09-09T10:43:52", r.getCreationTime());
    }

    @Test
    void releaseNotes() throws Exception {
        server.reply(200, "{\"updates\":[{\"msgType\":\"NEW\",\"htmlDescription\":\"x\"}]}");
        assertEquals("NEW", Copyleaks.getReleaseNotes().getUpdates()[0].getMsgType());
        assertRequest("GET", "/v3/release-logs.json", false, null);
    }

    @Test
    void supportedFileTypesUsesTheDocumentedPath() throws Exception {
        server.reply(200, "{\"textual\":[\"pdf\",\"txt\"],\"ocr\":[\"png\"]}");
        SupportedFileTypesResponse r = Copyleaks.getSupportedFileTypes();
        assertRequest("GET", "/v3/miscellaneous/supported-plagiarism-file-types", false, null);
        assertArrayEquals(new String[] { "pdf", "txt" }, r.getTextual());
        assertArrayEquals(r.getTextual(), r.getTextural());
        assertArrayEquals(new String[] { "png" }, r.getOcr());
    }

    @Test
    void supportedAiTextDetectionFileTypes() throws Exception {
        server.reply(200, "{\"supportedAiFileTypes\":[\"pdf\",\"docx\",\"LaTeX\"]}");
        SupportedAiTextDetectionFileTypesResponse r = Copyleaks.getSupportedAiTextDetectionFileTypes();
        assertRequest("GET", "/v3/miscellaneous/supported-ai-text-detection-file-types", false, null);
        assertArrayEquals(new String[] { "pdf", "docx", "LaTeX" }, r.getSupportedAiFileTypes());
    }

    @Test
    void allowedCrossLanguages() throws Exception {
        // The shape the API returns: language name to ISO-639-1 code.
        server.reply(200, "{\"documentLanguages\":{\"english\":\"en\",\"spanish\":\"es\"},"
                + "\"resultLanguages\":{\"albanian\":\"sq\",\"french\":\"fr\",\"german\":\"de\"}}");
        CrossLanguagesResponse r = Copyleaks.getAllowedCrossLanguages();
        assertRequest("GET", "/v3/miscellaneous/allowed-cross-languages", false, null);
        assertEquals("en", r.getDocumentLanguages().get("english"));
        assertEquals("es", r.getDocumentLanguages().get("spanish"));
        assertArrayEquals(new String[] { "en", "es" }, r.getDocumentLanguageCodes());
        assertArrayEquals(new String[] { "sq", "fr", "de" }, r.getResultLanguageCodes());
        assertEquals(3, r.getResultLanguages().size());
    }

    @Test
    void allowedCrossLanguagesAlsoReadsArrays() throws Exception {
        // The shape shown in the API reference: arrays of codes.
        server.reply(200, "{\"documentLanguages\":[\"en\",\"es\"],\"resultLanguages\":[\"fr\"]}");
        CrossLanguagesResponse r = Copyleaks.getAllowedCrossLanguages();
        assertArrayEquals(new String[] { "en", "es" }, r.getDocumentLanguageCodes());
        assertArrayEquals(new String[] { "fr" }, r.getResultLanguageCodes());
        assertEquals("fr", r.getResultLanguages().get("fr"));
    }

    @Test
    void ocrLanguages() throws Exception {
        server.reply(200, "[\"en\",\"he\"]");
        assertArrayEquals(new String[] { "en", "he" }, Copyleaks.getOCRSupportedLanguagesAsync());
        assertRequest("GET", "/v3/miscellaneous/ocr-languages-list", false, null);
    }

    @Test
    void aiTextDetectionWithExplainAndSensitivity() throws Exception {
        server.reply(200, "{\"modelVersion\":\"v8\",\"summary\":{\"human\":0.1,\"ai\":0.9}}");
        CopyleaksNaturalLanguageSubmissionModel m = new CopyleaksNaturalLanguageSubmissionModel("text", true, "en");
        m.setExplain(true);
        m.setSensitivity(3);
        double ai = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, "ai1", m).getSummary().getAi();
        assertJsonRequest("POST", "/v2/writer-detector/ai1/check",
                "{\"language\":\"en\",\"text\":\"text\",\"sandbox\":true,\"explain\":true,\"sensitivity\":3}");
        assertEquals(0.9, ai, 1e-9);
    }

    @Test
    void aiTextDetectionDefaultsSendNoExplainOrSensitivity() throws Exception {
        server.reply(200, "{}");
        Copyleaks.aiDetectionClient.submitNaturalLanguage(token, "ai2", new CopyleaksNaturalLanguageSubmissionModel("t"));
        assertJsonRequest("POST", "/v2/writer-detector/ai2/check", "{\"text\":\"t\",\"sandbox\":false}");
    }

    @Test
    void aiTextDetectionCredits() throws Exception {
        server.reply(200, "{\"credits\":382}");
        AIDetectionCreditsResponse r = Copyleaks.aiDetectionClient.getCredits(token);
        assertRequest("GET", "/v2/writer-detector/credits", true, null);
        assertEquals(382, r.getCredits());
    }

    @Test
    void writingAssistantSendsDefaultWeightsOfOne() throws Exception {
        server.reply(200, "{}");
        ScoreWeights w = new ScoreWeights();
        w.setGrammarScoreWeight(0.5);
        Copyleaks.writingAssistantClient.submitText(token, "wa1", new CopyleaksWritingAssistantSubmissionModel("txt", true, "en", w));
        assertJsonRequest("POST", "/v1/writing-feedback/wa1/check",
                "{\"text\":\"txt\",\"sandbox\":true,\"language\":\"en\",\"score\":{\"grammarScoreWeight\":0.5,"
                        + "\"mechanicsScoreWeight\":1.0,\"sentenceStructureScoreWeight\":1.0,\"wordChoiceScoreWeight\":1.0}}");
    }

    @Test
    void correctionTypes() throws Exception {
        server.reply(200, "{\"correctionTypes\":[]}");
        Copyleaks.writingAssistantClient.getCorrectionTypes(token, "en");
        assertRequest("GET", "/v1/writing-feedback/correction-types/en", true, null);
    }

    @Test
    void textModeration() throws Exception {
        server.reply(200, "{\"moderations\":{}}");
        CopyleaksTextModerationRequest req = new CopyleaksTextModerationRequest("hello", true, "en",
                new CopyleaksTextModerationLabel[] { new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.TOXIC_V1) });
        Copyleaks.textModerationClient.submitText(token, "tm1", req);
        assertJsonRequest("POST", "/v1/text-moderation/tm1/check",
                "{\"text\":\"hello\",\"sandbox\":true,\"language\":\"en\",\"labels\":[{\"id\":\"toxic-v1\"}]}");
    }

    private static final String AI_IMAGE_RESPONSE = "{\"model\":\"ai-image-1-ultra\","
            + "\"result\":{\"starts\":[0,10],\"lengths\":[5,5],\"labels\":[1,4]},"
            + "\"summary\":{\"human\":0.3,\"ai\":0.7},\"isAiDetected\":true,"
            + "\"imageInfo\":{\"shape\":{\"height\":1024,\"width\":768},\"metadata\":{\"issuedBy\":\"OpenAI\",\"contentSummary\":\"Created using generative AI\"}},"
            + "\"detectedObjects\":[{\"object\":\"face\",\"mask\":{\"starts\":[3],\"lengths\":[9]}}],"
            + "\"scannedDocument\":{\"scanId\":\"img1\",\"actualCredits\":1,\"expectedCredits\":1,\"creationTime\":\"2026-01-01T00:00:00Z\"}}";

    @Test
    void aiImageJsonSendsFilenameMaskTypeAndObjectDetection() throws Exception {
        server.reply(200, AI_IMAGE_RESPONSE);
        CopyleaksAiImageDetectionRequestModel m = new CopyleaksAiImageDetectionRequestModel("aW1n", "a.png",
                CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA, true);
        m.setMaskType(CopyleaksAiImageMaskTypes.HEATMAP);
        m.setObjectDetection(new CopyleaksObjectDetectionItem[] { new CopyleaksObjectDetectionItem("face"),
                new CopyleaksObjectDetectionItem("hand") });
        CopyleaksAiImageDetectionResponseModel r = Copyleaks.aiImageDetectionClient.submit(token, "img1", m);
        assertJsonRequest("POST", "/v1/ai-image-detector/img1/check",
                "{\"base64\":\"aW1n\",\"filename\":\"a.png\",\"model\":\"ai-image-1-ultra\",\"sandbox\":true,"
                        + "\"maskType\":\"heatmap\",\"objectDetection\":[{\"object\":\"face\"},{\"object\":\"hand\"}]}");
        assertTrue(r.getIsAiDetected());
        assertArrayEquals(new int[] { 1, 4 }, r.getResult().getLabels());
        assertEquals("face", r.getDetectedObjects()[0].getObject());
        assertArrayEquals(new int[] { 3 }, r.getDetectedObjects()[0].getMask().getStarts());
        assertArrayEquals(new int[] { 9 }, r.getDetectedObjects()[0].getMask().getLengths());
        assertEquals("Created using generative AI", r.getImageInfo().getMetadata().getContentSummary());
        assertEquals(0.7f, r.getSummary().getAi(), 1e-6);
    }

    @Test
    void aiImageMultipartUpload() throws Exception {
        server.reply(200, AI_IMAGE_RESPONSE);
        byte[] png = { (byte) 0x89, 'P', 'N', 'G', 0, 1, 2, (byte) 0xff };
        CopyleaksAiImageDetectionFileRequestModel m = new CopyleaksAiImageDetectionFileRequestModel(png, "pic.png",
                CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA, true);
        m.setMaskType(CopyleaksAiImageMaskTypes.NONE);
        m.setObjectDetection(new CopyleaksObjectDetectionItem[] { new CopyleaksObjectDetectionItem("face") });
        CopyleaksAiImageDetectionResponseModel r = Copyleaks.aiImageDetectionClient.submitFile(token, "img2", m);
        Recorded rec = assertRequest("POST", "/v1/ai-image-detector/img2/check", true, "multipart/form-data; boundary=");
        Map<String, Multipart.Part> parts = Multipart.parse(rec.header("Content-Type"), rec.body);
        assertEquals(Set.of("image", "filename", "model", "sandbox", "maskType", "objectDetection"), parts.keySet());
        assertArrayEquals(png, parts.get("image").content);
        assertEquals("form-data; name=\"image\"; filename=\"pic.png\"", parts.get("image").disposition());
        assertEquals("image/png", parts.get("image").contentType());
        assertEquals("pic.png", parts.get("filename").text());
        assertEquals("ai-image-1-ultra", parts.get("model").text());
        assertEquals("true", parts.get("sandbox").text());
        assertEquals("none", parts.get("maskType").text());
        assertEquals(json("[{\"object\":\"face\"}]"), json(parts.get("objectDetection").text()));
        assertTrue(r.getIsAiDetected());
    }

    @Test
    void aiImageMultipartOmitsUnsetOptions() throws Exception {
        server.reply(200, "{}");
        Copyleaks.aiImageDetectionClient.submitFile(token, "img3",
                new CopyleaksAiImageDetectionFileRequestModel(new byte[] { 1 }, "p.jpg", "ai-image-1-ultra"));
        Recorded rec = server.only();
        Map<String, Multipart.Part> parts = Multipart.parse(rec.header("Content-Type"), rec.body);
        assertEquals(Set.of("image", "filename", "model", "sandbox"), parts.keySet());
        assertEquals("false", parts.get("sandbox").text());
        assertEquals("image/jpeg", parts.get("image").contentType());
    }

    @Test
    void aiImageAsyncSubmit() throws Exception {
        server.reply(201, "{}");
        CopyleaksAiImageDetectionAsyncRequestModel m = new CopyleaksAiImageDetectionAsyncRequestModel(
                "https://example.com/i.png", "i.png", CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA,
                new CopyleaksAsyncWebhooksModel("https://example.com/webhook/{STATUS}", new String[][] { { "Authorization", "t" } }));
        m.setMaskType(CopyleaksAiImageMaskTypes.HEATMAP);
        m.setObjectDetection(new CopyleaksObjectDetectionItem[] { new CopyleaksObjectDetectionItem("face") });
        m.setHeaders(new String[][] { { "X-Custom-Auth", "tok" } });
        m.setSandbox(true);
        m.setDeveloperPayload("order-1");
        Copyleaks.aiImageDetectionClient.submitAsync(token, "imgasync1", m);
        assertJsonRequest("PUT", "/v1/ai-image-detector-async/imgasync1/submit",
                "{\"url\":\"https://example.com/i.png\",\"fileName\":\"i.png\",\"model\":\"ai-image-1-ultra\",\"maskType\":\"heatmap\","
                        + "\"objectDetection\":[{\"object\":\"face\"}],\"headers\":[[\"X-Custom-Auth\",\"tok\"]],"
                        + "\"webhooks\":{\"url\":\"https://example.com/webhook/{STATUS}\",\"headers\":[[\"Authorization\",\"t\"]]},"
                        + "\"sandbox\":true,\"developerPayload\":\"order-1\"}");
    }

    @Test
    void aiImageAsyncMinimalBody() throws Exception {
        server.reply(201, "{}");
        Copyleaks.aiImageDetectionClient.submitAsync(token, "imgasync2", new CopyleaksAiImageDetectionAsyncRequestModel(
                "https://example.com/i.png", "i.png", "ai-image-1-ultra", new CopyleaksAsyncWebhooksModel("https://example.com/w")));
        assertJsonRequest("PUT", "/v1/ai-image-detector-async/imgasync2/submit",
                "{\"url\":\"https://example.com/i.png\",\"fileName\":\"i.png\",\"model\":\"ai-image-1-ultra\","
                        + "\"webhooks\":{\"url\":\"https://example.com/w\"},\"sandbox\":false}");
    }

    @Test
    void aiVideoSubmit() throws Exception {
        server.reply(201, "{}");
        CopyleaksAiVideoDetectionRequestModel m = new CopyleaksAiVideoDetectionRequestModel("https://example.com/v.mp4",
                "v.mp4", CopyleaksAiVideoDetectionModels.AI_VIDEO_1_PRO,
                new CopyleaksAsyncWebhooksModel("https://example.com/webhook/{STATUS}"));
        m.setSandbox(true);
        m.setVerb("GET");
        m.setHeaders(new String[][] { { "X-Custom-Auth", "my-token" } });
        m.setDeveloperPayload("order-id-12345");
        Copyleaks.aiVideoDetectionClient.submit(token, "vid1", m);
        assertJsonRequest("POST", "/v1/ai-video-detector/vid1/submit",
                "{\"url\":\"https://example.com/v.mp4\",\"headers\":[[\"X-Custom-Auth\",\"my-token\"]],\"verb\":\"GET\","
                        + "\"filename\":\"v.mp4\",\"model\":\"ai-video-1-pro\",\"webhooks\":{\"url\":\"https://example.com/webhook/{STATUS}\"},"
                        + "\"sandbox\":true,\"developerPayload\":\"order-id-12345\"}");
    }

    @Test
    void imagePlagiarismCheck() throws Exception {
        server.reply(200, "{\"developerPayload\":\"dp\",\"scannedImage\":{\"scanId\":\"ip1\",\"expectedCredits\":1,\"actualCredits\":1,"
                + "\"creationTime\":\"2026-05-24T10:00:00Z\",\"width\":1920,\"height\":1080,\"filename\":\"p.jpg\"},"
                + "\"matches\":{\"internet\":[{\"url\":\"https://example.com/a.jpg\",\"matchType\":0,\"webPages\":[{\"url\":\"https://example.com/post\"}]},"
                + "{\"url\":\"https://example.org/b.jpg\",\"matchType\":1}],\"score\":{\"totalMatches\":2,\"fullMatches\":1,\"partialMatches\":1}}}");
        byte[] jpg = "jpeg-bytes".getBytes(StandardCharsets.US_ASCII);
        CopyleaksImagePlagiarismRequestModel m = new CopyleaksImagePlagiarismRequestModel(jpg, "p.jpg", true);
        m.setDeveloperPayload("dp");
        CopyleaksImagePlagiarismResponseModel r = Copyleaks.imagePlagiarismClient.check(token, "ip1", m);

        Recorded rec = assertRequest("POST", "/v1/image-plagiarism-detector/ip1/check", true, "multipart/form-data; boundary=");
        assertEquals(String.valueOf(rec.body.length), rec.header("Content-Length"));
        assertNull(rec.header("Transfer-Encoding"));
        Map<String, Multipart.Part> parts = Multipart.parse(rec.header("Content-Type"), rec.body);
        assertEquals(Set.of("image", "filename", "sandbox", "developerPayload"), parts.keySet());
        assertArrayEquals(jpg, parts.get("image").content);
        assertEquals("form-data; name=\"image\"; filename=\"p.jpg\"", parts.get("image").disposition());
        assertEquals("image/jpeg", parts.get("image").contentType());
        assertEquals("p.jpg", parts.get("filename").text());
        assertEquals("true", parts.get("sandbox").text());
        assertEquals("dp", parts.get("developerPayload").text());

        assertEquals("dp", r.getDeveloperPayload());
        assertEquals("ip1", r.getScannedImage().getScanId());
        assertEquals(1920, r.getScannedImage().getWidth());
        assertEquals(1080, r.getScannedImage().getHeight());
        assertEquals("p.jpg", r.getScannedImage().getFilename());
        assertEquals(1, r.getScannedImage().getActualCredits());
        assertEquals(2, r.getMatches().getScore().getTotalMatches());
        assertEquals(1, r.getMatches().getScore().getFullMatches());
        assertEquals(1, r.getMatches().getScore().getPartialMatches());
        CopyleaksImagePlagiarismInternetMatchModel first = r.getMatches().getInternet()[0];
        assertEquals(CopyleaksImagePlagiarismInternetMatchModel.MATCH_TYPE_FULL, first.getMatchType());
        assertEquals("https://example.com/post", first.getWebPages()[0].getUrl());
        assertEquals(CopyleaksImagePlagiarismInternetMatchModel.MATCH_TYPE_PARTIAL, r.getMatches().getInternet()[1].getMatchType());
        assertNull(r.getMatches().getInternet()[1].getWebPages());
    }

    @Test
    void imagePlagiarismWithoutPayloadOmitsIt() throws Exception {
        server.reply(200, "{}");
        Copyleaks.imagePlagiarismClient.check(token, "ip2", new CopyleaksImagePlagiarismRequestModel(new byte[] { 7 }, "p.webp"));
        Recorded rec = server.only();
        Map<String, Multipart.Part> parts = Multipart.parse(rec.header("Content-Type"), rec.body);
        assertEquals(Set.of("image", "filename", "sandbox"), parts.keySet());
        assertEquals("false", parts.get("sandbox").text());
        assertEquals("image/webp", parts.get("image").contentType());
    }

    // 429 is mapped the same way everywhere.

    interface Call {
        void run() throws Exception;
    }

    @Test
    void every429IsARateLimitException() {
        Map<String, Call> calls = new LinkedHashMap<>();
        CopyleaksAsyncWebhooksModel hooks = new CopyleaksAsyncWebhooksModel("https://example.com/w");
        calls.put("login", () -> Copyleaks.login("e", "k"));
        calls.put("submitFile", () -> Copyleaks.submitFile(token, "s", new CopyleaksFileSubmissionModel("eA==", "a.txt", sandboxProperties())));
        calls.put("submitFileOcr", () -> Copyleaks.submitFileOcr(token, "s", new CopyleaksFileOcrSubmissionModel("en", "eA==", "a.png", sandboxProperties())));
        calls.put("submitUrl", () -> Copyleaks.submitUrl(token, "s", new CopyleaksURLSubmissionModel("https://example.com", sandboxProperties())));
        calls.put("export", () -> Copyleaks.export(token, "s", "e", new CopyleaksExportModel("https://example.com/d")));
        calls.put("start", () -> Copyleaks.start(token, new CopyleaksStartRequest(new String[] { "s" }, 0)));
        calls.put("delete", () -> Copyleaks.delete(token, new CopyleaksDeleteRequest(new ScansRequests[] { new ScansRequests("s") }, false, null)));
        calls.put("resendWebhook", () -> Copyleaks.resendWebhook(token, "s"));
        calls.put("getCreditsBalance", () -> Copyleaks.getCreditsBalance(token));
        calls.put("getUsagesHistoryCsv", () -> Copyleaks.getUsagesHistoryCsv(token, "01-01-2026", "02-01-2026"));
        calls.put("getRepositoryInfo", () -> Copyleaks.getRepositoryInfo(token, "r"));
        calls.put("getReleaseNotes", Copyleaks::getReleaseNotes);
        calls.put("getSupportedFileTypes", Copyleaks::getSupportedFileTypes);
        calls.put("getSupportedAiTextDetectionFileTypes", Copyleaks::getSupportedAiTextDetectionFileTypes);
        calls.put("getAllowedCrossLanguages", Copyleaks::getAllowedCrossLanguages);
        calls.put("getOCRSupportedLanguagesAsync", Copyleaks::getOCRSupportedLanguagesAsync);
        calls.put("aiText", () -> Copyleaks.aiDetectionClient.submitNaturalLanguage(token, "s", new CopyleaksNaturalLanguageSubmissionModel("t")));
        calls.put("aiTextCredits", () -> Copyleaks.aiDetectionClient.getCredits(token));
        calls.put("aiImage", () -> Copyleaks.aiImageDetectionClient.submit(token, "s", new CopyleaksAiImageDetectionRequestModel("eA==", "a.png", "m")));
        calls.put("aiImageFile", () -> Copyleaks.aiImageDetectionClient.submitFile(token, "s", new CopyleaksAiImageDetectionFileRequestModel(new byte[] { 1 }, "a.png", "m")));
        calls.put("aiImageAsync", () -> Copyleaks.aiImageDetectionClient.submitAsync(token, "s", new CopyleaksAiImageDetectionAsyncRequestModel("https://example.com/a.png", "a.png", "m", hooks)));
        calls.put("aiVideo", () -> Copyleaks.aiVideoDetectionClient.submit(token, "s", new CopyleaksAiVideoDetectionRequestModel("https://example.com/v.mp4", "v.mp4", "m", hooks)));
        calls.put("imagePlagiarism", () -> Copyleaks.imagePlagiarismClient.check(token, "s", new CopyleaksImagePlagiarismRequestModel(new byte[] { 1 }, "a.png")));
        calls.put("writing", () -> Copyleaks.writingAssistantClient.submitText(token, "s", new CopyleaksWritingAssistantSubmissionModel("t")));
        calls.put("correctionTypes", () -> Copyleaks.writingAssistantClient.getCorrectionTypes(token, "en"));
        calls.put("moderation", () -> Copyleaks.textModerationClient.submitText(token, "s", new CopyleaksTextModerationRequest("t", true, null,
                new CopyleaksTextModerationLabel[] { new CopyleaksTextModerationLabel("toxic-v1") })));
        server.always(429, "{\"error\":\"Rate limit exceeded.\"}");
        for (Map.Entry<String, Call> c : calls.entrySet()) {
            RateLimitException e = assertThrows(RateLimitException.class, c.getValue()::run, c.getKey());
            assertEquals(429, e.getStatusCode(), c.getKey());
            assertEquals("{\"error\":\"Rate limit exceeded.\"}", e.getResponseBody(), c.getKey());
        }
        assertEquals(calls.size(), server.requests().size());
    }

    @Test
    void expiredTokenIsRejectedBeforeAnyRequest() {
        CopyleaksAuthToken expired = TestTokens.expired();
        assertThrows(AuthExpiredException.class, () -> Copyleaks.getCreditsBalance(expired));
        assertThrows(AuthExpiredException.class, () -> Copyleaks.imagePlagiarismClient.check(expired, "s",
                new CopyleaksImagePlagiarismRequestModel(new byte[] { 1 }, "a.png")));
        assertTrue(server.requests().isEmpty());
    }

    @Test
    void nullArgumentsFailFast() {
        assertThrows(NullPointerException.class, () -> Copyleaks.submitFile(token, null,
                new CopyleaksFileSubmissionModel("eA==", "a.txt", sandboxProperties())));
        assertThrows(NullPointerException.class, () -> Copyleaks.login(null, "k"));
        assertTrue(server.requests().isEmpty());
    }

    @Test
    void httpSettingsAreConfigurable() {
        assertEquals(Duration.ofSeconds(120), Copyleaks.getRequestTimeout());
        assertEquals(Duration.ofSeconds(30), Copyleaks.getConnectTimeout());
        assertEquals(20, Copyleaks.getMaxConcurrentRequests());
        assertEquals(0, Copyleaks.getMaxRetries());
        Copyleaks.setRequestTimeout(Duration.ofSeconds(10));
        Copyleaks.setConnectTimeout(Duration.ofSeconds(4));
        Copyleaks.setMaxConcurrentRequests(5);
        Copyleaks.setMaxRetries(2);
        assertEquals(Duration.ofSeconds(10), Copyleaks.getRequestTimeout());
        assertEquals(Duration.ofSeconds(4), Copyleaks.getConnectTimeout());
        assertEquals(Duration.ofSeconds(4), Copyleaks.getHttpClient().connectTimeout().get());
        assertEquals(5, Copyleaks.getMaxConcurrentRequests());
        assertEquals(2, Copyleaks.getMaxRetries());
    }

    @Test
    void requestTimeoutAppliesToSdkCalls() {
        Copyleaks.setRequestTimeout(Duration.ofMillis(300));
        server.replyAfter(5000, 200, "{\"Amount\":1}");
        java.util.concurrent.ExecutionException e = assertThrows(java.util.concurrent.ExecutionException.class,
                () -> Copyleaks.getCreditsBalance(token));
        assertTrue(e.getCause() instanceof java.net.http.HttpTimeoutException);
    }
}
