package academy.service;

import static org.assertj.core.api.Assertions.assertThat;

import academy.entity.ClassInfo;
import academy.entity.FieldInfo;
import academy.entity.MethodInfo;
import academy.sample.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InspectionServiceTest {
    private InspectionService inspectionService;

    @BeforeEach
    void setUp() {
        inspectionService = new InspectionService();
    }

    @Test
    void inspectClass_shouldReturnClassInfo() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result).isNotNull();
        assertThat(result.className()).isEqualTo("academy.sample.Person");
        assertThat(result.superclass()).isEqualTo("Human");
    }

    @Test
    void inspectClass_shouldReturnInterfaces() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result.interfaces()).contains("Serializable");
    }

    @Test
    void inspectClass_shouldReturnFields() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result.fields()).extracting(FieldInfo::name).contains("name", "age");
    }

    @Test
    void inspectClass_shouldReturnMethods() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result.methods()).extracting(MethodInfo::name).contains("getName", "setName", "getAge", "setAge");
    }

    @Test
    void inspectClass_shouldReturnAnnotations() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result.annotations()).contains("Entity");
    }

    @Test
    void inspectClass_shouldReturnHierarchy() {
        ClassInfo result = inspectionService.inspectClass(Person.class);

        assertThat(result.hierarchy()).isNotEmpty();
        assertThat(result.hierarchy()).containsKey("Human");
    }
}
