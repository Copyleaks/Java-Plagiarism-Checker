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

package models.response;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * The languages supported by cross-language plagiarism scans.
 * <p>
 * The API returns each list as an object that maps the language name to its ISO-639-1 code,
 * for example {@code {"english": "en", "spanish": "es"}}. Use the {@code ...Codes()} getters for the codes only.
 */
public class CrossLanguagesResponse {

    /** The languages a scanned document may be in: language name to ISO-639-1 code. */
    @JsonAdapter(LanguagesAdapter.class)
    private Map<String, String> documentLanguages;

    /** The languages that can be searched for translated matches: language name to ISO-639-1 code. */
    @JsonAdapter(LanguagesAdapter.class)
    private Map<String, String> resultLanguages;

    /** @return the languages a scanned document may be in, from language name to ISO-639-1 code. */
    public Map<String, String> getDocumentLanguages() {
        return documentLanguages == null ? null : Collections.unmodifiableMap(documentLanguages);
    }

    /** @return the languages that can be searched for translated matches, from language name to ISO-639-1 code. */
    public Map<String, String> getResultLanguages() {
        return resultLanguages == null ? null : Collections.unmodifiableMap(resultLanguages);
    }

    /** @return the ISO-639-1 codes of the languages a scanned document may be in, or null. */
    public String[] getDocumentLanguageCodes() {
        return documentLanguages == null ? null : documentLanguages.values().toArray(new String[0]);
    }

    /** @return the ISO-639-1 codes of the languages that can be searched for translated matches, or null. */
    public String[] getResultLanguageCodes() {
        return resultLanguages == null ? null : resultLanguages.values().toArray(new String[0]);
    }

    /**
     * Reads a language list sent as an object ({@code {"english": "en"}}) or as an array of codes
     * ({@code ["en"]}). For an array, each code is also used as the name.
     */
    static final class LanguagesAdapter extends TypeAdapter<Map<String, String>> {
        @Override
        public void write(JsonWriter out, Map<String, String> value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            for (Map.Entry<String, String> e : value.entrySet()) {
                out.name(e.getKey()).value(e.getValue());
            }
            out.endObject();
        }

        @Override
        public Map<String, String> read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            if (token == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Map<String, String> languages = new LinkedHashMap<>();
            if (token == JsonToken.BEGIN_ARRAY) {
                in.beginArray();
                while (in.hasNext()) {
                    String code = in.nextString();
                    languages.put(code, code);
                }
                in.endArray();
            } else {
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    languages.put(name, in.nextString());
                }
                in.endObject();
            }
            return languages;
        }
    }
}
