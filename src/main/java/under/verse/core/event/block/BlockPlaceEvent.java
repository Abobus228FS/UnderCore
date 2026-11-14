package under.verse.core.event.block;

import under.verse.core.entity.Player;
import under.verse.core.block.*;
import under.verse.core.UnderCore;
import under.verse.core.Material;
import under.verse.core.World;
import under.verse.core.event.Cancellable;
import under.verse.core.network.packet.play.ClientboundBlockChangePacket;

public class BlockPlaceEvent extends BlockEvent implements Cancellable {
    private final Player player;
    private boolean cancel;
    private Material material;
    private BlockFace face;
    private Block block;
    private ClickLocation location;
    public BlockPlaceEvent(Player player, Block block, Material type, BlockFace face, ClickLocation location) {
        super(block.getRelative(face));
        this.player = player;
        this.material = type;
        this.face = face;
        this.block = block;
        this.location = location;
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
        if (cancel) material = Material.AIR;
        World.w.placeNaturally(player, material, block.getLocation(), face, location);
    }
}
