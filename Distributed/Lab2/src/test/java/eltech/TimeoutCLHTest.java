package eltech;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimeoutCLHTest {
    static TimeoutCLH lock = new TimeoutCLH();
    static ExecutorService executor;
    double res = 0;
    int count = 1;

    public double main(String[] args) throws InterruptedException {
        executor = Executors.newFixedThreadPool(Integer.valueOf(args[0]));

        Runnable runnable = Boolean.valueOf(args[3]) ? () -> {
            res = Math.pow(2, count++);
        } : () -> {
            try {
                lock.tryLock(Integer.valueOf(args[2]), TimeUnit.SECONDS);
                res = Math.pow(2, count++);
            } finally {
                lock.unlock();
            }
        };

        Callable<Object> callable = Executors.callable(runnable);

        List<Callable<Object>> callableTasks = new ArrayList<>();

        for (int i = 0; i < Integer.valueOf(args[1]); i++) {
            callableTasks.add(callable);
        }

        executor.invokeAll(callableTasks);
        executor.shutdown();

        return res;
    }
}