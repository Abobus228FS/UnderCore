package under.verse.core.network.packet.status;

import java.io.*;
import java.net.*;
import under.verse.core.utils.LegacyToJson;
import under.verse.core.UnderCore;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;

public class ClientboundServerListStatusResponsePacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        String json ="{\"version\":{\"name\":\"UnderCore\",\"protocol\":393},\"players\":{\"max\":0,\"online\":0},\"description\":{\"text\":\"\",\"extra\":" + LegacyToJson.buildJson(UnderCore.getConfig().getString("server-motd", "The §6UnderCore §fserver")) + "}}";
        out.writeString(json);
        return new NextPacket(PacketSide.SERVER, 1);
    }
    public byte getId() {
        return 0;
    }
}
