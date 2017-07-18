package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import java.sql.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import java.util.*;
import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;

public class BlockLagManager
{
    ArrayList<String> members;
    ArrayList<String> owners;
    private static BlockLagManager instance;
    
    public BlockLagManager() {
        this.members = new ArrayList<String>();
        this.owners = new ArrayList<String>();
    }
    
    public static BlockLagManager getInstance() {
        if (BlockLagManager.instance == null) {
            BlockLagManager.instance = new BlockLagManager();
        }
        return BlockLagManager.instance;
    }
    
    private void setupTables() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("CREATE TABLE IF NOT EXISTS blocklag_members (`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`uuid` VARCHAR(45) NOT NULL);");
        sql.query("CREATE TABLE IF NOT EXISTS blocklag_owners (`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`uuid` VARCHAR(45) NOT NULL);");
    }
    
    private void loadFromTables() throws SQLException {
        this.members = new ArrayList<String>();
        this.owners = new ArrayList<String>();
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        final ResultSet resultSetMembers = sql.query("SELECT * FROM blocklag_members;");
        while (resultSetMembers != null && resultSetMembers.next()) {
            this.members.add(resultSetMembers.getString("uuid"));
        }
        final ResultSet resultSetOwners = sql.query("SELECT * FROM blocklag_owners;");
        while (resultSetOwners != null && resultSetOwners.next()) {
            this.owners.add(resultSetOwners.getString("uuid"));
        }
    }
    
    public void addMember(final String uuid) throws SQLException {
        if (this.members.contains(uuid)) {
            return;
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("INSERT INTO blocklag_members ( uuid ) VALUES ( '" + uuid + "' );");
        this.members.add(uuid);
    }
    
    public void addOwner(final String uuid) throws SQLException {
        if (this.owners.contains(uuid)) {
            return;
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("INSERT INTO blocklag_owners ( uuid ) VALUES ( '" + uuid + "' );");
        this.owners.add(uuid);
    }
    
    public void removeMember(final String uuid) throws SQLException {
        if (!this.members.contains(uuid)) {
            return;
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("DELETE FROM blocklag_members WHERE uuid='" + uuid + "';");
        this.members.remove(uuid);
    }
    
    public void removeOwner(final String uuid) throws SQLException {
        if (!this.owners.contains(uuid)) {
            return;
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("DELETE FROM blocklag_owners WHERE uuid='" + uuid + "';");
        this.owners.remove(uuid);
    }
    
    public boolean canBreak(final Player p, final Block b) {
        if (SH.getManager().getRankManager().getRank(p.getUniqueId().toString()).getId() >= RankType.MODERATOR.getId()) {
            SH.debug("p=" + p, "b=" + b, "er h\u00f8y nok rank");
            return true;
        }
        if (this.isMember(p.getUniqueId().toString())) {
            SH.debug("p=" + p, "b=" + b, "medlem i byen");
            return true;
        }
        SH.debug("p=" + p, "b=" + b, "kan ikke \u00f8delegge");
        return true;
    }
    
    public boolean useBlockProtection(final Player p, final Block b) {
        return b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST || true;
    }
    
    public boolean isMember(final String uuid) {
        SH.debug("uuid=" + uuid, "members=" + this.members, "owners=" + this.owners);
        return this.listContainsString(this.owners, uuid) || this.listContainsString(this.members, uuid);
    }
    
    public boolean isOwner(final String uuid) {
        return this.listContainsString(this.owners, uuid);
    }
    
    public boolean listContainsString(final List<String> list, final String string) {
        for (final String s : list) {
            if (s.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }
    
    public static class BlockLagPlugin extends SubPlugin
    {
        public BlockLagPlugin(final String name) {
            super(name);
        }
        
        @Override
        protected void disable() {
        }
        
        @Override
        protected void enable() {
            try {
                BlockLagManager.getInstance().setupTables();
                BlockLagManager.getInstance().loadFromTables();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            Bukkit.getPluginManager().registerEvents((Listener)new BlockLagListener(), (Plugin)SH.getPlugin());
        }
    }
    
    public static class BlockLagListener implements Listener
    {
    }
}
