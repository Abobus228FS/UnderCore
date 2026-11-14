package under.verse.core.network.packet.handshake;

import java.io.*;
import java.net.*;
import under.verse.core.UnderCore;
import under.verse.core.packet.*;
import under.verse.core.network.*;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.packet.NextPacket;
import java.nio.charset.StandardCharsets;
import under.verse.core.entity.Player;
import under.verse.core.network.NetworkStage;

public class ServerboundHandshakePacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        int version = in.readVarint();
        in.readString();
        in.skip(2);
        int lastByte = in.read();
        manager.setStage(NetworkStage.LOGIN);
        if (lastByte == 1) {
            manager.setStage(NetworkStage.STATUS);
            return new NextPacket(PacketSide.SERVER, 0);//CServerListPing.readPacket(in, out);
        }
        if (lastByte == 2) return new NextPacket(PacketSide.SERVER, 0);//CLoginStart.readPacket(in, out, player);
        return null;
    }
    public byte getId() { return 0; }
}
