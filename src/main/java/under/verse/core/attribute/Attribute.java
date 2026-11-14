package under.verse.core.attribute;

import under.verse.core.entity.Entity;

public class Attribute {

    private Entity entity;
    private AttributeType type;

    public Attribute(AttributeType type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }
}
