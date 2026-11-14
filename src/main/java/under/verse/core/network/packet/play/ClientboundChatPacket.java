package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.utils.*;
import under.verse.core.packet.VarIntReader;
import java.nio.charset.StandardCharsets;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;

public class ClientboundChatPacket extends ClientboundPacket {

    private byte[] bytes;
    public ClientboundChatPacket(String msg) {
        String json = LegacyToJson.buildJson(msg.replace("\\","\\\\").replace("\"","\\\""));
        bytes = new ByteBuilder()
        .append(VarIntReader.toVarInt(json.getBytes().length))
        .append(json.getBytes())
        .append((byte) 0)
        .toByteArray();
    }
    public ClientboundChatPacket(Component msg) {
        bytes = new ByteBuilder()
        .append(VarIntReader.toVarInt(msg.toString().replace("\\","\\\\").getBytes().length))
        .append(msg.toString().replace("\\","\\\\").getBytes())
        .append((byte) 1)
        .toByteArray();
    }
    public byte[] toByteArray() {
        return bytes;
    }
    public byte getId() {
        return 14;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) {
        out.write(bytes);
        return null;
    }
}
