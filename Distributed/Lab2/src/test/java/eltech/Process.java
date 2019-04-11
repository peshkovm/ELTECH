package eltech;

import java.util.ArrayList;
import java.util.List;

public class Process {
    public static void main(String[] args) throws InterruptedException {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TimeoutCLHTest timeoutCLHTest = new TimeoutCLHTest();
            Double res = timeoutCLHTest.main(new String[]{"10", "10", "1", "true"});
            list.add(res);
        }
        list.stream().distinct().forEach(System.out::println);
        list.clear();
        System.out.println();
        for (int i = 0; i < 100; i++) {
            TimeoutCLHTest timeoutCLHTest = new TimeoutCLHTest();
            Double res = timeoutCLHTest.main(new String[]{"10", "10", "1", "false"});
            list.add(res);
        }
        list.stream().distinct().forEach(System.out::println);

        System.out.println();

        double res = 0;
        int count = 1;

        for (int i = 0; i < 10; i++) {
            res = Math.pow(2, count++);
        }

        System.out.println(res);
    }
}
