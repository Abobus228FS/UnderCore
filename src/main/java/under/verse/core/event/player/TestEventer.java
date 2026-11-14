package under.verse.core.event.player;

import under.verse.core.event.*;
import under.verse.core.event.block.*;
import under.verse.core.entity.Player;
import under.verse.core.World;
import under.verse.core.NamespaceKey;
import under.verse.core.Material;
import under.verse.core.Location;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.command.CommandManager;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.play.ClientboundDeclareCommandsPacket;
import under.verse.core.network.packet.play.ClientboundChunkDataPacket;
import under.verse.core.network.packet.play.ClientboundPlayerPositionAndLookPacket;
import under.verse.core.utils.Component;

public class TestEventer implements Listener {
    @EventHandler
    public void onPM(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.STONE) return;
        event.setCancelled(true);
        event.getPlayer().sendMessage("не ломакай каменюга");
    }
    @EventHandler
    public void onPM(PlayerJoinEvent event) {
        NetworkManager con = event.getPlayer().connection;
        World w = event.getPlayer().getLocation().getWorld();
        con.send(new ClientboundPlayerPositionAndLookPacket(new Location(8.0, 70.0, 8.0, 0.0f, 0.0f)));
        for (int x = -5; x <= 5; x++)
          for (int z = -5; z <= 5; z++)
            con.send(new ClientboundChunkDataPacket(w.getChunk(x, z), x, z));
    }
}
