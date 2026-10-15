package testutil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import models.response.CopyleaksAuthToken;

/** Login tokens for offline tests. */
public final class TestTokens {

    public static final String ACCESS_TOKEN = "test-access-token";

    private TestTokens() {
    }

    public static CopyleaksAuthToken valid() {
        Instant now = Instant.now();
        return new CopyleaksAuthToken(ACCESS_TOKEN, now.toString(), now.plus(1, ChronoUnit.HOURS).toString());
    }

    public static CopyleaksAuthToken expired() {
        Instant now = Instant.now();
        return new CopyleaksAuthToken(ACCESS_TOKEN, now.minus(2, ChronoUnit.HOURS).toString(),
                now.minus(1, ChronoUnit.MINUTES).toString());
    }
}
