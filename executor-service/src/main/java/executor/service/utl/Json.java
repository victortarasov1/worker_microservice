package executor.service.utl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class Json {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T> T[] parseToArray(InputStream inputStream, Class<T> tClass)  {
        try {
            return parseToArrayOrThrow(inputStream, tClass);
        } catch (IOException e) {
            System.err.println("Json -> parse: " + e.getMessage()); //todo create logger later
            return (T[]) Array.newInstance(tClass, 0);
        }
    }

    public static <T> T[] parseToArrayOrThrow(InputStream inputStream, Class<T> tClass) throws IOException {
            return objectMapper.readerForArrayOf(tClass).readValue(inputStream);

    }
}
