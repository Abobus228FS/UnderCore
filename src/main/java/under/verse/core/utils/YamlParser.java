package under.verse.core.utils;

import under.verse.core.network.packet.Packet;
import under.verse.core.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class YamlParser {

    private final InputStream in;
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    
    public YamlParser(InputStream in) throws IOException {
        this.in = in;
        for (String line : new String(in.readAllBytes(), StandardCharsets.UTF_8).split("\n")) {
            String[] l = line.split(":", 2);
            map.put(l[0], l[1].trim());
        }
    }
    public String readString(String key) {
        return map.get(key);
    }
}
