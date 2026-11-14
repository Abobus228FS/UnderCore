package under.verse.core.plugin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import under.verse.core.Logger;
import under.verse.core.utils.YamlParser;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

public class PluginManager {
    
    private static final CopyOnWriteArrayList<Plugin> pluginList = new CopyOnWriteArrayList<>();
    private static final PluginManager instance = new PluginManager();
    private static final Logger logger = Logger.getLogger("PluginManager");
    
    public static void loadAllPlugins() {
        try {
        File dir = new File("plugins");
        if (!dir.exists()) dir.mkdir();
        if (!dir.isDirectory()) {
            logger.fatal("plugins не является директорией!");
            return;
        }
        File[] jars = dir.listFiles();
        if (jars == null) return;
        for (File file : jars) {
            URL url = file.toURI().toURL();
            try (URLClassLoader loader = new URLClassLoader(new URL[] { url })) {
                YamlParser yp = new YamlParser(loader.getResourceAsStream("undercore.yml"));
                String name = yp.readString("name");
                String description = yp.readString("description");
                String mainPath = yp.readString("main");
                Class<?> main = loader.loadClass(mainPath);
                JavaPlugin javaPlugin = (JavaPlugin) main.getDeclaredConstructor().newInstance();
                Plugin pluginInstance = new Plugin(javaPlugin, name, description);
                pluginInstance.getHandle().onEnable();
                pluginInstance.getLogger().info("Плагин успешно загружен");
                pluginList.add(pluginInstance);
            } catch (Throwable t) {
                if (t instanceof VirtualMachineError) System.exit(1);
                logger.error(t);
            }
        }
        } catch (Throwable t) { t.printStackTrace(); }
    }
    private PluginManager() {}
    public static PluginManager get() {
        return instance;
    }
    public static Plugin[] getAllPlugins() {
        return pluginList.toArray(new Plugin[0]);
    }
    public static void unloadAllPlugins() {
        for (Plugin plugin : pluginList) {
            plugin.getHandle().onDisable();
            plugin.getLogger().info("Успешное выключение плагина");
        }
    }
}
