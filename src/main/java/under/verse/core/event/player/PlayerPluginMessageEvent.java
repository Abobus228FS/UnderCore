package under.verse.core.event.player;

import under.verse.core.entity.Player;
import under.verse.core.NamespaceKey;

public class PlayerPluginMessageEvent extends PlayerEvent {
    private byte[] message;
    private NamespaceKey key;
    public PlayerPluginMessageEvent(Player player, NamespaceKey key, byte[] message) {
        super(player);
        this.message = message;
        this.key = key;
    }
    public byte[] getMessage() {
        return message;
    }
    public void setMessage(byte[] message) {
        this.message = message;
    }
    public NamespaceKey getKey() {
        return key;
    }
    public void setKey(NamespaceKey key) {
        this.key = key;
    }
    public void run() {}
}
