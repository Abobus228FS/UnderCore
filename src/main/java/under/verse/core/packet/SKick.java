package under.verse.core.packet;

import java.io.*;
import java.net.*;

public class SKick {

    public static void writePacket(InputStream in, OutputStream out) throws IOException {
        out.write(74);
        out.write(0);
        out.write("{\"text\":\"Неа, прости, но похоже на бот атаку\"}".getBytes());
        out.flush();
    }
}
