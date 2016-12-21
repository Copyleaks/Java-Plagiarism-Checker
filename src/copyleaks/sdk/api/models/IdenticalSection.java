package copyleaks.sdk.api.models;

import com.google.gson.annotations.SerializedName;

public class IdenticalSection
{
	public int getWordsCount()
	{
		return wordsCount;
	}

	public int getSourceStartPos()
	{
		return sourceStartPos;
	}

	public int getSourceEndPos()
	{
		return sourceEndPos;
	}

	public int getSuspectedStartPos()
	{
		return suspectedStartPos;
	}

	public int getSuspectedEndPos()
	{
		return suspectedEndPos;
	}

	@SerializedName("WC")
	private int wordsCount;

	@SerializedName("SoS")
	private int sourceStartPos;

	@SerializedName("SoE")
	private int sourceEndPos;

	@SerializedName("SuS")
	private int suspectedStartPos;
	
	@SerializedName("SuE")
	private int suspectedEndPos;

}