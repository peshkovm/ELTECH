package eltech;

public class RegBooleanMRSWRegister implements RegisterBoolean {
    ThreadLocal<Boolean> last;
    SafeBooleanMRSWRegister s_value; // safe MRSW register

    RegBooleanMRSWRegister(int capacity) {
        last = ThreadLocal.withInitial(() -> false);
    }

    public void write(Boolean x) {
        if (x != last.get()) {
            last.set(x);
            s_value.write(x);
        }
    }

    public Boolean read() {
        return s_value.read();
    }
}