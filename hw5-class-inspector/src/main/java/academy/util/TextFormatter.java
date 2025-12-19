package academy.util;

import academy.entity.ClassInfo;
import academy.entity.FieldInfo;
import academy.entity.MethodInfo;
import java.util.Map;

public class TextFormatter implements Formatter {

    @Override
    public String format(ClassInfo classInfo) {
        StringBuilder sb = new StringBuilder();

        sb.append("Class: ").append(classInfo.className()).append("\n");

        if (classInfo.superclass() != null) {
            sb.append("Superclass: ").append(classInfo.superclass()).append("\n");
        }

        if (!classInfo.interfaces().isEmpty()) {
            sb.append("Interfaces:\n");
            for (String iface : classInfo.interfaces()) {
                sb.append("  - ").append(iface).append("\n");
            }
        }

        if (!classInfo.fields().isEmpty()) {
            sb.append("Fields:\n");
            for (FieldInfo field : classInfo.fields()) {
                sb.append("  - ")
                        .append(field.access())
                        .append(" ")
                        .append(field.name())
                        .append(" (")
                        .append(field.type())
                        .append(")\n");
            }
        }

        if (!classInfo.methods().isEmpty()) {
            sb.append("Methods:\n");
            for (MethodInfo method : classInfo.methods()) {
                sb.append("  - ")
                        .append(method.access())
                        .append(" ")
                        .append(method.name())
                        .append("(");
                sb.append(String.join(", ", method.params()));
                sb.append(") : ").append(method.returnType()).append("\n");
            }
        }

        if (!classInfo.annotations().isEmpty()) {
            sb.append("Annotations:\n");
            for (String annotation : classInfo.annotations()) {
                sb.append("  - @").append(annotation).append("\n");
            }
        }

        if (!classInfo.hierarchy().isEmpty()) {
            sb.append("Hierarchy:\n");
            formatHierarchy(sb, classInfo.hierarchy(), 0);
        }

        return sb.toString();
    }

    private void formatHierarchy(StringBuilder sb, Map<String, Object> hierarchy, int level) {
        for (Map.Entry<String, Object> entry : hierarchy.entrySet()) {
            sb.append("  ".repeat(level));
            if (level > 0) {
                sb.append("└── ");
            }
            sb.append(entry.getKey()).append("\n");

            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> subHierarchy = (Map<String, Object>) entry.getValue();
                formatHierarchy(sb, subHierarchy, level + 1);
            }
        }
    }
}
