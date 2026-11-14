package under.verse.core.event.player;

import under.verse.core.event.Event;
import under.verse.core.entity.Player;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;

public abstract class PlayerEvent extends Event {
    private final Player player;
    
    public PlayerEvent(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
}
