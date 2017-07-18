package info.nordbyen.survivalheaven.api.regions;

import org.bukkit.*;

public interface IRegionData
{
    boolean containsLocation(final Location p0);
    
    Location getCenter();
    
    String getName();
    
    double getRadius();
    
    int getZValue();
    
    boolean isBreakable();
    
    boolean isInvincible();
    
    boolean isMonsters();
    
    boolean isPvp();
    
    void setBreakable(final boolean p0);
    
    void setCenter(final Location p0);
    
    void setInvincible(final boolean p0);
    
    void setMonsters(final boolean p0);
    
    void setPvp(final boolean p0);
    
    void setRadius(final double p0);
}
