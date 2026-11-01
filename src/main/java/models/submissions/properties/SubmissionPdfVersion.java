package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/**
 * PDF report version, sent as an integer: 1, 2 or 3. Deprecated by the API in favor of
 * {@code pdf.reportVersion}.
 */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionPdfVersion implements CopyleaksIntegerEnum {
    /** PDF report version 1. */
    V1(1),
    /** PDF report version 2. */
    V2(2),
    /** PDF report version 3. */
    V3(3);

    private final int value;

    SubmissionPdfVersion(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
