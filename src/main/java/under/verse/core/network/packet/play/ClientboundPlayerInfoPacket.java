package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.utils.LegacyToJson;
import under.verse.core.packet.VarIntReader;
import java.nio.charset.*;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.entity.Player;
import under.verse.core.network.NetworkManager;
import java.util.UUID;

public class ClientboundPlayerInfoPacket extends ClientboundPacket {

    private byte[] bytes;
    public ClientboundPlayerInfoPacket(Player... players) {
        ByteBuilder bb = new ByteBuilder().append((byte) 0).append((byte) players.length);
        for (Player player : players) {
      //      bb.append(UUID.fromUUID(player.getUniqueId())).append(player.getName().length()).append(player.getName()).append(0);
        }
        this.bytes = bb.toByteArray();
    }
    public byte[] toByteArray() {
        return bytes;
    }
    public byte getId() {
        return 48;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write(bytes);
        return null;
    }
}
