/********************************************************************************
 The MIT License(MIT)
 
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
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
********************************************************************************/

package copyleaks.sdk.api.models;

import com.google.gson.annotations.SerializedName;

public class ResultRecord implements Comparable<ResultRecord>
{
	public ResultRecord(String URL, int Percents, int NumberOfCopiedWords, String CachedVersion,
			String ComparisonReport, String Title, String Introduction, String EmbededComparison)
	{
		this.URL = URL;
		this.Percents = Percents;
		this.NumberOfCopiedWords = NumberOfCopiedWords;
		this.CachedVersion = CachedVersion;
		this.ComparisonReport = ComparisonReport;
		this.Title = Title;
		this.Introduction = Introduction;
		this.EmbededComparison = EmbededComparison;
	}

	public ResultRecord()
	{
		// For serialization.
	}

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

	@SerializedName("ComparisonReport")

	private String ComparisonReport;

	public String getComparisonReport()
	{
		return ComparisonReport;
	}

	@SerializedName("CachedVersion")

	private String CachedVersion;

	public String getCachedVersion()
	{
		return CachedVersion;
	}

	@SerializedName("Title")

	private String Title;

	public String getTitle()
	{
		return Title;
	}

	@SerializedName("Introduction")

	private String Introduction;

	public String getIntroduction()
	{
		return Introduction;
	}

	@SerializedName("EmbededComparison")

	private String EmbededComparison;

	public String getEmbededComparison()
	{
		return EmbededComparison;
	}

	@Override
	public int compareTo(ResultRecord o)
	{
		return Integer.compare(this.getPercents(), o.getPercents());
	}
}
