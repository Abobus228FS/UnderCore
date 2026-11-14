package under.verse.core.entity;

import java.io.*;
import java.net.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import under.verse.core.network.packet.play.*;
import under.verse.core.Location;
import under.verse.core.event.EventManager;
import under.verse.core.event.player.PlayerKickEvent;
import under.verse.core.packet.*;
import under.verse.core.network.packet.Packet;
import under.verse.core.network.DataInStream;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.utils.Component;
import under.verse.core.NamespaceKey;
import under.verse.core.command.CommandSender;
import under.verse.core.UnderCore;

public class Player extends Entity implements CommandSender {
    private String name;
    private String joinAddress;
    private Location location = new Location(0, 256, 0);
    private long ping = 0;
    public final NetworkManager connection;
    
    public Player(Socket socket) throws IOException {
        this.connection = new NetworkManager(this, socket);
        CompletableFuture.runAsync(() -> connection.start());
    }
    
    public void sendMessage(String msg) {
        this.connection.send(new ClientboundChatPacket(msg));
    }
    
    public void sendMessage(Component msg) {
        this.connection.send(new ClientboundChatPacket(msg));
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setJoinAddress(String joinAddress) {
        this.joinAddress = joinAddress;
    }
    public String getJoinAddress() {
        return joinAddress;
    }
    
    public void kick(String msg) {
        EventManager.throwEvent(new PlayerKickEvent(this, msg));
    }
    public boolean hasPermission(String permission) {
        return true;
    }
    public void setPing(long ping) {
        this.ping = ping;
    }
    public long getPing() {
        return ping;
    }
    public void sendPluginMessage(NamespaceKey key, byte[] data) {
        connection.send(new ClientboundPluginMessagePacket(key, data));
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void teleport(Location location) {
        this.location = location;
        for (Player player : UnderCore.players) {
            player.connection.send(new ClientboundPlayerPositionAndLookPacket(location));
        }
    }
    public Location getLocation() {
        return location;
    }
    public void sendTitle(String title, String subtitle, int var1, int var2, int var3) {
        this.connection.send(new ClientboundTitlePacket(3, var1, var2, var3));
        this.connection.send(new ClientboundTitlePacket(1, subtitle));
        this.connection.send(new ClientboundTitlePacket(0, title));
    }
    @Override
    public String toString() {
        return "Player{name=" + name + "}";
    }
}
