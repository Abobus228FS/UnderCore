package under.verse.core.entity;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import under.verse.core.NamespaceKey;

public abstract class Entity {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private UUID uuid;
    private final int id = idCounter.incrementAndGet();
    private final ConcurrentHashMap<NamespaceKey, Object> map = new ConcurrentHashMap<>();
    
    public int getId() { return id; }
    public UUID getUniqueId() { return uuid; }
    public void setUniqueId(UUID uuid) { this.uuid = uuid; }
    public void setData(NamespaceKey key, Object obj) { map.put(key, obj); }
    public Object getData(NamespaceKey key) { return map.get(key); }
}
