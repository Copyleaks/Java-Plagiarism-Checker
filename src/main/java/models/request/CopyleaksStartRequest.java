package models.request;

public class CopyleaksStartRequest {

    private String[] trigger;
    private int errorHandling;

    /**
     * @param trigger       IDs of scans submitted with the "check credits" action, to start now.
     * @param errorHandling {@link models.misc.ErrorHandling}: 1 (ignore) starts the valid scans even if some are in
     *                      error, 0 (cancel) cancels the request on any error.
     */
    public CopyleaksStartRequest(String[] trigger, int errorHandling) {
        this.trigger = trigger;
        this.errorHandling = errorHandling;
    }

    public String[] getTrigger() {
        return trigger;
    }

    public int getErrorHandling() {
        return errorHandling;
    }
}
