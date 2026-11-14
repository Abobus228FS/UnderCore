package under.verse.core.plugin;

import under.verse.core.Logger;

public class Plugin {
    private final String name;
    private final String description;
    private final Logger logger;
    private final JavaPlugin plugin;
    public Plugin(JavaPlugin plugin, String name, String description) {
        plugin.setParent(this);
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.logger = Logger.getLogger(name);
    }
    
    public final Logger getLogger() {
        return logger;
    }
    public final JavaPlugin getHandle() {
        return plugin;
    }
    public final String getName() {
        return name;
    }
}
