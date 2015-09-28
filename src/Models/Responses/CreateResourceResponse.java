package Models.Responses;

import java.util.UUID;

public class CreateResourceResponse {
	public UUID ProcessId;
	UUID ProcessId() {
		return ProcessId;
	}

	private void setProcessId(UUID processId) {
		ProcessId = processId;
	}
}
