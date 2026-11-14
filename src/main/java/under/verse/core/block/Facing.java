package under.verse.core.block;

import under.verse.core.Axis;

public enum Facing {
    
    NORTH(Axis.Z, false),
    SOUTH(Axis.Z, true),
    WEST(Axis.X, false),
    EAST(Axis.X, true);
    
    private final Axis axis;
    private final boolean positive;
    
    Facing(Axis axis, boolean positive) {
        this.axis = axis;
        this.positive = positive;
    }
    public Facing getOpposite() {
        return values()[positive ? ordinal() - 1 : ordinal() + 1];
    }
    public Axis getAxis() { return axis; }
    public boolean isPositive() { return positive; }
}
