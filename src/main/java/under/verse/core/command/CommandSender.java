package under.verse.core.command;

public interface CommandSender {
    public boolean hasPermission(String permission);
    public void sendMessage(String msg);
}
