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
import under.verse.core.Location;

public class ClientboundPlayerPositionAndLookPacket extends ClientboundPacket {

    private Location location;
    
    public ClientboundPlayerPositionAndLookPacket(Location location) {
        this.location = location;
    }

    public NextPacket write(DataOutStream out, NetworkManager manager) {
        out.write(location.getX());
        out.write(location.getY());
        out.write(location.getZ());
        out.write(location.getYaw());
        out.write(location.getPitch());
        out.write((byte) 0);
        out.writeVarint(1);
        return null;
    }
    public byte getId() {
        return 50;
    }
}
