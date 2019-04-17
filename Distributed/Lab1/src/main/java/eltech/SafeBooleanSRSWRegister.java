package eltech;

public class SafeBooleanSRSWRegister implements RegisterBoolean {
    private volatile boolean value = false;

    @Override
    public Boolean read() {
        return value;
    }

    @Override
    public void write(Boolean v) {
        value = v;
    }
}
