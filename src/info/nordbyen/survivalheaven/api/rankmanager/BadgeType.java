package info.nordbyen.survivalheaven.api.rankmanager;

import org.bukkit.*;

public enum BadgeType
{
    BYGGER("BYGGER", 0, 1, "BYGGER", "+", ChatColor.YELLOW), 
    PENSJONIST("PENSJONIST", 1, 2, "PENSJONIST", "+", ChatColor.DARK_PURPLE);
    
    private final int badge;
    private final String name;
    private final String prefix;
    private final ChatColor color;
    
    public static BadgeType getBadgeFromId(final int id) {
        if (id == 1) {
            return BadgeType.BYGGER;
        }
        if (id == 2) {
            return BadgeType.PENSJONIST;
        }
        return null;
    }
    
    private BadgeType(final String s, final int n, final int badge, final String name, final String prefix, final ChatColor color) {
        this.badge = badge;
        this.name = name;
        this.prefix = prefix;
        this.color = color;
    }
    
    public ChatColor getColor() {
        return this.color;
    }
    
    public int getId() {
        return this.badge;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPrefix() {
        return this.color + this.prefix;
    }
}
