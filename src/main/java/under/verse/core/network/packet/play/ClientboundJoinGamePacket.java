package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.entity.Player;
import under.verse.core.event.EventManager;
import under.verse.core.event.player.PlayerJoinEvent;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.PacketSide;
import under.verse.core.network.NetworkStage;
import under.verse.core.network.NetworkManager;
import under.verse.core.network.DataOutStream;
import under.verse.core.Logger;

public class ClientboundJoinGamePacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) {
        out.write(manager.getPlayer().getId());
        out.write((byte) 0);
        out.write((int) 0);
        out.write((byte) 0);
        out.write((byte) 1);
        out.write("flat");
        out.write((byte) 0);
        Logger.getLogger().info("Игрок " + manager.getPlayer().getName() + " появился с айди " + manager.getPlayer().getId() + " на координатах " + "координатпоканет");
        EventManager.throwEvent(new PlayerJoinEvent(manager.getPlayer()));
        return null;
    }
    public byte getId() {
        return 37;
    }
}
