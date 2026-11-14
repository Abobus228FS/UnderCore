package under.verse.core.packet;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class SUpdateLight {

    public static void writePacket(InputStream in, OutputStream out) throws IOException {
    out.write(9);
    out.write(37);
    out.write(0);
    out.write(0);
    out.write(0);
    out.write(0);
    out.write(0);
    out.write(0);
    out.write(0);
    out.write(0);
    out.flush();
    }
}
