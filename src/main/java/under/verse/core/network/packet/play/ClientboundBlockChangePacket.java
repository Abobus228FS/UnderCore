package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.block.BlockState;
import under.verse.core.Location;

public class ClientboundBlockChangePacket extends ClientboundPacket {
    
    private final Location loc;
    private final BlockState state;
    
    public ClientboundBlockChangePacket(Location loc, BlockState state) {
        this.loc = loc;
        this.state = state;
    }
    
    public byte getId() {
        return 0x0B;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) {
        long x = ((long) loc.getX()) & 0x3FFFFFFL;
        long z = ((long) loc.getZ()) & 0x3FFFFFFL;
        long y = ((long) loc.getY()) & 0xFFFL;
        out.write((long) (x << 38 | y << 26 | z));
        out.writeVarint(state.getId());
        return null;
    }
}
