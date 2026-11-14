package under.verse.core;

import under.verse.core.network.NetworkManager;
import under.verse.core.network.packet.play.*;
import under.verse.core.command.CommandManager;
import under.verse.core.command.ConsoleSender;
import under.verse.core.entity.Player;
import under.verse.core.plugin.PluginManager;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Scanner;
import java.util.HashMap;
import sun.reflect.ReflectionFactory;
import java.lang.reflect.Constructor;

public final class UnderCore {
    private static final long startTime = System.currentTimeMillis();
    public static final HashMap<Player, Material> testMap = new HashMap<>();
    private static ConfigManager configManager;
    private static final ConsoleSender console;
    public static final CopyOnWriteArraySet<Player> players = new CopyOnWriteArraySet<>();
    private static final CopyOnWriteArraySet<World> worlds = new CopyOnWriteArraySet<>();
    private static volatile boolean cancelled = false;
    private static final UnderCore instance = new UnderCore();
    
    static {
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor objConstructor = Object.class.getDeclaredConstructor();
            Constructor silentConstructor = rf.newConstructorForSerialization(ConsoleSender.class, objConstructor);
            console = (ConsoleSender) silentConstructor.newInstance();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static void main(String[] startArgs) {
       /* Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (cancelled) Runtime.getRuntime().halt(0);
            cancelled = true;
            UnderCore.shutdown();
        }));*/
        configManager = new ConfigManager();
        under.verse.core.event.EventManager.registerListener(new under.verse.core.event.player.TestEventer());
        PluginManager.loadAllPlugins();
        CompletableFuture.runAsync(() -> NetworkManager.startServer());
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] inputCommand = input.split(" ");
                String command = inputCommand[0];
                String[] args = Arrays.copyOfRange(inputCommand, 1, inputCommand.length);
                CommandManager.runCommand(console, command, args);
            }
            scanner.close();
        }).start();
        Logger.getLogger().info("Закончено (" + String.format("%.3f", (double)(System.currentTimeMillis() - startTime) / 1000.0).replace(',', '.') + "s)! Для помощи, напишите \"help\"");
    }
    
    public static ConfigManager getConfig() { return configManager; }
    public static UnderCore get() { return instance; }
    
    public static void broadcastMessage(String msg) {
        Logger.getLogger().info(msg);
        for (Player player : players) {
            player.sendMessage(msg);
        }
    }
    public static void shutdown() {
        Logger.getLogger().info("Выключение...");
        for (Player player : players) player.kick("Выключение");
        PluginManager.unloadAllPlugins();
        System.exit(0);
    }
    public static World getWorld(String world) {
        return null;
    }
    
    @Override
    public String toString() {
        return "UnderCore";
    }
}
