package under.verse.core.attribute;

public enum AttributeType {
    MAX_HEALTH(20.0);
    
    private final double base;
    
    AttributeType(double base) {
        this.base = base;
    }
    public double getBaseValue() {
        return base;
    }
}
