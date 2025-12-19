package academy.entity;

public record AppConfig<T> (
    Class<T> clazz,
    FormatType formatType
) {
}
