package under.verse.core.boss;

import under.verse.core.DyeColor;

public enum BarColor {
    PINK(DyeColor.PINK),
    BLUE(DyeColor.BLUE),
    RED(DyeColor.RED),
    GREEN(DyeColor.LIME),
    YELLOW(DyeColor.YELLOW),
    PURPLE(DyeColor.PURPLE),
    WHITE(DyeColor.WHITE);
    
    private final DyeColor color;
    
    BarColor(DyeColor color) { this.color = color; }
    public DyeColor getDyeColor() { return color; }
}
