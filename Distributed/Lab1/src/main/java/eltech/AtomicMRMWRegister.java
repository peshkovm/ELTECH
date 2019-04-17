package eltech;

public class AtomicMRMWRegister implements RegisterInteger {
    private StampedValue<Integer>[] a_table; // array of atomic MRSW registers

    public AtomicMRMWRegister(int capacity, Integer init) {
        a_table = (StampedValue<Integer>[]) new StampedValue[capacity];
        StampedValue<Integer> value = new StampedValue<Integer>(init);
        for (int j = 0; j < a_table.length; j++) {
            a_table[j] = value;
        }
    }

    public void write(Integer value) {
        int me = (int) Thread.currentThread().getId();
        StampedValue<Integer> max = StampedValue.MIN_VALUE;
        for (int i = 0; i < a_table.length; i++) {
            max = StampedValue.max(max, a_table[i]);
        }
        a_table[me] = new StampedValue(max.stamp + 1, value);
    }

    public Integer read() {
        StampedValue<Integer> max = StampedValue.MIN_VALUE;
        for (int i = 0; i < a_table.length; i++) {
            max = StampedValue.max(max, a_table[i]);
        }
        return max.value;
    }
}