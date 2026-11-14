package under.verse.core.command;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import under.verse.core.Logger;
import under.verse.core.command.builtin.*;

public class CommandManager {
    
    private static final ConcurrentHashMap<String, Command> commandMap = new ConcurrentHashMap<>();
    private static final CopyOnWriteArrayList<Command> commandList = new CopyOnWriteArrayList<>();
    private static final CommandManager instance = new CommandManager();
    
    static {
        instance.getCommand("plugins").setExecutor(new PluginsCommand());
        instance.getCommand("lookup").setExecutor(new LookupCommand());
        instance.getCommand("stop").setExecutor(new StopCommand());
        instance.getCommand("title").setExecutor(new TitleCommand());
        instance.getCommand("setblock").setExecutor(new SetblockCommand());
    }
    
    public static void runCommand(CommandSender sender, String label, String[] args) {
        boolean endSuccessful = true;
        try {
            if (!commandMap.containsKey(label)) {
                sender.sendMessage("§cКоманда не найдена!");
                return;
            }
            Command command = instance.getCommand(label);
            endSuccessful = command.execute(sender, label, args);
        } catch (Throwable t) {
            if (t instanceof VirtualMachineError) System.exit(1);
            Logger.getLogger().error(t);
            endSuccessful = false;
        } finally {
            if (!endSuccessful) {
                sender.sendMessage("§cКоманда не найдена!");
            }
        }
    }
    public Command getCommand(String label) {
        Command command = commandMap.get(label);
        if (command == null) {
            Command newCommand = new Command(label);
            commandMap.put(label, newCommand);
            commandList.add(newCommand);
            return newCommand;
        }
        return command;
    }
    private CommandManager() {}
    public static CommandManager get() {
        return instance;
    }
    public static Command[] getAllCommands() {
        return commandList.toArray(new Command[0]);
    }
}
