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

package helpers;

import java.io.IOException;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Gson support for {@link CopyleaksIntegerEnum} enums. They are written as their integer value,
 * for example {@code "action": 2}. When reading, both the integer and the constant name are accepted.
 * <p>
 * The SDK enums are annotated with {@code @JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)},
 * so any {@link Gson} instance, including your own, uses it.
 */
public final class CopyleaksIntegerEnumAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (!raw.isEnum() || !CopyleaksIntegerEnum.class.isAssignableFrom(raw)) {
            return null;
        }
        return (TypeAdapter<T>) new Adapter((Class) raw);
    }

    private static final class Adapter<E extends Enum<E> & CopyleaksIntegerEnum> extends TypeAdapter<E> {
        private final Class<E> type;

        Adapter(Class<E> type) {
            this.type = type;
        }

        @Override
        public void write(JsonWriter out, E value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.getValue());
            }
        }

        @Override
        public E read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            if (token == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String text = in.nextString();
            for (E constant : type.getEnumConstants()) {
                if (Integer.toString(constant.getValue()).equals(text)
                        || constant.name().toLowerCase(Locale.ROOT).equals(text.toLowerCase(Locale.ROOT))) {
                    return constant;
                }
            }
            throw new JsonParseException("Unknown " + type.getSimpleName() + " value: " + text);
        }
    }
}
