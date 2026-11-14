package under.verse.core.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBuilder {

    private byte[] bytes;
    private int arrayLength = 200;
    private int arrayCursor = 0;
    
    public ByteBuilder() {
        bytes = new byte[arrayLength];
    }
    public ByteBuilder(byte b) {
        bytes = new byte[arrayLength];
        bytes[0] = b;
        arrayCursor++;
    }
    
    public ByteBuilder append(byte bytee) {
        if (arrayLength < (arrayCursor + 1)) extendArray();
        try {
            bytes[arrayCursor++] = bytee;
        } catch (IndexOutOfBoundsException e) { e.printStackTrace(); }
        return this;
    }
    public ByteBuilder appendIf(boolean b, byte bytee) {
        if (b) append(bytee);
        return this;
    }
    public ByteBuilder appendIf(boolean b, byte[] bytee) {
        if (b) append(bytee);
        return this;
    }
    public ByteBuilder append(byte[] bytee) {
        for (byte b : bytee) {
            append(b);
        }
        return this;
    }
    public ByteBuilder append(int v) {
        append((byte) ((v >>> 24) & 0xFF));
        append((byte) ((v >>> 16) & 0xFF));
        append((byte) ((v >>> 8) & 0xFF));
        append((byte) (v & 0xFF));
        return this;
    }
    private void extendArray() {
        arrayLength *= 2;
        byte[] newBytes = new byte[arrayLength];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        bytes = newBytes;
    }

    
    @Override
    public String toString() {
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    public byte[] toByteArray() {
        return Arrays.copyOfRange(bytes, 0, arrayCursor);
    }
    public int size() {
        return Arrays.copyOfRange(bytes, 0, arrayCursor).length;
    }
    public ByteBuilder clear() {
        this.bytes = new byte[200];
        this.arrayCursor = 0;
        this.arrayLength = 200;
        return this;
    }
}
