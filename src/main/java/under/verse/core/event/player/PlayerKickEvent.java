package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;
import under.verse.core.network.packet.play.ClientboundKickPacket;

public class PlayerKickEvent extends PlayerEvent implements Cancellable {
    private String message;
    private boolean cancel;
    public PlayerKickEvent(Player player, String message) {
        super(player);
        this.message = message;
    }
    public String getReason() {
        return message;
    }
    public void setReason(String message) {
        this.message = message;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void run() {
        if (!cancel) getPlayer().connection.send(new ClientboundKickPacket(message));
    }
}
