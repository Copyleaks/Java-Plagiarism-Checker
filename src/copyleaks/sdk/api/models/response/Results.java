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

package copyleaks.sdk.api.models.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results implements Serializable
{

    @SerializedName("internet")
    @Expose
    private List<InternetResult> internet = null;
    @SerializedName("database")
    @Expose
    private List<DatabaseResult> database = null;
    @SerializedName("batch")
    @Expose
    private List<BatchResult> batch = null;
    @SerializedName("score")
    @Expose
    private Score score;
    private final static long serialVersionUID = 8486155764836277637L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Results() {
    }

    /**
     * The results of a scan
     * @param batch: The result for comparison between different documents 
     * @param score: The total score of all results
     * @param internet: A result from the Internet
     * @param database: A compare result from Copyleaks internal database
     */
    public Results(List<InternetResult> internet, List<DatabaseResult> database, List<BatchResult> batch, Score score) {
        super();
        this.internet = internet;
        this.database = database;
        this.batch = batch;
        this.score = score;
    }

    public List<InternetResult> getInternet() {
        return internet;
    }

    public void setInternet(List<InternetResult> internet) {
        this.internet = internet;
    }

    public List<DatabaseResult> getDatabase() {
        return database;
    }

    public void setDatabase(List<DatabaseResult> database) {
        this.database = database;
    }

    public List<BatchResult> getBatch() {
        return batch;
    }

    public void setBatch(List<BatchResult> batch) {
        this.batch = batch;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

}
