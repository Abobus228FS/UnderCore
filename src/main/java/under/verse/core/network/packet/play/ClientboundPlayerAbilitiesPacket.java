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

public class ClientboundPlayerAbilitiesPacket extends ClientboundPacket {

    public NextPacket write(DataOutStream out, NetworkManager manager) {
        out.write((byte) 0x07);
        out.write((float) 0.05f);
        out.write((float) 0.1f);
        return null;
    }
    public byte getId() {
        return 0x2E;
    }
}
