package under.verse.core.block;

import under.verse.core.Axis;

public enum BlockFace {
    
    BOTTOM(Axis.Y, false),
    TOP(Axis.Y, true),
    NORTH(Axis.Z, false),
    SOUTH(Axis.Z, true),
    WEST(Axis.X, false),
    EAST(Axis.X, true);
    
    private final int id = ordinal();
    private final Axis axis;
    private final boolean positive;
    
    BlockFace(Axis axis, boolean positive) {
        this.axis = axis;
        this.positive = positive;
    }
    public static BlockFace get(int id) {
        if (id >= 6 || id < 0) return null;
        return values()[id];
    }
    public BlockFace getOpposite() {
        return get(positive ? id - 1 : id + 1);
    }
    
    public int getId() { return id; }
    public Axis getAxis() { return axis; }
    public boolean isPositive() { return positive; }
}
