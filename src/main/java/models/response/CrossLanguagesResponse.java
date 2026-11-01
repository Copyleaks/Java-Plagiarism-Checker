package models.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
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
    private Languages documentLanguages;

    @JsonAdapter(LanguagesAdapter.class)
    private Languages resultLanguages;

    /** Languages a scanned document may be in. Null when the response lists codes only. */
    public Map<String, String> getDocumentLanguages() {
        return documentLanguages == null ? null : documentLanguages.names();
    }

    /** Languages that can be searched for translated matches. Null when the response lists codes only. */
    public Map<String, String> getResultLanguages() {
        return resultLanguages == null ? null : resultLanguages.names();
    }

    /** ISO-639-1 codes of the languages a scanned document may be in, or null. */
    public String[] getDocumentLanguageCodes() {
        return documentLanguages == null ? null : documentLanguages.codes();
    }

    /** ISO-639-1 codes of the languages that can be searched for translated matches, or null. */
    public String[] getResultLanguageCodes() {
        return resultLanguages == null ? null : resultLanguages.codes();
    }

    /** One language list: a name-to-code map, or only the codes when the response is a code array. */
    static final class Languages {
        private final Map<String, String> byName;
        private final List<String> codes;

        Languages(Map<String, String> byName, List<String> codes) {
            this.byName = byName;
            this.codes = codes;
        }

        Map<String, String> names() {
            return byName == null ? null : Collections.unmodifiableMap(byName);
        }

        String[] codes() {
            return byName == null ? codes.toArray(new String[0]) : byName.values().toArray(new String[0]);
        }
    }

    /** Reads a language object ({@code {"english": "en"}}) or a code array ({@code ["en"]}). */
    static final class LanguagesAdapter extends TypeAdapter<Languages> {
        @Override
        public void write(JsonWriter out, Languages value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            if (value.byName == null) {
                out.beginArray();
                for (String code : value.codes) {
                    out.value(code);
                }
                out.endArray();
                return;
            }
            out.beginObject();
            for (Map.Entry<String, String> e : value.byName.entrySet()) {
                out.name(e.getKey()).value(e.getValue());
            }
            out.endObject();
        }

        @Override
        public Languages read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            if (token == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            if (token == JsonToken.BEGIN_ARRAY) {
                List<String> codes = new ArrayList<>();
                in.beginArray();
                while (in.hasNext()) {
                    codes.add(in.nextString());
                }
                in.endArray();
                return new Languages(null, codes);
            }
            Map<String, String> byName = new LinkedHashMap<>();
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                byName.put(name, in.nextString());
            }
            in.endObject();
            return new Languages(byName, null);
        }
    }
}
