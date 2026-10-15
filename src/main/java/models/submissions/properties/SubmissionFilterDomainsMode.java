package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/** Whether {@code filters.domains} lists domains to include or to exclude. Sent as an integer: 0 or 1. */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionFilterDomainsMode implements CopyleaksIntegerEnum {
    /** Keep only results from the listed domains. */
    Include(0),
    /** Keep only results outside the listed domains. */
    Exclude(1);

    private final int value;

    SubmissionFilterDomainsMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
