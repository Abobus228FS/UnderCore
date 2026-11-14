package under.verse.core.command.builtin;

import under.verse.core.command.*;
import under.verse.core.UnderCore;
import under.verse.core.entity.Player;

public class TitleCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        ((Player)sender).sendTitle(args[0], args[1], 10, 100, 10);
        return true;
    }
}
