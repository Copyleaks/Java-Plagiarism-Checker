package models.constants;

/** Codes in {@code notifications.alerts[].code} of the completed webhook. Severity is 0 (lowest) to 4. */
public final class CopyleaksAlertCodes {

    /** AI text detected (severity 4). {@code additionalData} holds the AI Text Detection result as JSON. */
    public static final String SUSPECTED_AI_TEXT = "suspected-ai-text";
}
