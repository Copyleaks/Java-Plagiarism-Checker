package classes;

/** Server addresses, set with {@link Copyleaks#setApiUri(String)} and {@link Copyleaks#setIdentityUri(String)}. */
public final class Consts {
    public static volatile String API_SERVER_URI = "https://api.copyleaks.com";
    /** The login server address. */
    public static volatile String IDENTITY_SERVER_URI = "https://id.copyleaks.com";
    public static final String USER_AGENT = "java-sdk/6.0.0";
}
