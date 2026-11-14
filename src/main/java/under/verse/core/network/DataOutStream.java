package under.verse.core.network;

import under.verse.core.network.packet.Packet;
import under.verse.core.world.Chunk;
import under.verse.core.block.Block;
import java.io.*;
import under.verse.core.utils.ByteBuilder;

public class DataOutStream {

    private ByteBuilder bytes = new ByteBuilder();
    private final OutputStream out;
    
    public DataOutStream(OutputStream out) {
        this.out = out;
    }
    public synchronized void writeVarint(int v) {
            do {
                byte b = (byte)(v & 0x7F);
                v >>>= 7;
                if (v != 0) {
                    b |= 0x80;
                    bytes.append(b);
                } else {
                    bytes.append(b);
                    return;
                }
            } while (true);
    }
    private synchronized void writeSuperVarint(int v) throws IOException {
            do {
                byte b = (byte)(v & 0x7F);
                v >>>= 7;
                if (v != 0) {
                    b |= 0x80;
                    out.write(b);
                } else {
                    out.write(b);
                    return;
                }
            } while (true);
    }
    public synchronized void writeString(String v) {
        writeVarint(v.getBytes().length);
        write(v.getBytes());
    }
    public synchronized void write(String v) {
        writeVarint(v.getBytes().length);
        write(v.getBytes());
    }
    public synchronized void write(byte v) {
        bytes.append(v);
    }
    public synchronized void write(byte[] v) {
        for (byte b : v) {
            write(b);
        }
    }
    public synchronized void flush() throws IOException {
        writeSuperVarint(bytes.size());
        out.write(bytes.toByteArray());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes.toByteArray()) {
            sb.append((b & 0xFF) + " ");
        }
        System.out.println(bytes.toByteArray().length + ": " + sb);
        out.flush();
        bytes.clear();
    }
    public synchronized void write(int v) {
        write((byte) ((v >>> 24) & 0xFF));
        write((byte) ((v >>> 16) & 0xFF));
        write((byte) ((v >>> 8) & 0xFF));
        write((byte) (v & 0xFF));
    }
    public synchronized void write(float v) {
        write(Float.floatToIntBits(v));
    }
    public synchronized void write(long v) {
        write((byte) ((v >>> 56) & 0xFF));
        write((byte) ((v >>> 48) & 0xFF));
        write((byte) ((v >>> 40) & 0xFF));
        write((byte) ((v >>> 32) & 0xFF));
        write((byte) ((v >>> 24) & 0xFF));
        write((byte) ((v >>> 16) & 0xFF));
        write((byte) ((v >>> 8) & 0xFF));
        write((byte) (v & 0xFF));
    }
    public synchronized void write(double v) {
        write(Double.doubleToLongBits(v));
    }
    public synchronized void write(short v) {
        write((byte) ((v >>> 8) & 0xFF));
        write((byte) (v & 0xFF));
    }
    public synchronized void write(boolean v) {
        if (v) write((byte) 1);
        else write((byte) 0);
    }
    public synchronized void write(Chunk v) {
        System.out.println("фц");
        write((int) 1);
        write((int) 1);
        write(true);
        writeVarint(65535);
        ByteBuilder bb = new ByteBuilder();
        for (int i = 0; i < 16; i++) {
            bb.append(4096);
            bb.append((byte) 0x08);
            bb.append((byte)1);
            bb.append((byte)v.getBlocks()[0][0][0].getType().getId());
            bb.append((byte)0x80);
            bb.append((byte)2);
            bb.append(new byte[4096]);
            bb.append(new byte[2048]);
            bb.append(new byte[2048]);
        }
        writeVarint(bb.toByteArray().length);
        write(bb.toByteArray());
    }
}
