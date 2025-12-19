package academy.service;

import academy.exception.ObjectCreationException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ObjectCreationService {
    private static final int MAX_DEPTH = 3;
    private static final int MAX_COLLECTION_SIZE = 5;
    private static final Random RANDOM = new Random();

    public <T> T create(Class<T> clazz) {
        return create(clazz, 0, new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> clazz, int depth, Map<Class<?>, Object> cache) {
        if (depth > MAX_DEPTH) {
            return null;
        }

        if (cache.containsKey(clazz)) {
            return (T) cache.get(clazz);
        }

        if (clazz.isPrimitive() || isWrapperType(clazz)) {
            return createPrimitive(clazz);
        }

        if (clazz == String.class) {
            return (T) generateRandomString();
        }

        if (clazz.isEnum()) {
            return createEnum(clazz);
        }

        if (clazz.isArray()) {
            return createArray(clazz, depth, cache);
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return (T) createCollection(clazz);
        }

        if (Map.class.isAssignableFrom(clazz)) {
            return (T) createMap();
        }

        if (isDateType(clazz)) {
            return createDate(clazz);
        }

        return createObject(clazz, depth, cache);
    }

    @SuppressWarnings("unchecked")
    private <T> T createPrimitive(Class<T> clazz) {
        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(RANDOM.nextBoolean());
        }
        if (clazz == byte.class || clazz == Byte.class) {
            return (T) Byte.valueOf((byte) RANDOM.nextInt());
        }
        if (clazz == short.class || clazz == Short.class) {
            return (T) Short.valueOf((short) RANDOM.nextInt());
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(RANDOM.nextInt(1000));
        }
        if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(RANDOM.nextLong());
        }
        if (clazz == float.class || clazz == Float.class) {
            return (T) Float.valueOf(RANDOM.nextFloat());
        }
        if (clazz == double.class || clazz == Double.class) {
            return (T) Double.valueOf(RANDOM.nextDouble());
        }
        if (clazz == char.class || clazz == Character.class) {
            return (T) Character.valueOf((char) (RANDOM.nextInt(26) + 'a'));
        }
        return null;
    }

    private String generateRandomString() {
        int length = RANDOM.nextInt(10) + 5;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) (RANDOM.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    private <T> T createEnum(Class<T> clazz) {
        T[] constants = clazz.getEnumConstants();
        if (constants != null && constants.length > 0) {
            return constants[RANDOM.nextInt(constants.length)];
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T createArray(Class<T> clazz, int depth, Map<Class<?>, Object> cache) {
        Class<?> componentType = clazz.getComponentType();
        int length = RANDOM.nextInt(MAX_COLLECTION_SIZE) + 1;
        Object array = Array.newInstance(componentType, length);

        for (int i = 0; i < length; i++) {
            Object element = create(componentType, depth + 1, cache);
            Array.set(array, i, element);
        }

        return (T) array;
    }

    private Collection<Object> createCollection(Class<?> clazz) {
        Collection<Object> collection;
        if (clazz.isInterface()) {
            if (List.class.isAssignableFrom(clazz)) {
                collection = new ArrayList<>();
            } else if (Set.class.isAssignableFrom(clazz)) {
                collection = new HashSet<>();
            } else {
                collection = new ArrayList<>();
            }
        } else {
            try {
                collection = (Collection<Object>) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                collection = new ArrayList<>();
            }
        }

        int size = RANDOM.nextInt(MAX_COLLECTION_SIZE) + 1;
        for (int i = 0; i < size; i++) {
            collection.add(generateRandomString());
        }

        return collection;
    }

    private Map<Object, Object> createMap() {
        Map<Object, Object> map = new HashMap<>();
        int size = RANDOM.nextInt(MAX_COLLECTION_SIZE) + 1;
        for (int i = 0; i < size; i++) {
            map.put("key" + i, generateRandomString());
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private <T> T createDate(Class<T> clazz) {
        if (clazz == Date.class) {
            return (T) new Date();
        }
        if (clazz == LocalDate.class) {
            return (T) LocalDate.now();
        }
        if (clazz == LocalDateTime.class) {
            return (T) LocalDateTime.now();
        }
        return null;
    }

    private <T> T createObject(Class<T> clazz, int depth, Map<Class<?>, Object> cache) {
        try {
            Constructor<T> constructor = findConstructor(clazz);
            T instance = constructor.newInstance(createConstructorParams(constructor, depth, cache));

            cache.put(clazz, instance);

            populateFields(instance, depth, cache);

            return instance;
        } catch (Exception e) {
            throw new ObjectCreationException("Не удалось создать экземпляр класса: " + clazz.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Constructor<T> findConstructor(Class<T> clazz) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length > 0) {
                Constructor<?> constructor = constructors[0];
                constructor.setAccessible(true);
                return (Constructor<T>) constructor;
            }
            throw e;
        }
    }

    private Object[] createConstructorParams(Constructor<?> constructor, int depth, Map<Class<?>, Object> cache) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = create(paramTypes[i], depth + 1, cache);
        }

        return params;
    }

    private void populateFields(Object instance, int depth, Map<Class<?>, Object> cache) {
        Class<?> clazz = instance.getClass();

        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    Object value = create(field.getType(), depth + 1, cache);
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new ObjectCreationException("Не удалось установить значение поля: " + field.getName(), e);
                } catch (SecurityException e) {
                    continue;
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private boolean isWrapperType(Class<?> clazz) {
        return clazz == Boolean.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == Character.class;
    }

    private boolean isDateType(Class<?> clazz) {
        return clazz == Date.class || clazz == LocalDate.class || clazz == LocalDateTime.class;
    }
}
