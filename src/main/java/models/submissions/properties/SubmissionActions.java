package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/** What to do with the submission. Sent as an integer: 0, 1 or 2. */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionActions implements CopyleaksIntegerEnum {
    /** Start the scan now. */
    Scan(0),
    /** Only check how many credits the scan would use. Start it later with {@code Copyleaks.start}. */
    CheckCredits(1),
    /** Only index the file in the Copyleaks internal database or a Private Cloud Hub repository. Uses no credits. */
    IndexOnly(2);

    private final int value;

    SubmissionActions(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
