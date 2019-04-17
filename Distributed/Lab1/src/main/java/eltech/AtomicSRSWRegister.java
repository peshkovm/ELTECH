package eltech;

public class AtomicSRSWRegister implements RegisterInteger {
    ThreadLocal<Long> lastStamp;
    ThreadLocal<StampedValue<Integer>> lastRead;
    StampedValue<Integer> r_value; // regular SRSW timestamp-value pair

    public AtomicSRSWRegister(Integer init) {
        r_value = new StampedValue<Integer>(init);
        lastStamp = ThreadLocal.withInitial(() -> 0L);
        lastRead = ThreadLocal.withInitial(() -> r_value);
    }

    public Integer read() {
        StampedValue<Integer> value = r_value;
        StampedValue<Integer> last = lastRead.get();
        StampedValue<Integer> result = StampedValue.max(value, last);
        lastRead.set(result);
        return result.value;
    }

    public void write(Integer v) {
        long stamp = lastStamp.get() + 1;
        r_value = new StampedValue<>(stamp, v);
        lastStamp.set(stamp);
    }
}