package copyleaks.sdk.api.models;

import com.google.gson.annotations.SerializedName;

public class ResultRecord implements Comparable<ResultRecord>
{
	@SerializedName("URL")

	private String URL;

	public String getURL()
	{
		return URL;
	}

	@SerializedName("Percents")

	private int Percents;

	public int getPercents()
	{
		return Percents;
	}

	@SerializedName("NumberOfCopiedWords")

	private int NumberOfCopiedWords;

	public int getNumberOfCopiedWords()
	{
		return NumberOfCopiedWords;
	}
	
	@Override
	public int compareTo(ResultRecord o) {
		return Integer.compare(this.getPercents(), o.getPercents());
    }
}
