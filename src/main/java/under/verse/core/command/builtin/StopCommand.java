package under.verse.core.command.builtin;

import under.verse.core.command.*;
import under.verse.core.UnderCore;

public class StopCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        UnderCore.shutdown();
        return true;
    }
}
