package academy.util;

import academy.entity.ClassInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonFormatter implements Formatter {
    private final ObjectMapper objectMapper;

    public JsonFormatter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String format(ClassInfo classInfo) {
        try {
            return objectMapper.writeValueAsString(classInfo);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сериализации в JSON", e);
        }
    }
}
