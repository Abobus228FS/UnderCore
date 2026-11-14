package under.verse.core.block;

import under.verse.core.Material;
import under.verse.core.Location;
import under.verse.core.World;
import under.verse.core.entity.Player;

public abstract class BaseSlabBlock extends Block implements Waterloggable, Bisected {

    private boolean waterlogged = false;
    private Half half = Half.BOTTOM;
    
    BaseSlabBlock(Material material) {
        super(material);
    }
    
    @Override
    public boolean isWaterlogged() { return waterlogged; }
    @Override
    public void setWaterlogged(boolean val) {
        this.waterlogged = val;
    }
    
    public void setHalf(Half half) {
        this.half = half;
        sendUpdate();
    }
    public Half getHalf() { return half; }
    
    @Override
    protected int getStateIdOffset() {
        int id = half.ordinal() * 2;
        if (!waterlogged) id += 1;
        return id;
    }
    @Override
    public void place(Player player, Location loc, BlockFace face, ClickLocation clocation) {
        Block b = World.w.getBlockAt(loc);
        if (b == null) return;
        Block bb = b.getRelative(face);
        if (bb == null) return;
        if (b.getType() == getType()) {
            if (face == BlockFace.TOP && b.getState().getHalf() == Half.BOTTOM ||
                face == BlockFace.BOTTOM && b.getState().getHalf() == Half.TOP) {
                b.getState().setHalf(Half.DOUBLE);
                b.sendUpdate();
                return;
            }
        }
        if (face == BlockFace.BOTTOM) setHalf(Half.TOP);
        else if (face == BlockFace.TOP) setHalf(Half.BOTTOM);
        else {
            if (clocation.y() >= 0.5f) setHalf(Half.TOP);
            else setHalf(Half.BOTTOM);
        }
        setLocation(bb.getLocation());
    }
}
