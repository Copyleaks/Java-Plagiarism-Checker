package copyleaks.sdk.api.models;

import com.google.gson.annotations.SerializedName;

public class ResultRecord {

	@SerializedName("URL")

	private String URL;

	public String getURL() {
		return URL;
	}

	@SerializedName("Percents")

	private int Percents;

	public int getPercents() {
		return Percents;
	}

	@SerializedName("NumberOfCopiedWords")

	private int NumberOfCopiedWords;

	public int getNumberOfCopiedWords() {
		return NumberOfCopiedWords;
	}
}
