package academy.app;

import academy.exception.handler.CommandLineExceptionHandler;
import academy.exception.handler.ExitCodeMapper;
import academy.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "academy.ClassInspector run", version = "1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Option(
            names = {"--class", "-c"},
            description = "Полное имя класса")
    String className;

    @Option(
            names = {"--format", "-f"},
            description = "Формат вывода результатов")
    String format;

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Application());
        cmd.setParameterExceptionHandler(new CommandLineExceptionHandler());
        cmd.setExitCodeExceptionMapper(new ExitCodeMapper());

        try {
            int exitCode = cmd.execute(args);
            System.exit(exitCode);
        } catch (Exception e) {
            log.error("Непредвиденная ошибка при запуске приложения", e);
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            CliOptions options = new CliOptions(className, format);
            new MainService().start(options);
        } catch (IllegalArgumentException e) {
            log.error("Ошибка валидации параметров: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Ошибка выполнения программы", e);
            throw new RuntimeException(e);
        }
    }
}
