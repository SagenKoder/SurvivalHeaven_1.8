package info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit;

import com.sk89q.worldedit.extent.logging.*;
import com.sk89q.worldedit.extension.platform.*;
import com.sk89q.worldedit.world.*;
import com.sk89q.worldedit.extent.*;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.*;
import org.bukkit.*;

public class MyLogger extends AbstractLoggingExtent
{
    private final Actor actor;
    private final World world;
    
    public MyLogger(final Actor actor, final Extent extent, final World world) {
        super(extent);
        this.actor = actor;
        this.world = world;
    }
    
    protected void onBlockChange(final Vector position, final BaseBlock newBlock) {
        if (!Material.getMaterial(newBlock.getType()).isSolid()) {
            return;
        }
    }
}
