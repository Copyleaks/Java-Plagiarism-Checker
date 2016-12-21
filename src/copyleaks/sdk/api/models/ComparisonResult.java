package copyleaks.sdk.api.models;

import com.google.gson.annotations.SerializedName;

public class ComparisonResult
{
	public IdenticalSection[] getIdenticalSections()
	{
		return identicalSections;
	}

	public int getIdenticalCopiedWords()
	{
		return identicalCopiedWords;
	}

	public int getTotalWords()
	{
		return totalWords;
	}

	@SerializedName("Identical")
	private IdenticalSection[] identicalSections;

	@SerializedName("IdenticalCopiedWords")
	private int identicalCopiedWords;

	@SerializedName("TotalWords")
	private int totalWords;
}