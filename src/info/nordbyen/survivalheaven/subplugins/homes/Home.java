package info.nordbyen.survivalheaven.subplugins.homes;

import org.bukkit.*;

public class Home
{
    private final String name;
    private final String uuid;
    private final Location location;
    
    public Home(final String name, final String uuid, final Location location) {
        this.name = name;
        this.uuid = uuid;
        this.location = location;
    }
    
    public Location getLocation() {
        return this.location.clone();
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUuid() {
        return this.uuid;
    }
}
