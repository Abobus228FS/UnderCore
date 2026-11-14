package under.verse.core.plugin;

import under.verse.core.command.CommandExecutor;
import under.verse.core.command.CommandManager;
import under.verse.core.command.CommandSender;
import under.verse.core.event.EventManager;
import under.verse.core.event.Listener;
import under.verse.core.event.EventHandler;
import under.verse.core.event.player.PlayerJoinEvent;
import under.verse.core.Logger;

public abstract class JavaPlugin implements CommandExecutor, Listener {
    private Plugin plugin;
    
    public final Logger getLogger() {
        return plugin.getLogger();
    }
    public final void setParent(Plugin plugin) {
        this.plugin = plugin;
    }
    public final Plugin getParent() {
        return plugin;
    }
    public final CommandManager getCommandManager() {
        return CommandManager.get();
    }
    public final EventManager getEventManager() {
        return EventManager.get();
    }
    public abstract void onEnable();
    public void onDisable() {}
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        return true;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {}
}
