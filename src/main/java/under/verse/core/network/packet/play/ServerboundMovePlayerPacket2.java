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

public class ServerboundMovePlayerPacket2 extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        double x = in.read(double.class);
        double y = in.read(double.class);
        double z = in.read(double.class);
        in.skip(1);
        Location l = manager.getPlayer().getLocation();
        EventManager.throwEvent(new PlayerMoveEvent(manager.getPlayer(), new Location(x, y, z, l.getYaw(), l.getPitch())));
        return null;
    }
    
    public byte getId() {
        return 16;
    }
}
