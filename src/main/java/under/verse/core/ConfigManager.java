package under.verse.core;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

public class ConfigManager {
    private static final File file = new File("server.properties");
    private static final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    private static final List<String> intList = List.of("server-port", "max-players");
    static {
      if (!file.exists()) {
          Logger.getLogger().info("Конфиг не найден! Создан стандартный");
          map.put("server-ip", "0.0.0.0");
          map.put("server-port", 30006);
          map.put("server-motd", "The §6UnderCore §fserver");
          map.put("max-players", 10);
          try (FileOutputStream out = new FileOutputStream(file)) {
              map.forEach((key, value) -> {
                  try {
                      out.write((key + "=" + value + "\n").getBytes());
                  } catch (IOException e) {
                      Logger.getLogger().fatal(e);
                      return;
                  }
              });
              out.flush();
          } catch (IOException ioe) {
              Logger.getLogger().fatal(ioe);
          }
      } else {
          try (FileInputStream in = new FileInputStream(file)) {
              for (String str : new String(in.readAllBytes(), StandardCharsets.UTF_8).split("\n")) {
                  String[] strr = str.split("=", 2);
                  if (strr.length == 2) {
                      if (intList.contains(strr[0])) map.put(strr[0], Integer.parseInt(strr[1]));
                      else map.put(strr[0], strr[1]);
                  }
              }
          } catch (Exception e) {
              Logger.getLogger().fatal(e);
          }
      }
    }
    public String getString(String key, String stValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String value) return value;
        else return stValue;
    }
    public int getInt(String key, int stValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof Integer value) return value;
        else return stValue;
    }
}
