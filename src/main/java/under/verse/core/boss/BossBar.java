package under.verse.core.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import under.verse.core.network.packet.play.ClientboundBossBarPacket;
import under.verse.core.entity.Player;

public class BossBar {
    private BarColor color;
    private BarStyle style;
    private final List<Player> players = new ArrayList<>();
    private final UUID uuid = UUID.randomUUID();
    private String name = "";
    private float progress = 1.0f;
    
    public BossBar(BarColor color, BarStyle style) {
        this.color = color;
        this.style = style;
    }
    
    public BossBar(BarColor color, BarStyle style, List<Player> players) {
        this(color, style);
        for (Player player : players) this.players.add(player);
    }
    
    public BossBar(BarColor color, BarStyle style, String name, List<Player> players) {
        this(color, style, name);
        for (Player player : players) this.players.add(player);
    }
    
    public BossBar(BarColor color, BarStyle style, String name) {
        this(color, style);
        this.name = name;
    }
    public void setStyle(BarStyle style) {
        this.style = style;
        send(ClientboundBossBarPacket.Action.UPDATE_PCT);
    }
    public void setColor(BarColor color) {
        this.color = color;
        send(ClientboundBossBarPacket.Action.UPDATE_PCT);
    }
    public void setName(String name) {
        this.name = name;
        send(ClientboundBossBarPacket.Action.UPDATE_NAME);
    }
    public void addPlayer(Player p) {
        players.add(p);
        p.connection.send(new ClientboundBossBarPacket(this, ClientboundBossBarPacket.Action.ADD));
    }
    public void removePlayer(Player p) {
        players.remove(p);
        p.connection.send(new ClientboundBossBarPacket(this, ClientboundBossBarPacket.Action.REMOVE));
    }
    public void setPlayers(Player... p) {
        send(ClientboundBossBarPacket.Action.REMOVE);
        players.clear();
        for (Player pp : p) players.add(pp);
        send(ClientboundBossBarPacket.Action.ADD);
    }
    private final void send(ClientboundBossBarPacket.Action act) {
        ClientboundBossBarPacket packet = new ClientboundBossBarPacket(this, act);
        for (Player player : players) player.connection.send(packet);
    }
    public UUID getUniqueId() { return uuid; }
    public BarColor getColor() { return color; }
    public BarStyle getStyle() { return style; }
    public String getName() { return name; }
    public float getProgress() { return progress; }
}
