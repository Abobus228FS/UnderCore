package under.verse.core.command.builtin;

import under.verse.core.command.*;
import under.verse.core.plugin.Plugin;
import under.verse.core.plugin.PluginManager;

public class PluginsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        boolean b = false;
        int i = 0;
        for (Plugin plugin : PluginManager.getAllPlugins()) {
            i++;
            if (b) sb.append(", ");
            else b = true;
            sb.append(plugin.getName());
        }
        sender.sendMessage("Плагинов: " + i);
        sender.sendMessage(sb.toString());
        return true;
    }
}
