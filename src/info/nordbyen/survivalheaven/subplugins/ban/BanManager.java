package info.nordbyen.survivalheaven.subplugins.ban;

import java.util.*;

public class BanManager
{
    private HashMap<String, Ban> bans;
    
    public BanManager() {
        this.bans = new HashMap<String, Ban>();
        try {
            this.createConfig();
            this.updateFromConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createConfig() {
        BansConfig.createInstance();
    }
    
    private void updateFromConfig() {
        final HashMap<String, Ban> bans = new HashMap<String, Ban>();
        for (final Ban ban : BansConfig.getBans()) {
            bans.put(ban.getUUID(), ban);
        }
        this.bans = bans;
    }
    
    private void addBan(final Ban ban) {
        BansConfig.addBan(ban);
        this.bans.put(ban.getUUID(), ban);
    }
    
    private void removeBan(final Ban ban) {
        BansConfig.removeBan(ban);
        this.bans.remove(ban.getUUID());
    }
    
    public void ban(final String uuid, final String banner_uuid, final String reason, final Date to) {
        this.addBan(new Ban(uuid, banner_uuid, reason, new Date(), to));
    }
    
    public void unban(final String uuid) {
        final Ban ban = this.getBanFromPlayer(uuid);
        this.removeBan(ban);
    }
    
    public Ban getBanFromPlayer(final String uuid) {
        return this.bans.get(uuid);
    }
}
