package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.regions.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;

public class RegionData implements IRegionData
{
    protected Location center;
    protected final String name;
    protected double radius;
    protected final int zVal;
    protected boolean pvp;
    protected boolean breakable;
    protected boolean monsters;
    protected boolean invincible;
    protected boolean bp;
    protected boolean auto_door;
    
    public static RegionData createRegion(final Location center, final String name, final double radius, final int zVal, final boolean pvp, final boolean breakable, final boolean monsters, final boolean invincible, final boolean bp, final boolean auto_door) {
        final RegionData region = new RegionData(center, name, radius, zVal, pvp, breakable, monsters, invincible, bp, auto_door);
        SH.getManager().getRegionManager().addRegion(region);
        return region;
    }
    
    protected RegionData(final Location center, final String name, final double radius, final int zVal, final boolean pvp, final boolean breakable, final boolean monsters, final boolean invincible, final boolean bp, final boolean auto_door) {
        this.setCenter(center);
        this.name = name;
        this.setRadius(radius);
        this.zVal = zVal;
        this.setPvp(pvp);
        this.setBreakable(breakable);
        this.setMonsters(monsters);
        this.setInvincible(invincible);
        this.setBp(bp);
        this.setAuto_door(auto_door);
    }
    
    @Override
    public boolean containsLocation(final Location loc) {
        final Location copy = loc.clone();
        copy.setY(0.0);
        return this.center != null && loc.getWorld() != null && this.center.getWorld() != null && loc.getWorld().getName().equals(this.center.getWorld().getName()) && copy.distance(this.center) <= this.radius;
    }
    
    @Override
    public Location getCenter() {
        return this.center;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public double getRadius() {
        return this.radius;
    }
    
    @Override
    public int getZValue() {
        return this.zVal;
    }
    
    @Override
    public boolean isBreakable() {
        return this.breakable;
    }
    
    @Override
    public boolean isInvincible() {
        return this.invincible;
    }
    
    @Override
    public boolean isMonsters() {
        return this.monsters;
    }
    
    @Override
    public boolean isPvp() {
        return this.pvp;
    }
    
    @Override
    public void setBreakable(final boolean breakable) {
        this.breakable = breakable;
    }
    
    @Override
    public void setCenter(final Location center) {
        if (center == null) {
            return;
        }
        center.setY(0.0);
        this.center = center;
    }
    
    @Override
    public void setInvincible(final boolean invincible) {
        this.invincible = invincible;
    }
    
    @Override
    public void setMonsters(final boolean monsters) {
        this.monsters = monsters;
    }
    
    @Override
    public void setPvp(final boolean pvp) {
        this.pvp = pvp;
    }
    
    @Override
    public void setRadius(final double radius) {
        this.radius = radius;
    }
    
    public boolean isBp() {
        return this.bp;
    }
    
    public void setBp(final boolean bp) {
        this.bp = bp;
    }
    
    public boolean isAuto_door() {
        return this.auto_door;
    }
    
    public void setAuto_door(final boolean auto_door) {
        this.auto_door = auto_door;
    }
}
