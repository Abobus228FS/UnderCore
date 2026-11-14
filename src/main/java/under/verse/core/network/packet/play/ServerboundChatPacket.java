package under.verse.core.network.packet.play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.event.player.PlayerChatEvent;
import under.verse.core.event.player.PlayerCommandEvent;
import under.verse.core.event.EventManager;
import under.verse.core.entity.Player;
import under.verse.core.Logger;

public class ServerboundChatPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        String msg = new String(in.readNBytes(in.readVarint()), StandardCharsets.UTF_8);
        Player player = manager.getPlayer();
        if (msg.contains("§") || msg.contains("\u001B")) {
            player.kick("Нелегальные символы в чате");
            return null;
        }
        if (msg.startsWith("/")) {
            Logger.getLogger().info(player.getName() + " Выполняет команду " + msg);
            EventManager.throwEvent(new PlayerCommandEvent(player, msg.substring(1, msg.length())));
        }
        else EventManager.throwEvent(new PlayerChatEvent(player, msg));
        return null;
    }
    
    public byte getId() {
        return 14;
    }
}
