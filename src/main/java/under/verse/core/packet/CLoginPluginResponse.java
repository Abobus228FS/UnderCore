package under.verse.core.packet;

import java.io.*;
import java.net.*;

public class CLoginPluginResponse {

    public static void readPacket(InputStream in, OutputStream out) throws IOException {
        int length = VarIntReader.readVarInt(in);
        in.skip(1);
        if (in.read() != 1) in.close();
        if (in.read() != 1) in.close();
        in.skip(33);
        int i = VarIntReader.readVarInt(in);
        in.skip(i);
        in.skip(8);
        in.skip(length - (i + 45));
        out.write(3);
        out.write(2);
        out.write(1);
        out.write(1);
        out.flush();
    }
}
