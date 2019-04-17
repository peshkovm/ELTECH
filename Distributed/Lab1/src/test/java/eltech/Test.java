package eltech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

public class Test {
    static ExecutorService executor;
    final AtomicInteger a = new AtomicInteger(0);
    Random rand = new Random();

    static class Value<T> {
        T a;
        String str;

        public Value(T a, String str) {
            this.a = a;
            this.str = str;
        }

        @Override
        public String toString() {
            return str + " " + a;
        }
    }

    public long main(int read_cont, int write_count, Register register) throws InterruptedException {
        System.out.println(register.getClass());
        System.out.println();

        Callable<Value<Object>> read;
        Callable<Value<Object>> write;

        if (register instanceof RegisterBoolean) {
            read = () -> {
                Boolean a = (Boolean) register.read();
                return new Value<>(a, "read");
            };

            write = () -> {
                boolean b = rand.nextBoolean();
                register.write(b);
                return new Value<>(b, "write");
            };
        } else {
            read = () -> {
                Integer a = (Integer) register.read();
                return new Value<>(a, "read");
            };

            write = () -> {
                int b = a.getAndIncrement();
                register.write(b);
                return new Value<>(b, "write");
            };
        }

        return main(read_cont, write_count, register, read, write);
    }

    public <T> long main(int read_cont, int write_count, Register<T> register, Callable<Value<T>> read, Callable<Value<T>> write) throws InterruptedException {
        executor = Executors.newFixedThreadPool(read_cont + write_count);

        List<Callable<Value<T>>> callableTasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            callableTasks.add(read);
        }

        for (int i = 0; i < 10; i++) {
            callableTasks.add(write);
        }

        Collections.shuffle(callableTasks);

        long starttime;
        long endtime;
        starttime = System.currentTimeMillis();

        List<Future<Value<T>>> futures = executor.invokeAll(callableTasks);
        endtime = System.currentTimeMillis();
        executor.shutdown();

        futures.forEach(v -> {
            try {
                System.out.println(v.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return endtime - starttime;
    }
}