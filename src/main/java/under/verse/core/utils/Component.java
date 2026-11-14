package under.verse.core.utils;

public class Component {
    private final StringBuilder sb = new StringBuilder();
    private String text;
    private String color;
    private boolean b = false;
    
    public static Component text(String textt) {
        Component component = new Component();
        component.text = textt;
        return component;
    }
    public Component color(ComponentColor colorr) {
        this.color = colorr.getJsonColor();
        return this;
    }
    private String toAppend() {
        StringBuilder sbb = new StringBuilder();
        sbb.append("{\"text\":\"" + text + "\"");
        if (color != null) sbb.append(",\"color\":\"" + color + "\"");
        sbb.append("}");
        return sbb.toString();
    }
    
    public Component() {
        sb.append("[");
    }
    @Override
    public String toString() {
        return sb + "]";
    }
    
    public Component append(Component componentt) {
        if (b) sb.append(",");
        sb.append(componentt.toAppend());
        b = true;
        return this;
    }
    public static enum ComponentColor {
        BLACK('0', "black"),
        DARK_BLUE('1', "dark_blue"),
        GREEN('2', "green"),
        DARK_AQUA('3', "dark_aqua"),
        DARK_RED('4', "dark_red"),
        DARK_PURPLE('5', "dark_purple"),
        GOLD('6', "gold"),
        GRAY('7', "gray"),
        DARK_GRAY('8', "dark_gray"),
        BLUE('9', "blue"),
        LIME('a', "lime"),
        AQUA('b', "aqua"),
        RED('c', "red"),
        LIGHT_PURPLE('d', "light_purple"),
        YELLOW('e', "yellow"),
        WHITE('f', "white");
        
        private final char legacyColor;
        private final String jsonColor;
        
        ComponentColor(char legacyColor, String jsonColor) {
            this.legacyColor = legacyColor;
            this.jsonColor = jsonColor;
        }
        public char getLegacyColor() { return legacyColor; }
        public String getJsonColor() { return jsonColor; }
        public static ComponentColor get(char c) {
            for (ComponentColor value : values()) {
                if (c == value.getLegacyColor()) return value;
            }
            return WHITE;
        }
        public static ComponentColor get(String s) {
            for (ComponentColor value : values()) {
                if (s.equals(value.getJsonColor())) return value;
            }
            return WHITE;
        }
    }
}
