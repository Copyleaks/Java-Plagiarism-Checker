package helpers;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

import models.exceptions.AuthExpiredException;
import models.response.CopyleaksAuthToken;

public class CopyleaksClientHelper {

    /** A token is treated as expired when less than this many seconds of validity are left. */
    private static final long EXPIRY_MARGIN_SECONDS = 5 * 60;

    /** Cache, so the same token is not parsed on every call. */
    private static volatile ParsedExpiry lastExpiry;

    private static final class ParsedExpiry {
        final String text;
        final Instant instant;

        ParsedExpiry(String text, Instant instant) {
            this.text = text;
            this.instant = instant;
        }
    }

    /**
     * Checks that the token is not expired. A token that expires within 5 minutes counts as expired.
     * @throws AuthExpiredException if the token has expired. Log in again to get a new one.
     */
    public static void verifyAuthToken(CopyleaksAuthToken authToken) throws AuthExpiredException {
        Objects.requireNonNull(authToken, "authToken is null");
        String expires = authToken.getExpires();
        Objects.requireNonNull(expires, "authToken has no expiry date");
        ParsedExpiry cached = lastExpiry;
        Instant expireDate;
        if (cached != null && cached.text.equals(expires)) {
            expireDate = cached.instant;
        } else {
            expireDate = Instant.from(ZonedDateTime.parse(expires));
            lastExpiry = new ParsedExpiry(expires, expireDate);
        }
        if (!expireDate.isAfter(Instant.now().plusSeconds(EXPIRY_MARGIN_SECONDS))) {
            throw new AuthExpiredException();
        }
    }

    /** @return true for a 2xx status code. */
    public static boolean ensureHttpSuccess(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }
}
