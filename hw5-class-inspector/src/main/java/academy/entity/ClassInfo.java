package academy.entity;

import java.util.List;
import java.util.Map;

public record ClassInfo(
    String className,
    String superclass,
    List<String> interfaces,
    List<FieldInfo> fields,
    List<MethodInfo> methods,
    List<String> annotations,
    Map<String, Object> hierarchy
) {
}
