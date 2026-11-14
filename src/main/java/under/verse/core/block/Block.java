package under.verse.core.block;

import under.verse.core.Material;
import under.verse.core.Location;
import under.verse.core.entity.Player;
import under.verse.core.world.Chunk;
import under.verse.core.UnderCore;
import under.verse.core.World;
import under.verse.core.Axis;
import under.verse.core.network.packet.play.ClientboundBlockChangePacket;

public class Block {

    private Material type;
    private Location location;
    private final BlockState state;
    
    protected Block(Material type) {
        this.type = type;
        this.state = new BlockState(this);
    }
    public Material getType() { return type; }
    public Location getLocation() { return location; }
    public BlockState getState() { return state; }
    
    public static Block of(Material material) {
        if (material.isSlab()) {
            if (material.isWood()) return new WoodSlabBlock(material);
        }
        if (material.isStairs()) {
            if (material.isWood()) return new WoodStairsBlock(material);
        }
        if (material.isWool()) return new WoolBlock(material);
        return new Block(material);
    }
    
    public void setLocation(Location loc) {
        location = loc;
        World.w.setBlockAt(location, this);
        ClientboundBlockChangePacket packet = new ClientboundBlockChangePacket(loc, state);
        for (Player p : UnderCore.players) p.connection.send(packet);
    }
    
    public void setType(Material type) {
        this.type = type;
        if (location == null) return;
        ClientboundBlockChangePacket packet = new ClientboundBlockChangePacket(location, state);
        for (Player p : UnderCore.players) p.connection.send(packet);
    }
    public Block getRelative(BlockFace face) {
        int i = face.isPositive() ? 1 : -1;
        Axis ax = face.getAxis();
        Location l = new Location(ax == Axis.X ? (int) location.getX() + i : (int) location.getX(),
            ax == Axis.Y ? (int) location.getY() + i : (int) location.getY(),
            ax == Axis.Z ? (int) location.getZ() + i : (int) location.getZ());
        Block relative = World.w.getBlockAt(l);
        if (relative.getLocation() == null) relative.setLocation(l);
        return relative;

    }
    protected void sendUpdate() {
        if (location == null) return;
        ClientboundBlockChangePacket packet = new ClientboundBlockChangePacket(location, state);
        for (Player p : UnderCore.players) p.connection.send(packet);
    }
    protected int getStateIdOffset() { return 0; }
    public BlockFace getFace(Player p) {
        return null;
    }
    public void place(Player player, Location loc, BlockFace face, ClickLocation clocation) {
        Block b = World.w.getBlockAt(loc);
        if (b == null) return;
        Block bb = b.getRelative(face);
        if (bb == null) return;
        World.w.setBlockAt(bb.getLocation(), this);
    }
}
