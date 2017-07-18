package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.subplugins.blockdata.*;
import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.blockdata.*;
import info.nordbyen.survivalheaven.subplugins.playerdata.*;

public class BlockManager implements IBlockManager
{
    @Override
    public IPlayerData getBlockOwner(final Block b) {
        final BlockProtection bp = BlockProtection.getInstance();
        if (bp == null) {
            return null;
        }
        return bp.getWhoPlaced(b);
    }
    
    @Override
    public void setBlockOwner(final Block b, final Player owner, final BlockPlacedType type) {
        final BlockProtection bp = BlockProtection.getInstance();
        if (bp == null) {
            return;
        }
        bp.setWhoPlaced(owner.getUniqueId().toString(), owner.getName(), b, type);
    }
    
    @Override
    public void setBlockOwner(final Block b, final PlayerData owner, final BlockPlacedType type) {
        final BlockProtection bp = BlockProtection.getInstance();
        if (bp == null) {
            return;
        }
        bp.setWhoPlaced(owner, b, type);
    }
    
    @Override
    public void setBlockOwner(final Block b, final String uuid, final String name, final BlockPlacedType type) {
        final BlockProtection bp = BlockProtection.getInstance();
        if (bp == null) {
            return;
        }
        bp.setWhoPlaced(uuid, name, b, type);
    }
}
