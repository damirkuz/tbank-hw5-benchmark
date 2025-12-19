package academy.util;

import academy.entity.FormatType;
import academy.exception.InvalidFormatFlagException;
import academy.exception.UnsupportedFormatException;

public class FormatterFactory {

    public static Formatter getFormatter(String format) {
        if (format == null || format.isEmpty()) {
            return new TextFormatter();
        }

        FormatType formatType;

        try {
            formatType = FormatType.fromValue(format);
        } catch (InvalidFormatFlagException e) {
            throw new UnsupportedFormatException("Неподдерживаемый формат: " + format);
        }

        return switch (formatType) {
            case JSON -> new JsonFormatter();
            case TEXT -> new TextFormatter();
        };
    }
}
