package academy;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class MethodInvocationBenchmark {

    private Student student;
    private Method reflectionMethod;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaFunction;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Иван Иванов", 21);

        reflectionMethod = Student.class.getMethod("name");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        methodHandle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));

        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType invokedType = MethodType.methodType(Function.class);
        MethodType samMethodType = MethodType.methodType(Object.class, Object.class);
        MethodHandle target = caller.findVirtual(Student.class, "name", MethodType.methodType(String.class));
        MethodType instantiatedMethodType = MethodType.methodType(String.class, Student.class);

        lambdaFunction = (Function<Student, String>) LambdaMetafactory.metafactory(
                        caller, "apply", invokedType, samMethodType, target, instantiatedMethodType)
                .getTarget()
                .invokeExact();
    }

    @Benchmark
    public void directInvocation(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflectionInvocation(Blackhole bh) throws Exception {
        String name = (String) reflectionMethod.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandleInvocation(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactoryInvocation(Blackhole bh) {
        String name = lambdaFunction.apply(student);
        bh.consume(name);
    }
}
