package under.verse.core.command;

import under.verse.core.Logger; 

public class ConsoleSender implements CommandSender {

    public boolean hasPermission(String permission) {
        return true;
    }
    private ConsoleSender() {
        throw new UnsupportedOperationException("Синглтон!");
    }
    public void sendMessage(String msg) {
        Logger.getLogger().info(msg);
    }
}
