package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;
import under.verse.core.Location;

public class PlayerMoveEvent extends PlayerEvent implements Cancellable {
    private Location location;
    private boolean orig = true;
    private boolean cancel;
    public PlayerMoveEvent(Player player, Location location) {
        super(player);
        this.location = location;
    }
    public Location getFrom() {
        return getPlayer().getLocation();
    }
    public Location getTo() {
        return location;
    }
    public void setTo(Location location) {
        this.location = location;
        orig = false;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void run() {
        if (location != null && !cancel) {
            if (orig) getPlayer().setLocation(location);
            else getPlayer().teleport(location);
        }
        else getPlayer().teleport(getPlayer().getLocation());
    }
}
