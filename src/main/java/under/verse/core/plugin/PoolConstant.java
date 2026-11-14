package under.verse.core.plugin;

public class PoolConstant {

    private final int id;
    private byte[] val;
    
    PoolConstant(int id, byte[] val) {
        this.id = id;
        this.val = val;
    }
    public String getType() {
        return switch (id) {
            case 1 -> "UTF_8";
            case 3 -> "INTEGER";
            case 4 -> "FLOAT";
            case 5 -> "LONG";
            case 6 -> "DOUBLE";
            case 7 -> "CLASS";
            case 8 -> "STRING";
            case 9 -> "FIELD_REF";
            case 10 -> "METHOD_REF";
            case 11 -> "INTERFACE_METHOD_REF";
            case 12 -> "NAME_AND_TYPE";
            case 15 -> "METHOD_HANDLE";
            case 16 -> "METHOD_TYPE";
            case 17 -> "DYNAMIC";
            case 18 -> "INVOKE_DYNAMIC";
            case 19 -> "MODULE";
            case 20 -> "PACKAGE";
            default -> throw new IllegalArgumentException("Неизвестный тег: " + id);
        };
    }

    public int getLength() {
        return switch (id) {
            case 1 -> -1;
            case 3, 4 -> 5;
            case 5, 6 -> 9;
            case 7, 8, 16, 19, 20 -> 3;
            case 9, 10, 11, 12, 17, 18 -> 5;
            case 15 -> 4;
            default -> throw new IllegalArgumentException("Неизвестный тег: " + id);
        };
    }
}
