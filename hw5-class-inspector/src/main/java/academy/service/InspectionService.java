package academy.service;

import academy.entity.ClassInfo;
import academy.entity.FieldInfo;
import academy.entity.MethodInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InspectionService {

    public ClassInfo inspectClass(Class<?> clazz) {
        String className = clazz.getName();
        String superclass = getSuperclassName(clazz);
        List<String> interfaces = getInterfaces(clazz);
        List<FieldInfo> fields = getFields(clazz);
        List<MethodInfo> methods = getMethods(clazz);
        List<String> annotations = getAnnotations(clazz);
        Map<String, Object> hierarchy = buildHierarchy(clazz);

        return new ClassInfo(className, superclass, interfaces, fields, methods, annotations, hierarchy);
    }

    private String getSuperclassName(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        return superclass != null ? superclass.getSimpleName() : null;
    }

    private List<String> getInterfaces(Class<?> clazz) {
        return Arrays.stream(clazz.getInterfaces()).map(Class::getSimpleName).collect(Collectors.toList());
    }

    private List<FieldInfo> getFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new FieldInfo(
                        getAccessModifier(field.getModifiers()),
                        field.getName(),
                        field.getType().getSimpleName()))
                .collect(Collectors.toList());
    }

    private List<MethodInfo> getMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .map(method -> new MethodInfo(
                        getAccessModifier(method.getModifiers()),
                        method.getName(),
                        getParameterTypes(method),
                        method.getReturnType().getSimpleName()))
                .collect(Collectors.toList());
    }

    private List<String> getParameterTypes(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
    }

    private List<String> getAnnotations(Class<?> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .map(Annotation::annotationType)
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
    }

    private String getAccessModifier(int modifiers) {
        if (Modifier.isPublic(modifiers)) {
            return "public";
        } else if (Modifier.isProtected(modifiers)) {
            return "protected";
        } else if (Modifier.isPrivate(modifiers)) {
            return "private";
        } else {
            return "package-private";
        }
    }

    private Map<String, Object> buildHierarchy(Class<?> clazz) {
        Map<String, Object> hierarchy = new HashMap<>();

        Class<?> current = clazz;
        Map<String, Object> currentLevel = hierarchy;

        List<Class<?>> chain = new ArrayList<>();
        while (current != null && current != Object.class) {
            chain.addFirst(current);
            current = current.getSuperclass();
        }

        for (int i = 0; i < chain.size(); i++) {
            Class<?> c = chain.get(i);
            if (i == chain.size() - 1) {
                currentLevel.put(c.getSimpleName(), new HashMap<>());
            } else {
                Map<String, Object> nextLevel = new HashMap<>();
                currentLevel.put(c.getSimpleName(), nextLevel);
                currentLevel = nextLevel;
            }
        }

        return hierarchy;
    }
}
