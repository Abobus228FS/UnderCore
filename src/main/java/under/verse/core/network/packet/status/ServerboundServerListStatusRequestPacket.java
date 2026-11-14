package under.verse.core.network.packet.status;

import java.io.*;
import java.net.*;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.PacketSide;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.NetworkStage;

public class ServerboundServerListStatusRequestPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        return new NextPacket(PacketSide.CLIENT, 0);
     //   SServerPingResponse.writePacket(in, out);
    }
    public byte getId() {
        return 0;
    }
}
