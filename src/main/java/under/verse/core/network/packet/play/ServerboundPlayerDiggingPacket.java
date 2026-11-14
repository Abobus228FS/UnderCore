package under.verse.core.network.packet.play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.event.block.BlockBreakEvent;
import under.verse.core.world.Chunk;
import under.verse.core.event.EventManager;
import under.verse.core.entity.Player;
import under.verse.core.Logger;

public class ServerboundPlayerDiggingPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        int status = in.readVarint();
        long val = in.read(long.class);
int x = (int) (val >> 38);
int y = (int) ((val >> 26) & 0xFFFL);
int z = (int) ((val << 38) >> 38);
x = x & 15;
y = y & 255;
z = z & 15;
        if (x >= 33554432) x -= 67108864;
        if (z >= 33554432) z -= 67108864;
        System.out.println(new under.verse.core.Location(x, y, z).toString());
        in.skip(1);
        Player player = manager.getPlayer();
        if (status == 2) EventManager.throwEvent(new BlockBreakEvent(player, player.getLocation().getWorld().getBlockAt(x, y, z)));
        return null;
    }
    
    public byte getId() {
        return 24;
    }
}
