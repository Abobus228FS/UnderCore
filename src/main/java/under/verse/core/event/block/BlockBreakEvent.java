package under.verse.core.event.block;

import under.verse.core.entity.Player;
import under.verse.core.block.Block;
import under.verse.core.UnderCore;
import under.verse.core.Material;
import under.verse.core.event.Cancellable;
import under.verse.core.network.packet.play.ClientboundBlockChangePacket;

public class BlockBreakEvent extends BlockEvent implements Cancellable {
    private final Player player;
    private boolean cancel;
    public BlockBreakEvent(Player player, Block block) {
        super(block);
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void run() {
        if (!cancel) getBlock().setType(Material.AIR);
        else {
            ClientboundBlockChangePacket packet = new ClientboundBlockChangePacket(getBlock().getLocation(), getBlock().getState());
            for (Player p : UnderCore.players) p.connection.send(packet);
        }
    }
}
