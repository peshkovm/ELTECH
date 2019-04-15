package eltech;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Process {
    public static void main(String[] args) throws InterruptedException {
        Lock[] arr = {new TimeoutCLH(), new ReentrantLock(), new Lock() {
            @Override
            public void lock() {

            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {

            }

            @Override
            public Condition newCondition() {
                return null;
            }
        }};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < 10; j++) {
                if (1000000 % j == 0) {
                    Test test = new Test();
                    long res = test.main(j, j, 1, arr[i]);
                    System.out.println(arr[i] + " " + j + " " + res);
                }
            }
        }
    }
}