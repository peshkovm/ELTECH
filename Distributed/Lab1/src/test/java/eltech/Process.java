
package eltech;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Process {

    public static void main(String[] args) throws InterruptedException {
        Register[] arr = {new SafeBooleanSRSWRegister(), new SafeBooleanMRSWRegister(100), new RegBooleanMRSWRegister(100), new RegMRSWRegister(100), new AtomicSRSWRegister(1), new AtomicMRSWRegister(100, 10), new AtomicMRMWRegister(100, 1)};
    }
}