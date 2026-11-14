package under.verse.core.network.packet.login;

import java.io.*;
import java.net.*;
import java.util.UUID;
import under.verse.core.entity.Player;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkStage;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;

public class ClientboundLoginSuccessPacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.writeString(manager.getPlayer().getUniqueId().toString());
        out.writeString(manager.getPlayer().getName());
        manager.setStage(NetworkStage.PLAY);
        return new NextPacket(PacketSide.CLIENT, 37);
    }
    public byte getId() {
        return 2;
    }
}
