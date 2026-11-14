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

public class ClientboundTitlePacket extends ClientboundPacket {

    private byte[] bytes;
    public ClientboundTitlePacket(int id, String msg) {
        String json = LegacyToJson.buildJson(msg.replace("\\","\\\\").replace("\"","\\\""));
        bytes = new ByteBuilder()
        .append((byte) id)
        .append(VarIntReader.toVarInt(json.getBytes().length))
        .append(json.getBytes())
        .toByteArray();
    }
    public ClientboundTitlePacket(int id, Component msg) {
        bytes = new ByteBuilder()
        .append((byte) id)
        .append(VarIntReader.toVarInt(msg.toString().replace("\\","\\\\").getBytes().length))
        .append(msg.toString().replace("\\","\\\\").getBytes())
        .toByteArray();
    }
    public ClientboundTitlePacket(int id, int var1, int var2, int var3) {
        bytes = new ByteBuilder()
        .append((byte) id)
        .append(var1)
        .append(var2)
        .append(var3)
        .toByteArray();
    }
    public byte[] toByteArray() {
        return bytes;
    }
    public byte getId() {
        return 72;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) {
        out.write(bytes);
        return null;
    }
}
