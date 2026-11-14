package under.verse.core.network.packet.play;

import java.io.*;
import java.net.*;
import under.verse.core.utils.LegacyToJson;
import under.verse.core.packet.VarIntReader;
import java.nio.charset.*;
import under.verse.core.utils.ByteBuilder;
import under.verse.core.network.packet.ClientboundPacket;
import under.verse.core.network.packet.NextPacket;
import under.verse.core.network.DataOutStream;
import under.verse.core.network.NetworkManager;
import under.verse.core.command.Command;

public class ClientboundDeclareCommandsPacket extends ClientboundPacket {
    
    private byte[] bytes;
    public ClientboundDeclareCommandsPacket(Command... commands) {
        ByteBuilder bb = new ByteBuilder();
        bb.append((byte) (commands.length + 1)).append((byte) 0).append((byte) commands.length);
        byte i = 0;
        for (Command command : commands) {
            bb.append(++i);
        }
        for (Command command : commands) {
            bb.append((byte) 5).append((byte) 0).append((byte) command.getLabel().getBytes().length).append(command.getLabel().getBytes());
        }
        bb.append((byte) 0);
        this.bytes = bb.toByteArray();
    }
    public byte[] toByteArray() {
        return bytes;
    }
    public byte getId() {
        return 17;
    }
    public NextPacket write(DataOutStream out, NetworkManager manager) throws IOException {
        out.write(bytes);
        return null;
    }
}
