/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.submissions.properties;

public class SubmissionSensitiveData {
    /**
     * Mask driver's license numbers from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     * * Supported Types:
     * * Australia driver's license number
     * * Canada driver's license number
     * * United Kingdom driver's license number
     * * USA drivers license number
     * * Japan driver's license number
     * * Spain driver's license number
     * * Germany driver's license number
     */
    public Boolean driversLicense = null;

    /**
     * Mask credentials from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     * * Supported Types:
     * * Authentication token
     * * Amazon Web Services credentials
     * * Azure JSON Web Token
     * * HTTP basic authentication header
     * * Google Cloud Platform service account credentials
     * * Google Cloud Platform API key
     * * JSON Web Token
     * * Encryption key
     * * Password
     */
    private Boolean credentials = null;
    /**
     * Mask passports from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     * * Supported Types:
     * * Canada passport number
     * * China passport number
     * * France passport number
     * * Germany passport number
     * * Ireland passport number
     * * Japan passport number
     * * Korea passport number
     * * Mexico passport number
     * * Spain passport number
     * * United Kingdom passport number
     * * USA passport number
     * * Netherlands passport number
     * * Poland passport
     * * Sweden passport number
     * * Australia passport number
     * * Singapore passport number
     * * Taiwan passport number
     */
    private Boolean passport = null;

    /**
     * Mask network identifiers from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     * * Supported Types:
     * * IP address
     * * Local MAC address
     * * MAC address
     */
    private Boolean network = null;

    /**
     * Mask url from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     */
    private Boolean url = null;

    /**
     * Mask email addresses from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     */
    private Boolean emailAddress = null;

    /**
     * Mask credit card numbers and credit card track numbers from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     */
    private Boolean creditCard = null;
    /**
     * Mask phone numbers from the scanned document with # characters. Available for users on a plan for 2500 pages or more.
     */
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
