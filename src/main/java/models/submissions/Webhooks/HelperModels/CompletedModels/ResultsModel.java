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
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.submissions.Webhooks.HelperModels.CompletedModels;

import models.submissions.Webhooks.HelperModels.ResultsModels.BatchModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.DatabaseModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.InternetModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.RepositoriesModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.ScoreModel;

public class ResultsModel {

    private DatabaseModel[] database;
    private BatchModel[] batch;
    private RepositoriesModel[] repositories;
    private ScoreModel score;
    private InternetModel[] internet;

    public DatabaseModel[] getDatabase() {
        return database;
    }

    public BatchModel[] getBatch() {
        return batch;
    }

    public RepositoriesModel[] getRepositories() {
        return repositories;
    }

    public ScoreModel getScore() {
        return score;
    }

    public InternetModel[] getInternet() {
        return internet;
    }

}