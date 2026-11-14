package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.utils.LegacyToJson;
import under.verse.core.packet.VarIntReader;
import java.nio.charset.*;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.NamespaceKey;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;

public class ClientboundPluginMessagePacket extends ClientboundPacket {
    
    private final byte[] data;
    private final NamespaceKey key;
    
    public ClientboundPluginMessagePacket(NamespaceKey key, byte[] data) {
        this.key = key;
        this.data = data;
    }
    public byte getId() {
        return 25;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write(key.toString());
        out.write(data);
        return null;
    }
}
