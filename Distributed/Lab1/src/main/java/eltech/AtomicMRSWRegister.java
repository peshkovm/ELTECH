package eltech;

public class AtomicMRSWRegister implements RegisterInteger {
    ThreadLocal<Long> lastStamp;
    private StampedValue<Integer>[][] a_table; // each entry is SRSW atomic

    public AtomicMRSWRegister(Integer init, int readers) {
        lastStamp = ThreadLocal.withInitial(() -> 0L);
        a_table = (StampedValue<Integer>[][]) new StampedValue[readers][readers];
        StampedValue<Integer> value = new StampedValue<Integer>(init);
        for (int i = 0; i < readers; i++) {
            for (int j = 0; j < readers; j++) {
                a_table[i][j] = value;
            }
        }
    }

    public Integer read() {
        int me = (int) Thread.currentThread().getId();
        StampedValue<Integer> value = a_table[me][me];
        for (int i = 0; i < a_table.length; i++) {
            value = StampedValue.max(value, a_table[i][me]);
        }
        for (int i = 0; i < a_table.length; i++) {
            a_table[me][i] = value;
        }
        return value.value;
    }

    public void write(Integer v) {
        long stamp = lastStamp.get() + 1;
        lastStamp.set(stamp);
        StampedValue<Integer> value = new StampedValue<>(stamp, v);
        for (int i = 0; i < a_table.length; i++) {
            a_table[i][i] = value;
        }
    }
}