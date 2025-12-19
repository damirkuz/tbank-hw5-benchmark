package academy.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;


public class CommandLineExceptionHandler implements CommandLine.IParameterExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandLineExceptionHandler.class);

    public int handleParseException(ParameterException ex, String[] args) {
        CommandLine cmd = ex.getCommandLine();
        CommandSpec spec = cmd.getCommandSpec();

        log.warn("Ошибка командной строки: {}", ex.getMessage());
        System.err.println(ex.getMessage());
        System.err.println();
        System.err.println("Использование:");
        cmd.usage(System.err);

        return 2;
    }
}
