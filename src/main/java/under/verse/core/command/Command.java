package under.verse.core.command;

import java.util.concurrent.ConcurrentHashMap;

public class Command {

    private static final ConcurrentHashMap<Command, CommandExecutor> commandMap = new ConcurrentHashMap<>();
    private CommandExecutor executor;
    private final String label;
    
    public Command(String label) {
        this.label = label;
    }
    
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }
    public boolean execute(CommandSender sender, String label, String[] args) throws Throwable {
        return executor.onCommand(sender, label, args);
    }
    public CommandExecutor getExecutor() {
        return executor;
    }
    public String getLabel() {
        return label;
    }
    @Override
    public String toString() {
        return "Command{label=" + label + "}";
    }
}
