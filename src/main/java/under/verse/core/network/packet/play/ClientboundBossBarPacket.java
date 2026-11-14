package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.boss.BossBar;

public class ClientboundBossBarPacket extends ClientboundPacket {
    
    private final BossBar bar;
    private final Action act;
    
    public ClientboundBossBarPacket(BossBar bar, Action act) {
        this.bar = bar;
        this.act = act;
    }
    
    public static enum Action {
        ADD,
        REMOVE,
        UPDATE_PCT,
        UPDATE_NAME,
        UPDATE_STYLE,
        UPDATE_PROPERTIES;
    }
    
    public byte getId() {
        return 0x0B;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) {
    //    out.write(bar.getUniqueId());
        out.write((byte) act.ordinal());
        return null;
    }
}
