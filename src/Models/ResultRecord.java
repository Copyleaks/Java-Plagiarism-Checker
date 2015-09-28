package Models;

import com.google.gson.annotations.SerializedName;

public class ResultRecord {
	@SerializedName("Domain")
	public String Domain;

	String getDomain() {
		return Domain;
	}

	private void setDomain(String domain) {
		Domain = domain;
	}

	@SerializedName("URL")

	public String URL;

	String getURL() {
		return URL;
	}

	private void setURL(String url) {
		URL = url;
	}

	@SerializedName("Precents")

	public int Precents;

	int getPrecents() {
		return Precents;
	}

	private void setPrecents(int precents) {
		Precents = precents;
	}

	@SerializedName("NumberOfCopiedWords")

	public int NumberOfCopiedWords;

	public int getNumberOfCopiedWords() {
		return NumberOfCopiedWords;
	}

	private void setNumberOfCopiedWords(int numberOfCopiedWords) {
		NumberOfCopiedWords = numberOfCopiedWords;
	}

}
