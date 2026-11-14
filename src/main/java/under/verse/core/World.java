package under.verse.core;

import under.verse.core.block.*;
import under.verse.core.entity.Player;
import under.verse.core.world.Chunk;
import under.verse.core.Location;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    
    public final String name;
    public static final World w = new World("world");
    private final ConcurrentHashMap<XZ, Chunk> chunkMap = new ConcurrentHashMap<>();
    
    static {
        for (int x = -5; x <= 5; x++)
          for (int z = -5; z <= 5; z++)
            w.createChunk(x, z);
    }
    
    private World(String name) {
        this.name = name;
    }
    public Block getBlockAt(int x, int y, int z) {
        if (y > 256 || y < 0) return null;
        int chunkX = Math.floorDiv(x, 16);
        int chunkZ = Math.floorDiv(z, 16);
        XZ xz = new XZ(chunkX, chunkZ);
        Chunk chunk = chunkMap.get(xz);
        if (chunk == null) chunk = new Chunk(this, chunkX, chunkZ);
        int localX = Math.floorMod(x, 16);
        int localZ = Math.floorMod(z, 16);
        Block block = chunk.getBlocks()[localX][y][localZ];
        if (block.getLocation() == null) block.setLocation(new Location(x, y, z));
        return block;
    }
    public void setBlockAt(int x, int y, int z, Block block) {
        if (y > 256 || y < 0) return;
        int chunkX = Math.floorDiv(x, 16);
        int chunkZ = Math.floorDiv(z, 16);
        Chunk chunk = chunkMap.get(new XZ(chunkX, chunkZ));
        if (chunk == null) return;
        int localX = Math.floorMod(x, 16);
        int localZ = Math.floorMod(z, 16);
        chunk.getBlocks()[localX][y][localZ] = block;
    }
    public Block getBlockAt(Location location) {
        if (location == null) throw new IllegalArgumentException("Location не может быть null");
        return this.getBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }
    public void setBlockAt(Location location, Block block) {
        if (location == null) throw new IllegalArgumentException("Location не может быть null");
        this.setBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ(), block);
    }
    public boolean createChunk(int x, int z) {
        if (chunkMap.get(new XZ(x, z)) != null) return false;
        chunkMap.put(new XZ(x, z), new Chunk(this, x, z));
        return true;
    }
    public void setChunk(int x, int z, Chunk chunk) {
        chunkMap.put(new XZ(x, z), chunk);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    public void placeNaturally(Player player, Material type, Location location, BlockFace face, ClickLocation loc) {
        Block.of(type).place(player, location, face, loc);
    }
    public Chunk getChunk(int x, int z) { return chunkMap.get(new XZ(x, z)); }
    private static record XZ(int x, int z) {}
}
