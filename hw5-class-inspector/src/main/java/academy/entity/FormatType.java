package academy.entity;

import academy.exception.InvalidFormatFlagException;

public enum FormatType {
    TEXT("TEXT"),
    JSON("JSON");

    private final String value;

    FormatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormatType fromValue(String s) throws InvalidFormatFlagException {
        for (FormatType formatType : values()) {
            if (formatType.getValue().equalsIgnoreCase(s)) {
                return formatType;
            }
        }

        throw new InvalidFormatFlagException("Некорректный формат");
    }
}
