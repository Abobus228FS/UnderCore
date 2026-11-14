package under.verse.core.block;

import under.verse.core.Material;
import under.verse.core.Location;
import under.verse.core.World;
import under.verse.core.entity.Player;

public abstract class BaseStairsBlock extends Block implements Waterloggable, Directional, Bisected {

    private boolean waterlogged;
    private Half half = Half.BOTTOM;
    private Facing facing = Facing.NORTH;

    protected BaseStairsBlock(Material material) {
        super(material);
    }

    @Override
    public boolean isWaterlogged() {
        return waterlogged;
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        this.waterlogged = waterlogged;
        sendUpdate();
    }

    public void setHalf(Half half) {
        this.half = half;
        sendUpdate();
    }

    public Half getHalf() {
        return half;
    }

    @Override
    public Facing getFacing() {
        return facing;
    }

    @Override
    public void setFacing(Facing facing) {
        this.facing = facing;
        sendUpdate();
    }

    @Override
    protected int getStateIdOffset() {
        int id = (facing.ordinal() - 2) * 20;
        if (!waterlogged) id += 1;
        if (half == Half.BOTTOM) id += 10;
        return id;
    }

    @Override
    public void place(Player player, Location loc, BlockFace clickedFace, ClickLocation clickLocation) {
        Block clickedBlock = World.w.getBlockAt(loc);
        if (clickedBlock == null) return;

        Block targetBlock = clickedBlock.getRelative(clickedFace);
        if (targetBlock == null) return;

        setFacing(Facing.valueOf(getLookFace(player.getLocation()).name()));

        if (clickedFace == BlockFace.BOTTOM) {
            setHalf(Half.TOP);
        } else if (clickedFace == BlockFace.TOP) {
            setHalf(Half.BOTTOM);
        } else {
            setHalf(clickLocation.y() >= 0.5f ? Half.TOP : Half.BOTTOM);
        }

        setLocation(targetBlock.getLocation());
    }

    private static BlockFace getLookFace(Location location) {
        float yaw = location.getYaw();
        int index = Math.floorMod(Math.round(yaw / 90.0f), 4);

        return switch (index) {
            case 0 -> BlockFace.SOUTH;
            case 1 -> BlockFace.WEST;
            case 2 -> BlockFace.NORTH;
            default -> BlockFace.EAST;
        };
    }
}
