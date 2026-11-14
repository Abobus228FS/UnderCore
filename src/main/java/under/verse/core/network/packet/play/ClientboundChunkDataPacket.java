package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.world.Chunk;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkStage;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;
import under.verse.core.block.Block;
import under.verse.core.Material;

public class ClientboundChunkDataPacket extends ClientboundPacket {
    
    private final Chunk chunk;
    private final int x;
    private final int z;
    
public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write(x);
        out.write(z);
        out.write(true);
        out.writeVarint(0xFFFF);

        Block[][][] blocks = chunk.getBlocks();
        ByteBuilder bb = new ByteBuilder();

        for (int section = 0; section < 16; section++) {
            bb.append((byte) 14);
            appendVarInt(bb, 896); 

            long[] packedLongs = new long[896];

            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        int i = (y * 256) + (z * 16) + x;

                        int globalY = (section * 16) + y; 

                        Block b = blocks[x][globalY][z];
                        long id = b == null ? 0L : b.getState().getId() & 0x3FFFL;

                        long bitIndex = (long) i * 14;
                        int longIndex = (int) (bitIndex / 64);
                        int bitOffset = (int) (bitIndex % 64);

                        packedLongs[longIndex] |= (id << bitOffset);

                        if (bitOffset + 14 > 64) {
                            int bitsWritten = 64 - bitOffset;
                            packedLongs[longIndex + 1] |= (id >>> bitsWritten);
                        }
                    }
                }
            }

            byte[] blockss = new byte[7168];
            int byteIdx = 0;
            for (long l : packedLongs) {
                blockss[byteIdx++] = (byte) ((l >>> 56) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 48) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 40) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 32) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 24) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 16) & 0xFF);
                blockss[byteIdx++] = (byte) ((l >>> 8) & 0xFF);
                blockss[byteIdx++] = (byte) (l & 0xFF);
            }
            bb.append(blockss); 

            bb.append(new byte[2048]); 

            byte[] skyLight = new byte[2048];
            java.util.Arrays.fill(skyLight, (byte) 0xFF);
            bb.append(skyLight);
        }

        bb.append(new byte[1024]);          

        byte[] data = bb.toByteArray();
        out.writeVarint(data.length);
        out.write(data);

        out.writeVarint(0);
        
        return new NextPacket(PacketSide.CLIENT, 0x2E);
    }

private static void appendVarInt(ByteBuilder bb, int value) {
    while ((value & ~0x7F) != 0) {
        bb.append((byte) ((value & 0x7F) | 0x80));
        value >>>= 7;
    }
    bb.append((byte) value);
}

    public ClientboundChunkDataPacket(Chunk chunk, int x, int z) { this.chunk = chunk; this.x = x; this.z = z; }
    public byte getId() {
        return 34;
    }
}
