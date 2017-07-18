package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;
import org.bukkit.*;

public class PlayerDataSuper implements IPlayerData
{
    private String uuid;
    private String name;
    
    public PlayerDataSuper(final String uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
    }
    
    @Override
    public void addBadge(final int badge) {
    }
    
    @Override
    public void setIp(final String ip) {
    }
    
    @Override
    public ArrayList<Integer> getBadges() {
        return null;
    }
    
    @Override
    public String getBadgesAsString() {
        return null;
    }
    
    @Override
    public Date getFirstlogin() {
        return null;
    }
    
    @Override
    public int getGamemode() {
        return 0;
    }
    
    @Override
    public int getId() {
        return 0;
    }
    
    @Override
    public String getIp() {
        return null;
    }
    
    @Override
    public Location getLastlocation() {
        return null;
    }
    
    @Override
    public Date getLastlogin() {
        return null;
    }
    
    @Override
    public int getLevel() {
        return 0;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getRank() {
        return 0;
    }
    
    @Override
    public long getTimeplayed() {
        return 0L;
    }
    
    @Override
    public String getUUID() {
        return this.uuid;
    }
    
    @Override
    public void removeBadge(final int badge) {
    }
    
    @Override
    public void setBadges(final ArrayList<Integer> badges) {
    }
    
    @Override
    public void setGamemode(final int gamemode) {
    }
    
    @Override
    public void setLastlocation(final Location lastlocation) {
    }
    
    @Override
    public void setLastlogin(final Date lastlogin) {
    }
    
    @Override
    public void setLevel(final int level) {
    }
    
    @Override
    public void setName(final String name) {
    }
    
    @Override
    public void setRank(final int rank) {
    }
    
    @Override
    public void setTimeplayed(final long timeplayed) {
    }
}
