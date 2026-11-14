package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;

public class PlayerChatEvent extends PlayerEvent implements Cancellable {
    private String message;
    private String format;
    private boolean cancel;
    public PlayerChatEvent(Player player, String message) {
        super(player);
        this.message = message;
        this.format = "<%s> %s";
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void run() {
        if (message != null && !cancel) UnderCore.broadcastMessage(String.format(format, getPlayer().getName(), message));
    }
}
