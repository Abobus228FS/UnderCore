package under.verse.core.network.packet.play;

import java.io.IOException;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.NextPacket;

public class ServerboundKeepAlivePacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        manager.getPlayer().setPing(System.currentTimeMillis() - in.read(long.class));
        return null;
    }
    
    public byte getId() {
        return 14;
    }
}
