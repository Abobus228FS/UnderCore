package under.verse.core.block;

import under.verse.core.Material;
import under.verse.core.UnderCore;
import under.verse.core.network.packet.play.ClientboundBlockChangePacket;

public class BlockState {

    private final Block block;
    
    public BlockState(Block block) {
        this.block = block;
    }
    public Block getBlock() { return block; }
    public int getId() { return block.getType().getId() + block.getStateIdOffset(); }
    public boolean isWaterlogged() {
        if (!(block instanceof Waterloggable bl)) throw new IllegalStateException("Блок не может быть заполнен водой");
        return bl.isWaterlogged();
    }
    public void setWaterlogged(boolean val) {
        if (!(block instanceof Waterloggable bl)) throw new IllegalStateException("Блок не может быть заполнен водой");
        bl.setWaterlogged(val);
    }
    
    public Half getHalf() {
        if (!(block instanceof Bisected bl)) throw new IllegalStateException("Блок не реализует Bisected");
        return bl.getHalf();
    }
    public void setHalf(Half half) {
        if (!(block instanceof Bisected bl)) throw new IllegalStateException("Блок не реализует Bisected");
        bl.setHalf(half);
    }
}
