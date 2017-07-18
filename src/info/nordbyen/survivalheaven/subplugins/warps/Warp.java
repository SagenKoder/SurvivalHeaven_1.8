package info.nordbyen.survivalheaven.subplugins.warps;

import org.bukkit.*;

public class Warp
{
    private final String name;
    private final Location location;
    
    public Warp(final String name, final Location location) {
        this.name = name;
        this.location = location;
    }
    
    public Location getLocation() {
        return this.location.clone();
    }
    
    public String getName() {
        return this.name;
    }
}
