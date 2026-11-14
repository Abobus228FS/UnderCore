package under.verse.core.block;

import under.verse.core.Material;

public class WoodSlabBlock extends BaseSlabBlock {
    WoodSlabBlock(Material type) {
        super(type);
        if (!(type.isWood() && type.isSlab())) throw new IllegalArgumentException("Нельзя создать WoodSlabBlock не из деревянной плиты");
    }
}
