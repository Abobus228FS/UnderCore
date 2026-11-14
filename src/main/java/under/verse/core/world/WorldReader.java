package under.verse.core.world;

import java.io.*;
import under.verse.core.packet.VarIntReader;
import java.util.HashMap;
import under.verse.core.Material;

public class WorldReader {
    
    public static void readChunk(String world, Chunk chunk) throws IOException {
        File worldsFolder = new File("worlds");
        if (!(worldsFolder.exists()) || !(worldsFolder.isDirectory())) {
            worldsFolder.mkdir();
        }
        File worldFolder = new File("worlds/" + world);
        if (!(worldFolder.exists()) || !(worldFolder.isDirectory())) {
            worldFolder.mkdir();
        }
        try (FileInputStream file = new FileInputStream("worlds/" + world + "/regions/rg.rg")) {
            String[] lines = new String(file.readAllBytes()).split("\n");
            for (String temp : lines) {
                 String[] line = temp.split(":");
                 String[] loc = line[0].split("/");
                 chunk.getBlocks()[Integer.parseInt(loc[0])][Integer.parseInt(loc[1])][Integer.parseInt(loc[2])].setType(Material.valueOf(line[1].trim()));
            }
        }
    }
}
