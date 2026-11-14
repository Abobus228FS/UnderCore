package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;
import under.verse.core.command.CommandManager;
import java.util.Arrays;

public class PlayerCommandEvent extends PlayerEvent implements Cancellable {
    private String label;
    private String[] args;
    private boolean cancel = false;
    public PlayerCommandEvent(Player player, String command) {
        super(player);
        String[] commandd = command.split(" ");
        this.label = commandd[0];
        this.args = Arrays.copyOfRange(commandd, 1, commandd.length);
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String[] getArgs() {
        return args;
    }
    public void setArgs(String[] args) {
        this.args = args;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void run() {
        if (!cancel) CommandManager.runCommand(getPlayer(), label, args);
    }
}
