package eltech;

public class StampedValue<T> {
    public long stamp;
    public T value;

    // initial value with zero timestamp
    public StampedValue(T init) {
        stamp = 0;
        value = init;
    }

    // later values with timestamp provided
    public StampedValue(long stamp, T value) {
        stamp = stamp;
        value = value;
    }

    public static <T> StampedValue<T> max(StampedValue<T> x, StampedValue<T> y) {
        if (x.stamp > y.stamp) {
            return x;
        } else {
            return y;
        }
    }

    public static StampedValue MIN_VALUE =
            new StampedValue(null);
}