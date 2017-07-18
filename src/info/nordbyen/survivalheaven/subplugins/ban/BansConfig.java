package info.nordbyen.survivalheaven.subplugins.ban;

import info.nordbyen.survivalheaven.api.config.*;
import java.util.*;
import java.io.*;

public class BansConfig extends CustomConfiguration
{
    private static BansConfig cfg;
    
    public static void createInstance() {
        if (BansConfig.cfg == null) {
            BansConfig.cfg = new BansConfig();
        }
    }
    
    public static List<Ban> getBans() {
        createInstance();
        final List<Ban> bans = new ArrayList<Ban>();
        for (final String uuid : BansConfig.cfg.getKeys(false)) {
            final String banner_uuid = BansConfig.cfg.getString(String.valueOf(uuid) + ".banner_uuid");
            final String reason = BansConfig.cfg.getString(String.valueOf(uuid) + ".reason");
            final Date from = new Date(BansConfig.cfg.getLong(String.valueOf(uuid) + ".from"));
            final Date to = new Date(BansConfig.cfg.getLong(String.valueOf(uuid) + ".to"));
            final Ban ban = new Ban(uuid, banner_uuid, reason, from, to);
            bans.add(ban);
        }
        return bans;
    }
    
    public static void addBan(final Ban ban) {
        createInstance();
        BansConfig.cfg.set(String.valueOf(ban.getUUID()) + ".banner_uuid", (Object)ban.getGiverUUID());
        BansConfig.cfg.set(String.valueOf(ban.getUUID()) + ".reason", (Object)ban.getReason());
        BansConfig.cfg.set(String.valueOf(ban.getUUID()) + ".from", (Object)ban.getFrom().getTime());
        BansConfig.cfg.set(String.valueOf(ban.getUUID()) + ".to", (Object)ban.getTo().getTime());
        BansConfig.cfg.save();
    }
    
    public static void removeBan(final Ban ban) {
        createInstance();
        BansConfig.cfg.set(ban.getUUID(), (Object)null);
        BansConfig.cfg.save();
    }
    
    public BansConfig() {
        super(new File("./plugins/SurvivalHeaven/bans.yml"));
        (BansConfig.cfg = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        this.save();
    }
}
