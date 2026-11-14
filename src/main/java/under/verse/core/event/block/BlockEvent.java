package under.verse.core.event.block;

import under.verse.core.event.Event;
import under.verse.core.block.Block;
import under.verse.core.UnderCore;
import under.verse.core.event.Cancellable;

public abstract class BlockEvent extends Event {
    private final Block block;
    
    public BlockEvent(Block block) {
        this.block = block;
    }
    
    public Block getBlock() {
        return block;
    }
}
