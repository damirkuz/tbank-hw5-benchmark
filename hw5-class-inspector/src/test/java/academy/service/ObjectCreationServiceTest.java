package academy.service;

import academy.sample.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectCreationServiceTest {
    private ObjectCreationService creationService;

    @BeforeEach
    void setUp() {
        creationService = new ObjectCreationService();
    }

    @Test
    void create_shouldCreatePersonInstance() {
        Person result = creationService.create(Person.class);

        assertThat(result).isNotNull();
    }

    @Test
    void create_shouldPopulateFields() {
        Person result = creationService.create(Person.class);

        assertThat(result.getName()).isNotNull();
        assertThat(result.getAge()).isNotZero();
    }

    @Test
    void create_shouldCreateStringInstance() {
        String result = creationService.create(String.class);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void create_shouldCreateIntegerInstance() {
        Integer result = creationService.create(Integer.class);

        assertThat(result).isNotNull();
    }
}
