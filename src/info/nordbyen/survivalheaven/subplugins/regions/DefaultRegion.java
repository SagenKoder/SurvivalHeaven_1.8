package info.nordbyen.survivalheaven.subplugins.regions;

import org.bukkit.*;

public class DefaultRegion extends RegionData
{
    DefaultRegion() {
        super(null, "Unknown", 0.0, 0, true, true, true, false, false, false);
    }
    
    @Override
    public Location getCenter() {
        return null;
    }
    
    @Override
    public String getName() {
        return "Unknown";
    }
    
    @Override
    public double getRadius() {
        return Double.MAX_VALUE;
    }
    
    @Override
    public int getZValue() {
        return Integer.MIN_VALUE;
    }
    
    @Override
    public boolean isBreakable() {
        return true;
    }
    
    @Override
    public boolean isMonsters() {
        return true;
    }
    
    @Override
    public boolean isPvp() {
        return true;
    }
    
    @Override
    public boolean containsLocation(final Location loc) {
        return true;
    }
}
