package under.verse.core.network.packet.status;

import java.io.*;
import java.net.*;
import under.verse.core.utils.LegacyToJson;
import under.verse.core.UnderCore;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;
import under.verse.core.NamespaceKey;

public class ClientboundServerListPingResponsePacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write((Long) manager.getPlayer().getData(NamespaceKey.create("undercore", "ping")));
        return null;
    }
    public byte getId() {
        return 1;
    }
}
