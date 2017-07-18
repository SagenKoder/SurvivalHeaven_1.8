package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;

public interface Wand
{
    boolean canCreate(final ItemStack p0, final Player p1);
    
    String getName();
    
    void onLeftClick(final ItemStack p0, final Player p1, final Block p2, final BlockFace p3);
    
    void onRightClick(final ItemStack p0, final Player p1, final Block p2, final BlockFace p3);
}
