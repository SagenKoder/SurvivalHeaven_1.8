package info.nordbyen.survivalheaven.subplugins.blockdata;

import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.blockdata.*;
import info.nordbyen.survivalheaven.subplugins.playerdata.*;

public interface IBlockManager
{
    IPlayerData getBlockOwner(final Block p0);
    
    void setBlockOwner(final Block p0, final Player p1, final BlockPlacedType p2);
    
    void setBlockOwner(final Block p0, final PlayerData p1, final BlockPlacedType p2);
    
    void setBlockOwner(final Block p0, final String p1, final String p2, final BlockPlacedType p3);
}
