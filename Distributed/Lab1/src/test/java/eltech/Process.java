
package eltech;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Process {

    public static void main(String[] args) throws InterruptedException {
        /*Lock[] arr = {};
        List<PlotDataPair> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            List<PlotDataPair.TimeExecThreadCountPair> list1 = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                if (1000000 % j == 0) {
                    Test test = new Test();
                    long res = test.main(j, j, 1, arr[i]);
                    list1.add(new PlotDataPair.TimeExecThreadCountPair(res, j));
                    System.out.println(arr[i] + " " + j + " " + res);
                }
            }
            list.add(new PlotDataPair(arr[i].toString(), list1));
        }
        Plot plot = new Plot("Plot", list);
        plot.pack();
        plot.setVisible(true);*/
        Test test = new Test();
        test.main(1, 1, new SafeBooleanMRSWRegister(100));
    }
}