package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public interface IWandManager
{
    void add(final Wand p0);
    
    boolean createWand(final ItemStack p0, final Wand p1, final Player p2);
    
    Wand get(final String p0);
    
    boolean isWand(final ItemStack p0);
    
    Wand search(final ItemStack p0);
}
