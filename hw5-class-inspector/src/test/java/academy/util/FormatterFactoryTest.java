package academy.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.exception.UnsupportedFormatException;
import org.junit.jupiter.api.Test;

class FormatterFactoryTest {

    @Test
    void getFormatter_withTextFormat_shouldReturnTextFormatter() {
        Formatter result = FormatterFactory.getFormatter("TEXT");

        assertThat(result).isInstanceOf(TextFormatter.class);
    }

    @Test
    void getFormatter_withJsonFormat_shouldReturnJsonFormatter() {
        Formatter result = FormatterFactory.getFormatter("JSON");

        assertThat(result).isInstanceOf(JsonFormatter.class);
    }

    @Test
    void getFormatter_withNullFormat_shouldReturnTextFormatter() {
        Formatter result = FormatterFactory.getFormatter(null);

        assertThat(result).isInstanceOf(TextFormatter.class);
    }

    @Test
    void getFormatter_withInvalidFormat_shouldThrowException() {
        assertThatThrownBy(() -> FormatterFactory.getFormatter("XML"))
                .isInstanceOf(UnsupportedFormatException.class)
                .hasMessageContaining("Неподдерживаемый формат");
    }
}
