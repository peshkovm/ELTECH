package eltech;

public class RegMRSWRegister implements RegisterInteger {
    private static int RANGE = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
    RegBooleanMRSWRegister[] r_bit = new RegBooleanMRSWRegister[RANGE]; // regular boolean MRSW

    public RegMRSWRegister(int capacity) {
        for (int i = 1; i < r_bit.length; i++)
            r_bit[i].write(false);
        r_bit[0].write(true);
    }

    public void write(Integer x) {
        r_bit[x].write(true);
        for (int i = x - 1; i >= 0; i--)
            r_bit[i].write(false);
    }

    public Integer read() {
        for (int i = 0; i < RANGE; i++)
            if (r_bit[i].read()) {
                return i;
            }
        return -1; // impossible
    }
}