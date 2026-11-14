package under.verse.core.network;

import under.verse.core.network.packet.Packet;
import under.verse.core.Logger;
import under.verse.core.util.Varint;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataInStream {

    private final InputStream in;
    
    public DataInStream(InputStream in) {
        this.in = in;
    }
    @Deprecated
    public int readVarint() throws IOException {
            int varInt = 0;
            int length = 0;
            do {
                int varIntByte = in.read();
                length++;
                if (varIntByte == -1) return -1;
                if (length > 5) {
                    throw new IOException("VarInt слишком большой");
                }
                if (varIntByte >= 128) {
                    varIntByte = varIntByte - 128;
                    varInt |= (varIntByte << (7 * (length - 1)));
                } else {
                    varInt |= (varIntByte << (7 * (length - 1)));
                    return varInt;
                }
            } while (true);
    }
    public String readString() throws IOException {
        return new String(in.readNBytes(readVarint()), StandardCharsets.UTF_8);
    }
    public int read() throws IOException {
        return in.read();
    }
    public byte[] readNBytes(int v) throws IOException {
        return in.readNBytes(v);
    }
    public void skipNBytes(int v) throws IOException {
        in.skipNBytes(v);
    }
    public void skip(int v) throws IOException {
        in.skip(v);
    }
    public <T> T read(Class<T> type) throws IOException {
        if (type == int.class || type == Integer.class) {
            byte[] v = readNBytes(4);
            int value = ((int) (v[0] & 0xFF) << 24) |
                 ((int) (v[1] & 0xFF) << 16) |
                 ((int) (v[2] & 0xFF) << 8)  |
                 ((int) (v[3] & 0xFF));
            return (T) ((Integer) value);
        }
        if (type == long.class || type == Long.class) {
            byte[] v = readNBytes(8);
            long value = ((long) (v[0] & 0xFF) << 56) |
                 ((long) (v[1] & 0xFF) << 48) |
                 ((long) (v[2] & 0xFF) << 40) |
                 ((long) (v[3] & 0xFF) << 32) |
                 ((long) (v[4] & 0xFF) << 24) |
                 ((long) (v[5] & 0xFF) << 16) |
                 ((long) (v[6] & 0xFF) << 8)  |
                 ((long) (v[7] & 0xFF));
            return (T) ((Long) value);
        }
        if (type == short.class || type == Short.class) {
            short value = 0;
            byte[] v = readNBytes(2);
            value += v[0] & 0xFF;
            value += (v[1] & 0xFF) << 8;
            return (T) ((Short) value);
        }
        if (type == char.class || type == Character.class) {
            char value = 0;
            byte[] v = readNBytes(2);
            value += v[0] & 0xFF;
            value += (v[1] & 0xFF) << 8;
            return (T) ((Character) value);
        }
        if (type == byte.class || type == Byte.class) return (T) ((Byte) (byte) read());
        if (type == boolean.class || type == Boolean.class) return (T) ((Boolean) (read() != 0));
        if (type == double.class || type == Double.class) {
            byte[] v = readNBytes(8);
    // Сначала собираем строго long биты
            long bits = ((long) (v[0] & 0xFF) << 56) |
                ((long) (v[1] & 0xFF) << 48) |
                ((long) (v[2] & 0xFF) << 40) |
                ((long) (v[3] & 0xFF) << 32) |
                ((long) (v[4] & 0xFF) << 24) |
                ((long) (v[5] & 0xFF) << 16) |
                ((long) (v[6] & 0xFF) << 8)  |
                ((long) (v[7] & 0xFF));
    // Магический метод превращает биты long в нормальный double
            return (T) Double.valueOf(Double.longBitsToDouble(bits));
        }

        if (type == float.class || type == Float.class) {
            byte[] v = readNBytes(4);
    // Сначала собираем строго int биты
            int bits = ((v[0] & 0xFF) << 24) |
                       ((v[1] & 0xFF) << 16) |
                       ((v[2] & 0xFF) << 8)  |
                       (v[3] & 0xFF);
    // Магический метод превращает биты int в нормальный float
            return (T) Float.valueOf(Float.intBitsToFloat(bits));
}

        if (type == String.class) return (T) new String(readNBytes(read(Varint.class).intValue()), StandardCharsets.UTF_8);
        if (type == Varint.class) return (T) new Varint(readVarint());
        throw new IllegalArgumentException(type.getName() + " не является допустимым типом для чтения");
    }
 /*   public long readLong() throws IOException {
        long v = 0;
        for (byte b : readNBytes(8)) {
            v = (v << 8) | (b & 0xFFL);
        }
        return v;
    }*/
}
