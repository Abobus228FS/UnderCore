package under.verse.core.util;

public class Varint extends Number {
    
    private int value;
    
    @Override public long longValue() { return (long) value; }
    @Override public int intValue() { return value; }
    @Override public double doubleValue() { return (double) value; }
    @Override public float floatValue() { return (float) value; }
    @Override public short shortValue() { return (short) value; }
    @Override public byte byteValue() { return (byte) value; }
    
    public Varint(int value) {
        this.value = value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
