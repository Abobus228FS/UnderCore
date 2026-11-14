package under.verse.core.command.builtin;

import under.verse.core.command.*;
import under.verse.core.UnderCore;
import under.verse.core.entity.Player;
import under.verse.core.Location;
import under.verse.core.Material;
import under.verse.core.world.Chunk;

public class SetblockCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        UnderCore.testMap.put((Player) sender, Material.valueOf(args[0]));
        return true;
    }
}
