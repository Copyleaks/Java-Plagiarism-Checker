package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/** The algorithm goal of a scan. Sent as an integer. */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionScanMethodAlgorithm implements CopyleaksIntegerEnum {
    /** Prioritize a higher similarity score. */
    MaximumCoverage(0),
    /** Prioritize finding more sources. */
    MaximumResults(1);

    private final int value;

    SubmissionScanMethodAlgorithm(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
