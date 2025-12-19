package academy;

import academy.sample.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassInspectorTest {

    @Test
    void inspect_withTextFormat_shouldReturnFormattedString() {
        String result = ClassInspector.inspect(Person.class, "TEXT");

        assertThat(result).isNotNull();
        assertThat(result).contains("Class: academy.sample.Person");
        assertThat(result).contains("Superclass: Human");
        assertThat(result).contains("Interfaces:");
        assertThat(result).contains("Serializable");
    }

    @Test
    void inspect_withJsonFormat_shouldReturnJsonString() {
        String result = ClassInspector.inspect(Person.class, "JSON");

        assertThat(result).isNotNull();
        assertThat(result).contains("\"className\"");
        assertThat(result).contains("\"academy.sample.Person\"");
        assertThat(result).contains("\"superclass\"");
    }

    @Test
    void inspect_withNullFormat_shouldUseTextFormat() {
        String result = ClassInspector.inspect(Person.class, null);

        assertThat(result).isNotNull();
        assertThat(result).contains("Class: academy.sample.Person");
    }

    @Test
    void create_shouldReturnNewInstance() {
        Person result = ClassInspector.create(Person.class);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(Person.class);
    }

    @Test
    void create_shouldPopulateFieldsWithRandomValues() {
        Person result = ClassInspector.create(Person.class);

        assertThat(result.getName()).isNotNull();
    }
}
