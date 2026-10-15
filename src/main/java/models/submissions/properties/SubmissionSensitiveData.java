package models.submissions.properties;

/** Masks sensitive data in the scanned document with # characters. Requires a plan of 2,500 pages or more. */
public class SubmissionSensitiveData {
    /** Driver's license numbers: Australia, Canada, UK, USA, Japan, Spain, Germany. */
    public Boolean driversLicense = null;

    /** Auth tokens, AWS, Azure and GCP credentials and keys, HTTP basic auth, JWTs, encryption keys, passwords. */
    private Boolean credentials = null;
    /**
     * Passports: Canada, China, France, Germany, Ireland, Japan, Korea, Mexico, Spain, UK, USA,
     * Netherlands, Poland, Sweden, Australia, Singapore, Taiwan.
     */
    private Boolean passport = null;

    /** IP and MAC addresses. */
    private Boolean network = null;

    private Boolean url = null;

    private Boolean emailAddress = null;

    /** Credit card numbers and track numbers. */
    private Boolean creditCard = null;
    private Boolean phoneNumber = null;

    public Boolean getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(Boolean driversLicense) {
        this.driversLicense = driversLicense;
    }

    public Boolean getCredentials() {
        return credentials;
    }

    public void setCredentials(Boolean credentials) {
        this.credentials = credentials;
    }

    public Boolean getPassport() {
        return passport;
    }

    public void setPassport(Boolean passport) {
        this.passport = passport;
    }

    public Boolean getNetwork() {
        return network;
    }

    public void setNetwork(Boolean network) {
        this.network = network;
    }

    public Boolean getUrl() {
        return url;
    }

    public void setUrl(Boolean url) {
        this.url = url;
    }

    public Boolean getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(Boolean emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        this.creditCard = creditCard;
    }

    public Boolean getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Boolean phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
