package under.verse.core;

public enum ChatColor {
    BLACK('0'),
    DARK_BLUE('1'),
    GREEN('2'),
    CYAN('3'),
    DARK_RED('4'),
    PURPLE('5'),
    ORANGE('6'),
    LIGHT_GRAY('7'),
    GRAY('8'),
    BLUE('9'),
    LIME('a'),
    LIGHT_BLUE('b'),
    RED('c'),
    PINK('d'),
    YELLOW('e'),
    WHITE('f');
    
    private final char legacyColor;
    
    ChatColor(char legacyColor) {
        this.legacyColor = legacyColor;
    }
    public char getLegacyColor() { return legacyColor; }
}

