package under.verse.core.packet;

import java.io.*;
import java.net.*;

public class SLoginPluginRequest {

    public static void writePacket(InputStream in, OutputStream out) throws IOException {
        out.write(23);
        out.write(4);
        out.write(1);
        out.write(20);
        out.write("velocity:player_info".getBytes());
        out.flush();
    }
}
