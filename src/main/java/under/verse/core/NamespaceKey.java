package under.verse.core;

import java.util.concurrent.ConcurrentHashMap;

public class NamespaceKey {
    
    private static final ConcurrentHashMap<String, NamespaceKey> map = new ConcurrentHashMap<>();
    
    private final String key;
    private final String value;
    
    private NamespaceKey(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public static NamespaceKey create(String namespace, String value) {
        String keyy = namespace + ":" + value;
        NamespaceKey nk = map.get(keyy);
        if (nk == null) {
            NamespaceKey newNk = new NamespaceKey(namespace, value);
            map.put(keyy, newNk);
            return newNk;
        }
        return nk;
    }
    
    @Override
    public String toString() {
        return key + ":" + value;
    }
    public String key() { return key; }
    public String value() { return value; }
}
