package academy.exception.handler;

import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public class CommandLineExceptionHandler implements CommandLine.IParameterExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandLineExceptionHandler.class);

    public int handleParseException(CommandLine.ParameterException ex, String[] args) {
        PrintWriter err = ex.getCommandLine().getErr();
        err.println(ex.getMessage());
        log.warn("Ошибка командной строки: {}", ex.getMessage());
        return ex.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
    }
}
