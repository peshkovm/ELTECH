package eltech;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Test {
    static ExecutorService executor;
    int count = 1;

    public long main(int pool, int threads, int time, Lock lock) throws InterruptedException {
        executor = Executors.newFixedThreadPool(pool);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000000 / threads; i++) {
                try {
                    lock.tryLock(time, TimeUnit.SECONDS);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Callable<Object> callable = Executors.callable(runnable);

        List<Callable<Object>> callableTasks = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            callableTasks.add(callable);
        }

        long starttime;
        long endtime;
        starttime = System.currentTimeMillis();

        executor.invokeAll(callableTasks);
        endtime = System.currentTimeMillis();
        executor.shutdown();

        return endtime - starttime;
    }
}