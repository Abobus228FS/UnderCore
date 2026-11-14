package under.verse.core.utils;

public class LegacyToJson {

    private static String colorParser(char color) {
        return switch (color) {
            case '0' -> "black";
            case '1' -> "dark_blue";
            case '2' -> "dark_green";
            case '3' -> "dark_aqua";
            case '4' -> "dark_red";
            case '5' -> "dark_purple";
            case '6' -> "gold";
            case '7' -> "gray";
            case '8' -> "dark_gray";
            case '9' -> "blue";
            case 'a' -> "green";
            case 'b' -> "aqua";
            case 'c' -> "red";
            case 'd' -> "light_purple";
            case 'e' -> "yellow";
            case 'f' -> "white";
            default -> booleanParser(color);
        };
    }
    private static String booleanParser(char code) {
        return switch (code) {
            case 'l' -> "bold";
            case 'o' -> "italic";
            case 'n' -> "underlined";
            case 'm' -> "strikethrough";
            case 'k' -> "obfuscsated";
            default -> "white";
        };
    }
    public static String buildJson(String legacyText) {
        String json = "";
        boolean isBooleanCode = false;
        String[] legacyTextParts = legacyText.split("§");
        for (String legacyTextPart : legacyTextParts) {
            if (legacyTextPart.length() == 0) continue;
            String legacyTextColor = "white";
            String legacyTextClear = legacyTextPart;
            if (json != "" || legacyText.startsWith("§")) {
                legacyTextColor = colorParser(legacyTextPart.charAt(0));
                legacyTextClear = legacyTextPart.substring(1);
            }
            String jsonTextPart;
            if (legacyTextClear == "") {
                jsonTextPart = "{\"color\":\"" + legacyTextColor + "\"";
                isBooleanCode = true;
            } else if (isBooleanCode) {
                jsonTextPart = "\"text\":\"" + legacyTextClear + "\",\"" + legacyTextColor + "\":true}";
                isBooleanCode = false;
            } else if (legacyTextColor == "bold" || legacyTextColor == "italic" || legacyTextColor == "underlined" || legacyTextColor == "strikethrough" || legacyTextColor == "obfuscated") {
                jsonTextPart = "{\"text\":\"" + legacyTextClear + "\",\"" + legacyTextColor + "\":true}";
                isBooleanCode = false;
            } else {
                jsonTextPart = "{\"text\":\"" + legacyTextClear + "\",\"color\":\"" + legacyTextColor + "\"}";
                isBooleanCode = false;
            }
            if (!json.equals("")) {
                json = json + ",";
            }
            json = json + jsonTextPart;
        }
        json = "[" + json + "]";
        return json;
    }
}
