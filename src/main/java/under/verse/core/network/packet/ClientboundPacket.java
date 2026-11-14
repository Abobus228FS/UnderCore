package under.verse.core.network.packet;

import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;
import java.io.IOException;

public abstract class ClientboundPacket extends Packet {
    public abstract NextPacket write(DataOutStream out, NetworkManager manager) throws IOException;
}
