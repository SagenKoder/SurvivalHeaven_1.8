package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.*;
import java.util.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.blockdata.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import com.sk89q.worldedit.*;
import info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit.*;
import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.sql.*;
import info.nordbyen.survivalheaven.subplugins.playerdata.*;

public final class BlockProtection extends SubPlugin
{
    private static BlockProtection instance;
    private final ArrayList<String> registeredWorlds;
    
    static BlockProtection getInstance() {
        return BlockProtection.instance;
    }
    
    public BlockProtection(final String name) {
        super(name);
        this.registeredWorlds = new ArrayList<String>();
        BlockProtection.instance = this;
    }
    
    private void createTables() {
        for (final World world : Bukkit.getWorlds()) {
            this.createWorldTable(world.getName());
        }
    }
    
    private void createWorldTable(final String world) {
        if (this.registeredWorlds.contains(world)) {
            return;
        }
        try {
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.SURVIVAL.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ")ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.CREATIVE.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ") " + "ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.WORLDEDIT.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ") " + "ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            this.registeredWorlds.add(world);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        this.createTables();
        Bukkit.getPluginManager().registerEvents((Listener)new BlockProtectionListener(), (Plugin)SH.getPlugin());
        WorldEdit.getInstance().getEventBus().register((Object)new MyEventHandler());
    }
    
    IPlayerData getWhoPlaced(final Block b) {
        final String w = b.getWorld().getName();
        this.createWorldTable(w);
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("(SELECT uuid, time FROM `blocks_" + BlockPlacedType.SURVIVAL.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\") UNION (SELECT uuid, time FROM `blocks_" + BlockPlacedType.CREATIVE.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\") UNION (SELECT uuid, time FROM `blocks_" + BlockPlacedType.WORLDEDIT.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\")");
            String uuid = null;
            long time = -1L;
            while (rs.next()) {
                final String uuid_ = rs.getString("uuid");
                final Long time_ = rs.getLong("time");
                if (time_ > time) {
                    time = time_;
                    uuid = uuid_;
                }
            }
            if (time == -1L) {
                return null;
            }
            return SH.getManager().getPlayerDataManager().getPlayerData(uuid);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    void setWhoPlaced(final PlayerData p, final Block b, final BlockPlacedType type) {
        this.setWhoPlaced(p.getUUID(), p.getName(), b, type);
    }
    
    void setWhoPlaced(final String uuid, final String name, final Block b, final BlockPlacedType type) {
        try {
            SH.getManager().getMysqlManager().query("REPLACE INTO blocks_" + type.name + "_" + b.getWorld().getName() + " ( name, uuid, x, y, z, time ) " + "VALUES ( \"" + name + "\", \"" + uuid + "\", " + b.getX() + ", " + b.getY() + ", " + b.getZ() + ", " + System.currentTimeMillis() + " );");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void setWhoPlaced(final Block a, final Block b, final String uuid, final String name, final BlockPlacedType type) {
        if (!a.getWorld().getName().equals(b.getWorld().getName())) {
            return;
        }
        final int xMin = (int)Math.min(a.getLocation().getX(), b.getLocation().getX());
        final int yMin = (int)Math.min(a.getLocation().getY(), b.getLocation().getY());
        final int zMin = (int)Math.min(a.getLocation().getZ(), b.getLocation().getZ());
        final int xMax = (int)Math.max(a.getLocation().getX(), b.getLocation().getX());
        final int yMax = (int)Math.max(a.getLocation().getY(), b.getLocation().getY());
        final int zMax = (int)Math.max(a.getLocation().getZ(), b.getLocation().getZ());
        for (int x = xMin; x < xMax; ++x) {
            int y = yMin;
            while (x < yMax) {
                int z = zMin;
                while (x < zMax) {
                    try {
                        SH.getManager().getMysqlManager().query("REPLACE INTO blocks_" + type.name + "_" + b.getWorld().getName() + "(" + "name, uuid, x, y, z, time) " + "VALUES ( " + "'" + name + "', '" + uuid + "', " + x + ", " + y + ", " + z + ", " + System.currentTimeMillis() + " " + ");");
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ++z;
                }
                ++y;
            }
        }
    }
}
