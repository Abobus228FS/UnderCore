package under.verse.core.world;

import java.util.HashMap;
import under.verse.core.block.Block;
import under.verse.core.Material;
import under.verse.core.Location;
import under.verse.core.World;
import java.util.concurrent.ThreadLocalRandom;

public class Chunk {
//    public static final Chunk test = new Chunk();
    private Block[][][] blocks = new Block[16][256][16];
    
    public Chunk(World world, int chX, int chY) {try{
        WorldReader.readChunk("world", this);
        world.setChunk(chX, chY, this);
        }catch(Exception e) { throw new RuntimeException(e);}
    }
    public Block[][][] getBlocks() {
        return blocks;
    }
}
