package academy.exception.handler;

import academy.exception.ClassNotFoundInspectorException;
import academy.exception.UnsupportedFormatException;
import picocli.CommandLine.IExitCodeExceptionMapper;

public class ExitCodeMapper implements IExitCodeExceptionMapper {

    @Override
    public int getExitCode(Throwable t) {
        //        - `0` - программа успешно завершила свою работу
        //        - `1` - непредвиденная ошибка
        //        - `2` - некорректное использование программы (неверные параметры, отсутствие файлов и т.д.)

        if (t instanceof ClassNotFoundInspectorException
                || t instanceof UnsupportedFormatException
                || t instanceof IllegalArgumentException) {
            return 2;
        }
        return 1;
    }
}
