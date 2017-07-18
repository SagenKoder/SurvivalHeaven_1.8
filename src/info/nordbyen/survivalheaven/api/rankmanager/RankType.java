package info.nordbyen.survivalheaven.api.rankmanager;

import org.bukkit.*;
import info.nordbyen.survivalheaven.*;

public enum RankType
{
    BANNED("BANNED", 0, 0, "BANNED", "", ChatColor.GRAY), 
    BRUKER("BRUKER", 1, 1, "BRUKER", "", ChatColor.RESET), 
    DIAMANT("DIAMANT", 2, 2, "DIAMANT", "[Diamant] ", ChatColor.AQUA), 
    SMARAGD("SMARAGD", 3, 3, "SMARAGD", "[Smaragd] ", ChatColor.GREEN), 
    ARKITEKT("ARKITEKT", 4, 4, "ARKITEKT", "[Arkitekt] ", ChatColor.YELLOW), 
    UTVIKLER("UTVIKLER", 5, 5, "UTVIKLER", "[Utvikler] ", ChatColor.LIGHT_PURPLE), 
    MODERATOR("MODERATOR", 6, 6, "MODERATOR", "[Mod] ", ChatColor.BLUE), 
    ADMINISTRATOR("ADMINISTRATOR", 7, 7, "ADMINISTRATOR", "[Admin] ", ChatColor.GOLD), 
    SERVERLEDER("SERVERLEDER", 8, 8, "LEDER", "", ChatColor.DARK_PURPLE);
    
    private final int rank;
    private final String name;
    private final String prefix;
    private final ChatColor color;
    
    public static RankType getRankFromId(final int id) {
        if (id == 0) {
            return RankType.BANNED;
        }
        if (id == 1) {
            return RankType.BRUKER;
        }
        if (id == 2) {
            return RankType.DIAMANT;
        }
        if (id == 3) {
            return RankType.SMARAGD;
        }
        if (id == 4) {
            return RankType.ARKITEKT;
        }
        if (id == 5) {
            return RankType.UTVIKLER;
        }
        if (id == 6) {
            return RankType.MODERATOR;
        }
        if (id == 7) {
            return RankType.ADMINISTRATOR;
        }
        if (id == 8) {
            return RankType.SERVERLEDER;
        }
        SH.debug("Fant ikke rank med id: " + id);
        return RankType.BRUKER;
    }
    
    public static RankType getRankFromName(final String name) {
        if (name.equalsIgnoreCase("banned") || name.equalsIgnoreCase("ban")) {
            return RankType.BANNED;
        }
        if (name.equalsIgnoreCase("bruker")) {
            return RankType.BRUKER;
        }
        if (name.equalsIgnoreCase("diamant")) {
            return RankType.DIAMANT;
        }
        if (name.equalsIgnoreCase("smaragd")) {
            return RankType.SMARAGD;
        }
        if (name.equalsIgnoreCase("utvikler")) {
            return RankType.UTVIKLER;
        }
        if (name.equalsIgnoreCase("arkitekt") || name.equalsIgnoreCase("ark")) {
            return RankType.ARKITEKT;
        }
        if (name.equalsIgnoreCase("mod") || name.equalsIgnoreCase("moderator")) {
            return RankType.MODERATOR;
        }
        if (name.equalsIgnoreCase("admin") || name.equalsIgnoreCase("administrator")) {
            return RankType.ADMINISTRATOR;
        }
        if (name.equalsIgnoreCase("leder")) {
            return RankType.SERVERLEDER;
        }
        return null;
    }
    
    private RankType(final String s, final int n, final int rank, final String name, final String prefix, final ChatColor color) {
        this.rank = rank;
        this.name = name;
        this.prefix = prefix;
        this.color = color;
    }
    
    public String getColor() {
        return this.color + this.prefix;
    }
    
    public int getId() {
        return this.rank;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPrefix() {
        return new StringBuilder().append(this.color).toString();
    }
}
