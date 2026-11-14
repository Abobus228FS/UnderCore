package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.UnderCore;

public class PlayerQuitEvent extends PlayerEvent {
    private String message;
    public PlayerQuitEvent(Player player) {
        super(player);
        message = "§e" + player.getName() + " отключился";
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void run() {
        if (message != null) UnderCore.broadcastMessage(message);
    }
}
