package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.block.*;

public final class WandSelection
{
    private Block block1;
    private Block block2;
    
    public WandSelection() {
        this.block1 = null;
        this.block2 = null;
    }
    
    public WandSelection(final Block block1, final Block block2) {
        this.block1 = null;
        this.block2 = null;
        this.setBlock1(block1);
        this.setBlock2(block2);
    }
    
    public Block getBlock1() {
        return this.block1;
    }
    
    public Block getBlock2() {
        return this.block2;
    }
    
    public void setBlock1(final Block block1) {
        this.block1 = block1;
    }
    
    public void setBlock2(final Block block2) {
        this.block2 = block2;
    }
}
