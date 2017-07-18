package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.*;
import java.util.*;

public class PlayerData implements IPlayerData
{
    private final int id;
    private String ip;
    private String name;
    private final String uuid;
    private final Date firstlogin;
    private Date lastlogin;
    private long timeplayed;
    private int rank;
    private final ArrayList<Integer> badges;
    private int gamemode;
    private Location lastlocation;
    private int level;
    
    public PlayerData(final int id, final String name, final String ip, final String uuid, final Date firstlogin, final Date lastlogin, final long timeplayed, final int rank, final ArrayList<Integer> badges, final Location lastlocation, final int level, final int gamemode) {
        this.badges = new ArrayList<Integer>();
        this.setGamemode(gamemode);
        this.id = id;
        this.setName(name);
        this.uuid = uuid;
        this.firstlogin = firstlogin;
        this.setLastlogin(lastlogin);
        this.setTimeplayed(timeplayed);
        this.setRank(rank);
        this.setLastlocation(lastlocation);
        this.setLevel(level);
        this.ip = ip;
        this.setBadges(badges);
    }
    
    @Override
    public void addBadge(final int badge) {
        if (!this.badges.contains(badge)) {
            this.badges.add(badge);
        }
    }
    
    @Override
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Override
    public ArrayList<Integer> getBadges() {
        return this.badges;
    }
    
    @Override
    public String getBadgesAsString() {
        String list = "";
        for (final int badge : this.badges) {
            list = String.valueOf(list) + badge + ",";
        }
        return list;
    }
    
    @Override
    public Date getFirstlogin() {
        return this.firstlogin;
    }
    
    @Override
    public int getGamemode() {
        return this.gamemode;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getIp() {
        return this.ip;
    }
    
    @Override
    public Location getLastlocation() {
        return this.lastlocation;
    }
    
    @Override
    public Date getLastlogin() {
        return this.lastlogin;
    }
    
    @Override
    public int getLevel() {
        return this.level;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getRank() {
        return this.rank;
    }
    
    @Override
    public long getTimeplayed() {
        return this.timeplayed;
    }
    
    @Override
    public String getUUID() {
        return this.uuid;
    }
    
    @Override
    public void removeBadge(final int badge) {
        this.badges.remove(badge);
    }
    
    @Override
    public void setBadges(final ArrayList<Integer> badges) {
        for (final int badge : badges) {
            this.addBadge(badge);
        }
    }
    
    @Override
    public void setGamemode(final int gamemode) {
        this.gamemode = gamemode;
    }
    
    @Override
    public void setLastlocation(final Location lastlocation) {
        this.lastlocation = lastlocation;
    }
    
    @Override
    public void setLastlogin(final Date lastlogin) {
        this.lastlogin = lastlogin;
    }
    
    @Override
    public void setLevel(final int level) {
        this.level = level;
    }
    
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public void setRank(final int rank) {
        this.rank = rank;
    }
    
    @Override
    public void setTimeplayed(final long timeplayed) {
        this.timeplayed = timeplayed;
    }
}
