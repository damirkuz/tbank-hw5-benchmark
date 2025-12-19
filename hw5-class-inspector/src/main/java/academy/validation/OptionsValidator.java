package academy.validation;

import academy.app.CliOptions;

public class OptionsValidator {

    public static void validate(CliOptions options) {
        if (options.className() == null || options.className().isEmpty()) {
            throw new IllegalArgumentException(
                "Параметр --class обязателен для заполнения"
            );
        }
    }
}
