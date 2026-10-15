package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/** PDF report version, sent as an integer. Deprecated by the API in favor of {@code pdf.reportVersion}. */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionPdfVersion implements CopyleaksIntegerEnum {
    V1(1),
    V2(2),
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
