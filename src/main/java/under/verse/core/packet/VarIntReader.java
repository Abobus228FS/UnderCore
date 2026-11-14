package under.verse.core.packet;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import under.verse.core.Logger;
import under.verse.core.utils.ByteBuilder;

public class VarIntReader {

    public static int readVarInt(InputStream in) throws IOException {
        int varInt = 0;
        int length = 0;
        do {
            int varIntByte = in.read();
            length++;
            if (varIntByte == -1) return -1;
            if (length > 5) {
                Logger.getLogger().error("VarInt is too big!");
                throw new IOException("VarInt is too big");
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
    
    public static void writeVarInt(OutputStream out, int value) throws IOException {
        do {
            byte b = (byte)(value & 0x7F);
            value >>>= 7;
            if (value != 0) {
                b |= 0x80;
                out.write(b);
            } else {
                out.write(b);
                return;
            }
        } while (true);
    }
    
    public static byte[] toVarInt(int value) {
        ByteBuilder bytes = new ByteBuilder();
        do {
            byte b = (byte)(value & 0x7F);
            value >>>= 7;
            if (value != 0) {
                b |= 0x80;
                bytes.append(b);
            } else {
                bytes.append(b);
                return bytes.toByteArray();
            }
        } while (true);
    }
    
    public static int varIntLength(int value) throws IOException {
        int length = 0;
        do {
            length++;
            byte b = (byte)(value & 0x7F);
            value >>>= 7;
            if (value == 0) {
                return length;
            }
        } while (true);
    }
    
    public static void writeString(OutputStream out, String string) throws IOException {
        writeVarInt(out, string.getBytes().length);
        out.write(string.getBytes());
    }
    
    public static String readString(InputStream in) throws IOException {
        int i = readVarInt(in);
        return new String(in.readNBytes(i), StandardCharsets.UTF_8);
    }
}
