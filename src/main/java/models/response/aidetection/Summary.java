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

package models.response.aidetection;

import com.google.gson.annotations.SerializedName;

public class Summary {
    /**
     * Share of the text classified as human-written. Range: 0.0-1.0.
     * Also read from the PascalCase key {@code Human} sent by sandbox scans.
     */
    @SerializedName(value = "human", alternate = {"Human"})
    private double human;

    /**
     * Share of the text classified as AI-generated. Range: 0.0-1.0.
     * Also read from the PascalCase key {@code Ai} sent by sandbox scans.
     */
    @SerializedName(value = "ai", alternate = {"Ai"})
    private double ai;

    public double getHuman() {
        return human;
    }

    public double getAi() {
        return ai;
    }
}
