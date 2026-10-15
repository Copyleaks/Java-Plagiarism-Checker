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
 * Languages for cross-language plagiarism scans, as maps from language name to ISO-639-1 code, for example
 * {@code {"english": "en"}}. The {@code ...Codes()} getters return the codes only.
 */
public class CrossLanguagesResponse {

    @JsonAdapter(LanguagesAdapter.class)
    private Map<String, String> documentLanguages;

    @JsonAdapter(LanguagesAdapter.class)
    private Map<String, String> resultLanguages;

    /** Languages a scanned document may be in. */
    public Map<String, String> getDocumentLanguages() {
        return documentLanguages == null ? null : Collections.unmodifiableMap(documentLanguages);
    }

    /** Languages that can be searched for translated matches. */
    public Map<String, String> getResultLanguages() {
        return resultLanguages == null ? null : Collections.unmodifiableMap(resultLanguages);
    }

    /** ISO-639-1 codes of the languages a scanned document may be in, or null. */
    public String[] getDocumentLanguageCodes() {
        return documentLanguages == null ? null : documentLanguages.values().toArray(new String[0]);
    }

    /** ISO-639-1 codes of the languages that can be searched for translated matches, or null. */
    public String[] getResultLanguageCodes() {
        return resultLanguages == null ? null : resultLanguages.values().toArray(new String[0]);
    }

    /** Reads a language object ({@code {"english": "en"}}) or a code array ({@code ["en"]}, code used as name). */
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
