# JMH-бенчмарки и ClassInspector

Учебный проект №5 курса backend-разработки Т-Банка (ИТИС): два модуля — микробенчмарки на JMH, сравнивающие разные способы вызова метода и конкатенации строк, и консольная утилита ClassInspector на Reflection API, которая анализирует структуру класса и генерирует заполненные экземпляры объектов.

## Модуль hw5-benchmark

Бенчмарки JMH 1.37 (AverageTime, ns/op, прогрев и замеры 5 × 2 с, Blackhole против оптимизаций JIT):

- `MethodInvocationBenchmark` — четыре способа вызова `Student#name()`:
  1. прямой вызов
  2. `java.lang.reflect.Method` (`clazz.getMethod(...)`)
  3. `MethodHandles` (`MethodHandles.lookup().findVirtual(...)`)
  4. `LambdaMetafactory.metafactory(...)`
- `StringConcatBenchmark` — конкатенация строк оператором `+` против `StringBuilder`.

Фактические результаты зафиксированы в `hw5-benchmark/benchmark-results.txt`:

```text
Benchmark                                              Mode  Cnt  Score   Error  Units
MethodInvocationBenchmark.directInvocation             avgt    5  0,647 ± 0,025  ns/op
MethodInvocationBenchmark.lambdaMetafactoryInvocation  avgt    5  0,949 ± 0,220  ns/op
MethodInvocationBenchmark.methodHandleInvocation       avgt    5  3,172 ± 0,447  ns/op
MethodInvocationBenchmark.reflectionInvocation         avgt    5  6,044 ± 0,366  ns/op
```

## Модуль hw5-class-inspector

Консольная утилита на picocli:

- `--class, -c` — полное имя класса; `--format, -f` — формат вывода `TEXT` или `JSON` (Jackson).
- Анализ класса: имя, суперкласс, интерфейсы, поля (модификаторы, имена, типы), методы (модификаторы, параметры, возвращаемый тип), аннотации класса и членов, дерево иерархии наследования.
- `create(Class<T>)` — генерация объекта со случайными значениями полей (аналог `Instancio.create`): строки, числа, boolean, даты, коллекции, массивы, рекурсивные вложенные объекты с ограничением глубины.
- Fail-fast: ошибки транслируются в коды возврата — `0` успех, `1` непредвиденная ошибка, `2` неверные параметры запуска.
- Логирование Log4j2, юнит-тесты (JUnit, AssertJ).

## Технологии

- Java 24, многомодульный Maven (wrapper)
- JMH 1.37, picocli 4.7.7, Jackson 2.19.2, Log4j2 2.25.1
- Тесты: JUnit Jupiter, AssertJ, Awaitility, Instancio; JaCoCo
- Статический анализ: SpotBugs, PMD, Spotless; CI — GitLab CI

## Запуск

```bash
./mvnw clean package -DskipTests

# бенчмарки (результаты дописываются в hw5-benchmark/benchmark-results.txt)
./mvnw -pl hw5-benchmark compile exec:java -Dexec.mainClass=academy.Application

# инспектор
./mvnw -pl hw5-class-inspector compile exec:java \
  -Dexec.mainClass=academy.app.Application \
  -Dexec.args="--class academy.sample.Employee --format TEXT"

# тесты
./mvnw test
```

## Структура проекта

```
hw5-benchmark/
  src/main/java/academy/            # Application (JMH Runner), MethodInvocationBenchmark, StringConcatBenchmark
  benchmark-results.txt             # итоговая таблица замеров
hw5-class-inspector/
  src/main/java/academy/
    app/                            # точка входа на picocli (Application, CliOptions)
    service/                        # анализ класса и генерация объектов
    entity/                         # ClassInfo, MethodInfo, FieldInfo, FormatType
    util/                           # текстовый и JSON-форматтеры
    exception/, validation/         # доменные исключения, маппинг кодов выхода
    sample/                         # демо-иерархия Human → Person → Employee → Manager
```
