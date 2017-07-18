package info.nordbyen.survivalheaven.api.playerdata;

import java.util.*;
import org.bukkit.*;

public interface IPlayerData
{
    void addBadge(final int p0);
    
    void setIp(final String p0);
    
    ArrayList<Integer> getBadges();
    
    String getBadgesAsString();
    
    Date getFirstlogin();
    
    int getGamemode();
    
    int getId();
    
    String getIp();
    
    Location getLastlocation();
    
    Date getLastlogin();
    
    int getLevel();
    
    String getName();
    
    int getRank();
    
    long getTimeplayed();
    
    String getUUID();
    
    void removeBadge(final int p0);
    
    void setBadges(final ArrayList<Integer> p0);
    
    void setGamemode(final int p0);
    
    void setLastlocation(final Location p0);
    
    void setLastlogin(final Date p0);
    
    void setLevel(final int p0);
    
    void setName(final String p0);
    
    void setRank(final int p0);
    
    void setTimeplayed(final long p0);
}
