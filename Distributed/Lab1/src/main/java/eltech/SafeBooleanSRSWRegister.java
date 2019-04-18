package eltech;

public class SafeBooleanSRSWRegister implements RegisterBoolean {
    private volatile Boolean value = false;

    @Override
    public Boolean read() {
        return value;
    }

    @Override
    public void write(Boolean v) {
        value = v;
    }
}
