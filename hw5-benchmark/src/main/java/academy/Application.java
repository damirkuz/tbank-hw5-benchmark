package academy;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Запуск бенчмарков производительности");

        Options options = new OptionsBuilder()
                .include(MethodInvocationBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.TEXT)
                .result("benchmark-results.txt")
                .build();

        try {
            new Runner(options).run();
            log.info("Бенчмарки успешно завершены. Результаты сохранены в benchmark-results.txt");
        } catch (RunnerException e) {
            log.error("Ошибка при выполнении бенчмарков", e);
            System.exit(1);
        }
    }
}
