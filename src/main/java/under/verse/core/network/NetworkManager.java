package under.verse.core.network;

import java.io.*;
import java.net.*;
import under.verse.core.NamespaceKey;
import under.verse.core.world.Chunk;
import under.verse.core.entity.Player;
import under.verse.core.network.packet.play.*;
import under.verse.core.network.packet.login.*;
import under.verse.core.network.packet.handshake.*;
import under.verse.core.network.packet.status.*;
import under.verse.core.network.packet.*;
import under.verse.core.UnderCore;
import under.verse.core.Logger;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import under.verse.core.event.EventManager;
import under.verse.core.event.player.PlayerQuitEvent;
import under.verse.core.event.player.PlayerChatEvent;
import under.verse.core.event.player.PlayerJoinEvent;
import under.verse.core.event.player.PlayerCommandEvent;

public class NetworkManager {

    private final DataOutStream out;
    private final DataInStream in;
    private final Socket socket;
    private static ServerSocket serverSocket;
    private final Player player;
    private boolean writing;
    private NetworkStage stage = NetworkStage.HANDSHAKE;
    private final List<Chunk> chunks = new ArrayList<>();
    private final List<ClientboundPacket> packetList = new ArrayList<>();
    private static final HashMap<NetworkStage, HashMap<Integer, ClientboundPacket>> clientboundPackets = new HashMap<>();
    private static final HashMap<NetworkStage, HashMap<Integer, ServerboundPacket>> serverboundPackets = new HashMap<>();
    static {
        clientboundPackets.put(NetworkStage.STATUS, new HashMap<>());
        clientboundPackets.put(NetworkStage.HANDSHAKE, new HashMap<>());
        clientboundPackets.put(NetworkStage.LOGIN, new HashMap<>());
        clientboundPackets.put(NetworkStage.PLAY, new HashMap<>());
        serverboundPackets.put(NetworkStage.STATUS, new HashMap<>());
        serverboundPackets.put(NetworkStage.HANDSHAKE, new HashMap<>());
        serverboundPackets.put(NetworkStage.LOGIN, new HashMap<>());
        serverboundPackets.put(NetworkStage.PLAY, new HashMap<>());
        serverboundPackets.get(NetworkStage.STATUS).put(0, new ServerboundServerListStatusRequestPacket());
        clientboundPackets.get(NetworkStage.STATUS).put(0, new ClientboundServerListStatusResponsePacket());
        serverboundPackets.get(NetworkStage.STATUS).put(1, new ServerboundServerListPingRequestPacket());
        clientboundPackets.get(NetworkStage.STATUS).put(1, new ClientboundServerListPingResponsePacket());
        serverboundPackets.get(NetworkStage.HANDSHAKE).put(0, new ServerboundHandshakePacket());
        serverboundPackets.get(NetworkStage.LOGIN).put(0, new ServerboundLoginStartPacket());
        clientboundPackets.get(NetworkStage.LOGIN).put(2, new ClientboundLoginSuccessPacket());
        clientboundPackets.get(NetworkStage.PLAY).put(37, new ClientboundJoinGamePacket());
        serverboundPackets.get(NetworkStage.PLAY).put(14, new ServerboundKeepAlivePacket());
        clientboundPackets.get(NetworkStage.PLAY).put(33, new ClientboundKeepAlivePacket());
        clientboundPackets.get(NetworkStage.PLAY).put(0x2E, new ClientboundPlayerAbilitiesPacket());
        serverboundPackets.get(NetworkStage.PLAY).put(10, new ServerboundPluginMessagePacket());
        serverboundPackets.get(NetworkStage.PLAY).put(24, new ServerboundPlayerDiggingPacket());
        serverboundPackets.get(NetworkStage.PLAY).put(17, new ServerboundMovePlayerPacket());
        serverboundPackets.get(NetworkStage.PLAY).put(16, new ServerboundMovePlayerPacket2());
        serverboundPackets.get(NetworkStage.PLAY).put(16, new ServerboundMovePlayerPacket2());
        serverboundPackets.get(NetworkStage.PLAY).put(0x29, new ServerboundPlayerBlockPlacementPacket());
        serverboundPackets.get(NetworkStage.PLAY).put(2, new ServerboundChatPacket());
    }
    
    public NetworkManager(Player player, Socket socket) {
        this.player = player;
        this.socket = socket;
        try {
            out = new DataOutStream(socket.getOutputStream());
            in = new DataInStream(socket.getInputStream());
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public void start() {
        new Thread(() -> {
            try {
                Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                        send(clientboundPackets.get(NetworkStage.PLAY).get(33));
                }, 5L, 10L, TimeUnit.SECONDS);
                under.verse.core.network.packet.Packet nextPacket = null;
                while (true) {
                    if (!packetList.isEmpty()) {
                        ClientboundPacket packet = packetList.removeFirst();
                        out.write(packet.getId());
                        packet.write(out, this);
                        out.flush();
                        continue;
                    }
                    if (nextPacket != null) {
                        if (nextPacket instanceof ClientboundPacket packet) {
                            out.write(packet.getId());
                            System.out.println(packet.getClass().getSimpleName());
                            NextPacket np = packet.write(out, this);
                            System.out.println(packet.getClass().getSimpleName() + " wawa");
                            out.flush();
                            if (np == null) {
                                nextPacket = null;
                                continue;
                            }
                            if (np.side() == PacketSide.CLIENT) nextPacket = clientboundPackets.get(stage).get(np.id());
                            else nextPacket = serverboundPackets.get(stage).get(np.id());
                        }
                        if (nextPacket instanceof ServerboundPacket packet) {
                            NextPacket np = packet.read(in, this);
                            if (np == null) {
                                nextPacket = null;
                                continue;
                            }
                            if (np.side() == PacketSide.CLIENT) nextPacket = clientboundPackets.get(stage).get(np.id());
                            else nextPacket = serverboundPackets.get(stage).get(np.id());
                        }
                        continue;
                    }
                    int packetLength = in.readVarint();
                    if (packetLength == -1) {
                        System.out.println("555");
                        break;
                    }
                    if (packetLength == 0) continue;
                    player.setData(NamespaceKey.create("undercore", "packet_length"), packetLength);
                    int packetId = in.readVarint();
                    ServerboundPacket sPacket = serverboundPackets.get(stage).get(packetId);
                    if (sPacket == null) {
                        in.skip(packetLength - 1);
                        continue;
                    }
                    NextPacket nextPackettt = sPacket.read(in, this);
                    if (nextPackettt == null) continue;
                    if (nextPackettt.side() == PacketSide.SERVER) {
                        ServerboundPacket nextPackett = serverboundPackets.get(stage).get(nextPackettt.id());
                        if (nextPackett == null) continue;
                        nextPacket = nextPackett;
                    }
                   // ServerboundPacket packet();
               }
                        socket.close();
                        System.out.println("111");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        System.out.println("222");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        UnderCore.players.remove(player);
                        System.out.println("333");
                        if (stage != NetworkStage.STATUS) EventManager.throwEvent(new PlayerQuitEvent(player));
                    }
        }).start();
    }
    public static void startServer() {
        try {
            int serverPort = UnderCore.getConfig().getInt("server-port", 30006);
            serverSocket = new ServerSocket(serverPort);
            Logger.getLogger().info("Сервер стартовал на 0.0.0.0:" + serverPort);
        } catch (Exception e) {
            Logger.getLogger().fatal(e);
            UnderCore.shutdown();
            return;
        }
        try {
            while (true) {
                Socket sockett = serverSocket.accept();
                Player player = new Player(sockett);
                UnderCore.players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Player getPlayer() {
        return player;
    }
    public void setStage(NetworkStage stage) {
        this.stage = stage;
    }
    public void send(ClientboundPacket packet) {
        packetList.add(packet);
    }
    public DataOutStream getOut() {
        return out;
    }
    public DataInStream getIn() {
        return in;
    }
    public Socket getSocket() {
        return socket;
    }
    public void addChunk() {
    }
}
