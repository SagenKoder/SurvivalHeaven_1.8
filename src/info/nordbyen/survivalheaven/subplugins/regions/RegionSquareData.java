package info.nordbyen.survivalheaven.subplugins.regions;

import org.bukkit.*;
import info.nordbyen.survivalheaven.*;

public class RegionSquareData extends RegionData
{
    private int xMin;
    private int xMax;
    private int zMin;
    private int zMax;
    
    public static RegionData createRegion(final String name, final int zVal, final boolean pvp, final boolean breakable, final boolean monsters, final boolean invincible, final boolean bp, final boolean auto_door, final World world, final int x1, final int z1, final int x2, final int z2) {
        final RegionSquareData region = new RegionSquareData(new Location(world, (double)x1, 90.0, (double)z1), name, Math.max(Math.max(x1, x2) - Math.min(x1, x2), Math.max(z1, z2) - Math.min(z1, z2)), zVal, pvp, breakable, monsters, invincible, bp, auto_door);
        region.xMax = Math.max(x1, x2);
        region.xMin = Math.min(x1, x2);
        region.zMax = Math.max(z1, z2);
        region.zMin = Math.min(z1, z2);
        SH.getManager().getRegionManager().addRegion(region);
        return region;
    }
    
    protected RegionSquareData(final Location center, final String name, final double radius, final int zVal, final boolean pvp, final boolean breakable, final boolean monsters, final boolean invincible, final boolean bp, final boolean auto_door) {
        super(center, name, radius, zVal, pvp, breakable, monsters, invincible, bp, auto_door);
    }
    
    @Override
    public boolean containsLocation(final Location loc) {
        if (loc.getWorld() == null || this.center.getWorld() == null) {
            return false;
        }
        if (!loc.getWorld().getName().equals(this.center.getWorld().getName())) {
            return false;
        }
        final double locX = loc.getX();
        final double locZ = loc.getZ();
        return locX >= this.xMin && locX <= this.xMax && locZ >= this.zMin && locZ <= this.zMax;
    }
}
