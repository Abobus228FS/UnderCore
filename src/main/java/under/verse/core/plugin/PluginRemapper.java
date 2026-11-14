package under.verse.core.plugin;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PluginRemapper {
    private PluginRemapper() {}
    private static final PluginRemapper instance = new PluginRemapper();
    
    static PluginRemapper get() { return instance; }
    
    public static File Remap(File inFile) throws IOException {
        File outFile = new File("plugins/remapped/" + inFile.getName());
        if (outFile.exists()) return outFile;
        ZipInputStream in = new ZipInputStream(new FileInputStream(inFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
        while (true) {
            ZipEntry entry = in.getNextEntry();
            if (entry == null) break;
            byte[] bytes = in.readAllBytes();
            boolean notClass = false;
            if (bytes[0] != 0xCA ||
                bytes[1] != 0xFE ||
                bytes[2] != 0xBA ||
                bytes[3] != 0xBE) notClass = true;
            short value = (short) ((bytes[8] << 8) | (bytes[9] & 0xFF));
            for (int i = 0; i < value; i++) {
                
            }
            in.closeEntry();
        }
        in.close();
        return outFile;
    }
}
