package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import models.constants.CopyleaksErrorTypes;
import models.exceptions.CommandException;
import models.exceptions.CopyleaksApiException;
import models.exceptions.CopyleaksErrorDetail;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;

/** Parsing of the API error envelope into CopyleaksApiException. */
class CopyleaksApiExceptionTest {

    private static CopyleaksApiException exceptionFor(String body) {
        return new CommandException("m", 400, body);
    }

    private static void assertNoEnvelope(CopyleaksApiException e) {
        assertNull(e.getErrorType());
        assertNull(e.getErrorId());
        assertNull(e.getErrorCode());
        assertNull(e.getErrorMessage());
        assertNull(e.getErrorUrl());
        assertNotNull(e.getErrorDetails());
        assertTrue(e.getErrorDetails().isEmpty());
    }

    @Test
    void fullEnvelopeIsParsed() {
        String body = "{\"error\":{\"type\":\"invalid_request_error\",\"id\":\"missing_parameter\",\"code\":1,"
                + "\"message\":\"Bad request. One or several required parameters are missing or incorrect.\","
                + "\"url\":\"https://docs.copyleaks.com/using-the-apis/api-errors/#error-reference\","
                + "\"details\":[{\"param\":\"labels\",\"message\":\"Id Label EXAMPLE-v1 is not supported\"}]}}";

        CopyleaksApiException e = exceptionFor(body);

        assertEquals(CopyleaksErrorTypes.INVALID_REQUEST_ERROR, e.getErrorType());
        assertEquals("missing_parameter", e.getErrorId());
        assertEquals(Integer.valueOf(1), e.getErrorCode());
        assertEquals("Bad request. One or several required parameters are missing or incorrect.", e.getErrorMessage());
        assertEquals("https://docs.copyleaks.com/using-the-apis/api-errors/#error-reference", e.getErrorUrl());
        List<CopyleaksErrorDetail> details = e.getErrorDetails();
        assertEquals(1, details.size());
        assertEquals("labels", details.get(0).getParam());
        assertEquals("Id Label EXAMPLE-v1 is not supported", details.get(0).getMessage());
        assertThrows(UnsupportedOperationException.class, () -> details.add(new CopyleaksErrorDetail("p", "m")));
        // The exception message, status and raw body are unchanged.
        assertEquals("m", e.getMessage());
        assertEquals(400, e.getStatusCode());
        assertEquals(body, e.getResponseBody());
    }

    @Test
    void missingOptionalFieldsAreNullAndDetailsEmpty() {
        CopyleaksApiException e = exceptionFor(
                "{\"error\":{\"type\":\"payment_error\",\"id\":\"insufficient_credits\",\"code\":2,\"message\":\"no credits\"}}");

        assertEquals(CopyleaksErrorTypes.PAYMENT_ERROR, e.getErrorType());
        assertEquals("insufficient_credits", e.getErrorId());
        assertEquals(Integer.valueOf(2), e.getErrorCode());
        assertEquals("no credits", e.getErrorMessage());
        assertNull(e.getErrorUrl());
        assertTrue(e.getErrorDetails().isEmpty());

        assertNoEnvelope(exceptionFor("{\"error\":{}}"));
    }

    @Test
    void codeAsNumericStringIsParsedAndOtherCodesAreIgnored() {
        assertEquals(Integer.valueOf(409), exceptionFor("{\"error\":{\"code\":\"409\"}}").getErrorCode());
        assertEquals(Integer.valueOf(42), exceptionFor("{\"error\":{\"code\":\" 42 \"}}").getErrorCode());
        assertNull(exceptionFor("{\"error\":{\"code\":\"abc\"}}").getErrorCode());
        assertNull(exceptionFor("{\"error\":{\"code\":1.5}}").getErrorCode());
        assertNull(exceptionFor("{\"error\":{\"code\":true}}").getErrorCode());
        assertNull(exceptionFor("{\"error\":{\"code\":null}}").getErrorCode());
        assertNull(exceptionFor("{\"error\":{\"code\":99999999999}}").getErrorCode());
    }

    @Test
    void nonJsonBodyLeavesEveryFieldEmpty() {
        assertNoEnvelope(exceptionFor("<html>Bad Gateway</html>"));
        assertNoEnvelope(exceptionFor(""));
        assertNoEnvelope(exceptionFor(null));
        assertNoEnvelope(exceptionFor("{\"error\":{\"type\":\"api_error\""));
        assertNoEnvelope(exceptionFor("{\"error\":{\"type\":\"api_error\"}} trailing"));
        assertNoEnvelope(exceptionFor("[{\"error\":{\"type\":\"api_error\"}}]"));
        // Constructors without a response body.
        assertNoEnvelope(new CommandException("m"));
        assertNoEnvelope(new RateLimitException());
        assertNoEnvelope(new UnderMaintenanceException());
    }

    @Test
    void jsonWithoutAnErrorObjectLeavesEveryFieldEmpty() {
        assertNoEnvelope(exceptionFor("{\"message\":\"Unauthorized\"}"));
        assertNoEnvelope(exceptionFor("{\"error\":\"Rate limit exceeded.\"}"));
        assertNoEnvelope(exceptionFor("{\"error\":null}"));
        assertNoEnvelope(exceptionFor("{\"error\":[{\"type\":\"api_error\"}]}"));
    }

    @Test
    void malformedDetailsAndNonStringFieldsAreSkipped() {
        CopyleaksApiException notArray = exceptionFor("{\"error\":{\"type\":\"api_error\",\"details\":{\"param\":\"x\"}}}");
        assertEquals(CopyleaksErrorTypes.API_ERROR, notArray.getErrorType());
        assertTrue(notArray.getErrorDetails().isEmpty());

        CopyleaksApiException mixed = exceptionFor("{\"error\":{\"type\":{\"nested\":true},\"id\":5,\"message\":\"still read\","
                + "\"details\":[\"text\",null,7,{\"param\":3,\"message\":\"m\"},{\"param\":\"p\"}]}}");
        assertNull(mixed.getErrorType());
        assertNull(mixed.getErrorId());
        assertEquals("still read", mixed.getErrorMessage());
        List<CopyleaksErrorDetail> details = mixed.getErrorDetails();
        assertEquals(2, details.size());
        assertNull(details.get(0).getParam());
        assertEquals("m", details.get(0).getMessage());
        assertEquals("p", details.get(1).getParam());
        assertNull(details.get(1).getMessage());
    }

    @Test
    void subclassesGetTheParsedFields() {
        String body = "{\"error\":{\"type\":\"api_error\",\"id\":\"too_many_requests\",\"code\":7}}";
        RateLimitException rate = new RateLimitException(429, body, null);
        assertEquals(CopyleaksErrorTypes.API_ERROR, rate.getErrorType());
        assertEquals("too_many_requests", rate.getErrorId());
        assertEquals(Integer.valueOf(7), rate.getErrorCode());
        assertEquals("api_error", new UnderMaintenanceException(503, body).getErrorType());
    }

    @Test
    void errorTypeConstantsMatchTheDocumentedValues() {
        assertEquals("invalid_request_error", CopyleaksErrorTypes.INVALID_REQUEST_ERROR);
        assertEquals("authentication_error", CopyleaksErrorTypes.AUTHENTICATION_ERROR);
        assertEquals("payment_error", CopyleaksErrorTypes.PAYMENT_ERROR);
        assertEquals("api_error", CopyleaksErrorTypes.API_ERROR);
    }
}
