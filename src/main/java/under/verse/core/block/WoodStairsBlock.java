package under.verse.core.block;

import under.verse.core.Material;

public class WoodStairsBlock extends BaseStairsBlock {
    WoodStairsBlock(Material type) {
        super(type);
        if (!(type.isWood() && type.isStairs())) throw new IllegalArgumentException("Нельзя создать WoodStairsBlock не из деревянной ступеньки");
    }
}
