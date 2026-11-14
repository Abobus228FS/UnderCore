package under.verse.core.command;

public interface CommandExecutor {
    public boolean onCommand(CommandSender sender, String label, String[] args) throws Throwable;
}
