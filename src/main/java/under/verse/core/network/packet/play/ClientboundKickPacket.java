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
import under.verse.core.network.NetworkManager;

public class ClientboundKickPacket extends ClientboundPacket {
    
    private byte[] bytes;
    public ClientboundKickPacket(String msg) {
        String json = LegacyToJson.buildJson(msg.replace("\\","\\\\").replace("\"","\\\""));
        bytes = new ByteBuilder()
        .append(VarIntReader.toVarInt(json.getBytes().length))
        .append(json.getBytes())
        .toByteArray();
    }
    public byte[] toByteArray() {
        return bytes;
    }
    public byte getId() {
        return 27;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write(bytes);
        return null;
    }
}
