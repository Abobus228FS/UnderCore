package under.verse.core.network.packet.play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.event.player.PlayerChatEvent;
import under.verse.core.event.player.PlayerPluginMessageEvent;
import under.verse.core.event.EventManager;
import under.verse.core.NamespaceKey;
import under.verse.core.entity.Player;

public class ServerboundPluginMessagePacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        int channelLength = in.readVarint();
        String[] channel = new String(in.readNBytes(channelLength), StandardCharsets.UTF_8).split(":", 2);
        NamespaceKey key = NamespaceKey.create(channel[0], channel[1]);
        Player player = manager.getPlayer();
        EventManager.throwEvent(new PlayerPluginMessageEvent(player, key, in.readNBytes(((Integer) player.getData(NamespaceKey.create("undercore", "packet_length"))) - channelLength - 2)));
        return null;
    }
    
    public byte getId() {
        return 10;
    }
}
