package under.verse.core.network.packet.play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.event.player.PlayerMoveEvent;
import under.verse.core.event.EventManager;
import under.verse.core.Location;
import under.verse.core.entity.Player;

public class ServerboundMovePlayerPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        double x = in.read(double.class);
        double y = in.read(double.class);
        double z = in.read(double.class);
        float yaw = in.read(float.class);
        float pitch = in.read(float.class);
        in.skip(1);
        EventManager.throwEvent(new PlayerMoveEvent(manager.getPlayer(), new Location(x, y, z, yaw, pitch)));
        return null;
    }
    
    public byte getId() {
        return 17;
    }
}
