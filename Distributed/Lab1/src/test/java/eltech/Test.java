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

    public long main(int read_cont, int write_count, RegisterBoolean register) throws InterruptedException {
        Runnable read = () -> {
            for (int i = 0; i < 10; i++) {
                Boolean a = register.read();
                System.out.println("i=" + " " + i + " " + "id=" + " " + Thread.currentThread().getId() + " " + "read" + " " + a);
            }
        };

        Runnable write = () -> {
            for (int i = 0; i < 10; i++) {
                boolean b = rand.nextBoolean();
                register.write(b);
                System.out.println("i=" + " " + i + " " + "id=" + " " + Thread.currentThread().getId() + " " + "write" + " " + b);
            }
        };

        return main(read_cont, write_count, register, read, write);
    }

    public long main(int read_cont, int write_count, RegisterInteger register) throws InterruptedException {
        Runnable read = () -> {
            for (int i = 0; i < 10; i++) {
                Integer a = register.read();
                System.out.println("i=" + " " + i + " " + "id=" + " " + Thread.currentThread().getId() + " " + "read" + " " + a);
            }
        };

        Runnable write = () -> {
            for (int i = 0; i < 10; i++) {
                int b = a.getAndIncrement();
                register.write(b);
                System.out.println("i=" + " " + i + " " + "id=" + " " + Thread.currentThread().getId() + " " + "write" + " " + b);
            }
        };

        return main(read_cont, write_count, register, read, write);
    }

    public <T> long main(int read_cont, int write_count, Register<T> register, Runnable read, Runnable write) throws InterruptedException {
        executor = Executors.newFixedThreadPool(read_cont + write_count);

        List<Callable<Object>> callableTasks = new ArrayList<>();

        Callable<Object> reads = Executors.callable(read);
        Callable<Object> writes = Executors.callable(write);

        for (int i = 0; i < read_cont; i++) {
            callableTasks.add(reads);
        }

        for (int i = 0; i < write_count; i++) {
            callableTasks.add(writes);
        }

        Collections.shuffle(callableTasks);

        long starttime;
        long endtime;
        starttime = System.currentTimeMillis();

        List<Future<Object>> futures = executor.invokeAll(callableTasks);
        endtime = System.currentTimeMillis();
        executor.shutdown();
        return endtime - starttime;
    }
}