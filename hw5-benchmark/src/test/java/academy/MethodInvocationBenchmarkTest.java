package academy;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.infra.Blackhole;

class MethodInvocationBenchmarkTest {

    @Test
    void setup_shouldInitializeAllFields() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();

        assertThatCode(benchmark::setup).doesNotThrowAnyException();
    }

    @Test
    void directInvocation_shouldExecuteWithoutErrors() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();
        benchmark.setup();

        Blackhole blackhole = new Blackhole(
                "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

        assertThatCode(() -> benchmark.directInvocation(blackhole)).doesNotThrowAnyException();
    }

    @Test
    void reflectionInvocation_shouldExecuteWithoutErrors() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();
        benchmark.setup();

        Blackhole blackhole = new Blackhole(
                "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

        assertThatCode(() -> benchmark.reflectionInvocation(blackhole)).doesNotThrowAnyException();
    }

    @Test
    void methodHandleInvocation_shouldExecuteWithoutErrors() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();
        benchmark.setup();

        Blackhole blackhole = new Blackhole(
                "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

        assertThatCode(() -> benchmark.methodHandleInvocation(blackhole)).doesNotThrowAnyException();
    }

    @Test
    void lambdaMetafactoryInvocation_shouldExecuteWithoutErrors() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();
        benchmark.setup();

        Blackhole blackhole = new Blackhole(
                "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

        assertThatCode(() -> benchmark.lambdaMetafactoryInvocation(blackhole)).doesNotThrowAnyException();
    }

    @Test
    void allBenchmarkMethods_shouldWorkWithSetup() throws Throwable {
        MethodInvocationBenchmark benchmark = new MethodInvocationBenchmark();
        benchmark.setup();

        Blackhole blackhole = new Blackhole(
                "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

        assertThatCode(() -> {
                    benchmark.directInvocation(blackhole);
                    benchmark.reflectionInvocation(blackhole);
                    benchmark.methodHandleInvocation(blackhole);
                    benchmark.lambdaMetafactoryInvocation(blackhole);
                })
                .doesNotThrowAnyException();
    }
}
