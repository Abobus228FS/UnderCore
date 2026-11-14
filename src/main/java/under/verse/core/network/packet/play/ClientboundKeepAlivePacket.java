package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkStage;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;

public class ClientboundKeepAlivePacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write((long) System.currentTimeMillis());
        return null;
    }
    public byte getId() {
        return 33;
    }
}
