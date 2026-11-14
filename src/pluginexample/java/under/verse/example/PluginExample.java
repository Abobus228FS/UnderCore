package under.verse.example;

import under.verse.core.plugin.JavaPlugin;
import under.verse.core.Logger;
import under.verse.core.event.player.PlayerJoinEvent;

public class PluginExample extends JavaPlugin {

    public void onEnable() {
        Logger.getLogger("plugin").info("я загрузился!");
        getEventManager().registerListener(this);
    }
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("это сообщение отправил " + this + "!");
    }
}
