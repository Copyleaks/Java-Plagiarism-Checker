package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/** How documents indexed in a Private Cloud Hub repository are shown to other users. Sent as an integer. */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionMaskingPolicy implements CopyleaksIntegerEnum {
    NoMask(0),
    /** Mask documents other users added: text that does not match identically is replaced with #. */
    MaskOtherUsersFiles(1),
    /** Mask all documents: text that does not match identically is replaced with #. */
    MaskAllFiles(2);

    private final int value;

    SubmissionMaskingPolicy(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
