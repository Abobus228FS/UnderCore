package under.verse.core.block;

import under.verse.core.DyeColor;
import under.verse.core.Material;

public class WoolBlock extends Block implements Dyeable {

    private DyeColor color;
    
    WoolBlock(Material material) {
        super(material);
        if (!material.isWool()) throw new IllegalArgumentException("Нельзя создать шерсть не из шерсти");
        this.color = material.getColor();
    }
    @Override
    public void setColor(DyeColor color) {
        this.color = color;
        sendUpdate();
    }
    @Override
    public DyeColor getColor() { return color; }
    
    @Override
    protected int getStateIdOffset() {
        return color.ordinal();
    }
}
