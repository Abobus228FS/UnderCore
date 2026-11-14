package under.verse.core.network.packet.login;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import under.verse.core.Logger;
import under.verse.core.UnderCore;
import under.verse.core.entity.Player;
import under.verse.core.event.EventManager;
import under.verse.core.event.player.PlayerJoinEvent;
import under.verse.core.network.packet.ServerboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataInStream;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkStage;
import under.verse.core.network.NetworkManager;

public class ServerboundLoginStartPacket extends ServerboundPacket {

    public NextPacket read(DataInStream in, NetworkManager manager) throws IOException {
        String name = in.readString();
        name = name.substring(2, name.length());
        Player player = manager.getPlayer();
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
        player.setName(name);
        player.setUniqueId(uuid);
        Logger.getLogger().info("UUID игрока " + name + " равен " + uuid);
        return new NextPacket(PacketSide.CLIENT, 2);
    }
    public byte getId() {
        return 0;
    }
}
