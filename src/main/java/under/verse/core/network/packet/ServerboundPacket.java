package under.verse.core.network.packet;

import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import java.io.IOException;

public abstract class ServerboundPacket extends Packet {
    public abstract NextPacket read(DataInStream in, NetworkManager manager) throws IOException;
}
