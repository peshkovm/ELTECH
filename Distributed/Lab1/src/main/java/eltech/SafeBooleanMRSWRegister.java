package eltech;

public class SafeBooleanMRSWRegister implements RegisterBoolean {
    private volatile SafeBooleanSRSWRegister[] s_table; // array of safe SRSW registers

    public SafeBooleanMRSWRegister(int capacity) {
        s_table = new SafeBooleanSRSWRegister[capacity];
        for (int i = 0; i < capacity; i++) {
            s_table[i] = new SafeBooleanSRSWRegister();
        }
    }

    public Boolean read() {
        return s_table[(int) Thread.currentThread().getId()].read();
    }

    public void write(Boolean x) {
        for (int i = 0; i < s_table.length; i++)
            s_table[i].write(x);
    }
}