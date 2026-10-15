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
 * Gson adapter for {@link CopyleaksIntegerEnum}: writes the integer value, reads the integer or the constant name.
 * The SDK enums use it through {@code @JsonAdapter}, so any {@link Gson} instance, including yours, applies it.
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
