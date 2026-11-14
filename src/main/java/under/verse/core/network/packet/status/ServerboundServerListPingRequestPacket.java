package under.verse.core.network.packet.status;

import java.io.*;
import java.net.*;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.PacketSide;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.NamespaceKey;
import under.verse.core.network.NetworkStage;

public class ServerboundServerListPingRequestPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        manager.getPlayer().setData(NamespaceKey.create("undercore", "ping"), in.read(long.class));
        return new NextPacket(PacketSide.CLIENT, 1);
    }
    public byte getId() {
        return 1;
    }
}
