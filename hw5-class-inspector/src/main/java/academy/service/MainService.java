package academy.service;

import academy.ClassInspector;
import academy.app.CliOptions;
import academy.validation.OptionsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainService {
    private static final Logger log = LoggerFactory.getLogger(MainService.class);

    public void start(CliOptions options) {
        log.info("Начало обработки запроса для класса: {}", options.className());

        OptionsValidator.validate(options);

        Class<?> clazz = loadClass(options.className());
        String result = ClassInspector.inspect(clazz, options.format());

        System.out.println(result);

        log.info("Обработка завершена успешно");
    }

    private Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new academy.exception.ClassNotFoundInspectorException(
                "Класс не найден: " + className, e
            );
        }
    }
}
