package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.warning.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.java.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.subplugin.annotations.*;
import java.sql.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;

@SurvivalHeavenSubPlugin(name = "WarningManager")
public class WarningManager implements IWarningManager
{
    private final List<IWarning> warnings;
    
    public WarningManager() {
        this.warnings = new ArrayList<IWarning>();
    }
    
    private static void createTable() throws SQLException {
        SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `warnings` (`id` INT(11) NOT NULL AUTO_INCREMENT, `playeruuid` VARCHAR(255) NOT NULL, `setteruuid` VARCHAR(255) NOT NULL, `message` VARCHAR(255) NOT NULL, `date` VARCHAR(255) NOT NULL, `level` INT(11) NOT NULL, PRIMARY KEY (`id`) );");
    }
    
    @SurvivalHeavenDisable
    private static void disable(final JavaPlugin plugin) {
    }
    
    @SurvivalHeavenEnable
    private static void enable(final JavaPlugin plugin) {
        try {
            createTable();
            loadFromMysql();
        }
        catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Noe galt skjedde under loading av mysql");
            e.printStackTrace();
        }
    }
    
    private static void loadFromMysql() throws SQLException {
        final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT * FROM warnings");
        while (rs.next()) {
            final int id = rs.getInt("id");
            final String puuid = rs.getString("playeruuid");
            final String suuid = rs.getString("setteruuid");
            final String message = rs.getString("message");
            final Date date = SH.getManager().getMysqlManager().getDate(rs.getString("date"));
            final Warning warning = new Warning(date, SH.getManager().getPlayerDataManager().getPlayerData(puuid), SH.getManager().getPlayerDataManager().getPlayerData(suuid), message, null);
        }
    }
    
    @Override
    public void addWarning(final Date date, final IPlayerData player, final IPlayerData setter, final String message) throws SQLException {
        this.addWarning(date, player, setter, message, IWarning.Level.LOW);
    }
    
    @Override
    public void addWarning(final Date date, final IPlayerData player, final IPlayerData setter, final String message, final IWarning.Level level) throws SQLException {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null!");
        }
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null!");
        }
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null!");
        }
        final IWarning warning = new Warning(date, player, setter, message, level);
        this.warnings.add(warning);
    }
    
    @Override
    public List<IWarning> getEveryWarnings() {
        return new ArrayList<IWarning>(this.warnings);
    }
    
    @Override
    public IWarning getWarningFromId(final int id) {
        for (final IWarning w : this.warnings) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }
    
    @Override
    public List<IWarning> getWarningsFromName(final String name) {
        final List<IWarning> pw = new ArrayList<IWarning>();
        for (final IWarning w : this.warnings) {
            if (w.getPlayer().getName().equals(name)) {
                pw.add(w);
            }
        }
        return pw;
    }
    
    @Override
    public List<IWarning> getWarningsFromPlayer(final IPlayerData pd) {
        final List<IWarning> pw = new ArrayList<IWarning>();
        for (final IWarning w : this.warnings) {
            if (w.getPlayer().getUUID().equals(pd.getUUID())) {
                pw.add(w);
            }
        }
        return pw;
    }
    
    @Override
    public List<IWarning> getWarningsFromUuid(final String uuid) {
        final List<IWarning> pw = new ArrayList<IWarning>();
        for (final IWarning w : this.warnings) {
            if (w.getPlayer().getUUID().equals(uuid)) {
                pw.add(w);
            }
        }
        return pw;
    }
    
    @Override
    public void removeWarning(final int id) {
        final List<IWarning> removed = new ArrayList<IWarning>();
        for (final IWarning w : this.warnings) {
            if (w.getId() == id) {
                removed.add(w);
            }
        }
        for (final IWarning rem : removed) {
            this.removeWarning(rem);
        }
    }
    
    @Override
    public void removeWarning(final IWarning warning) {
        if (!this.warnings.contains(warning)) {
            return;
        }
        this.warnings.remove(warning);
    }
}
