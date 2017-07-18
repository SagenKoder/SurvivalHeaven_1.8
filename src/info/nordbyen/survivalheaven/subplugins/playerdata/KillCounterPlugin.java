package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.*;
import java.sql.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class KillCounterPlugin extends SubPlugin
{
    public KillCounterPlugin() {
        super("KillCounter");
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        try {
            sql.query("CREATE TABLE IF NOT EXISTS kills( `id` SMALLINT NOT NULL AUTO_INCREMENT, `killer` VARCHAR(30) NOT NULL, `killed` VARCHAR(30) NOT NULL, `x` MEDIUMINT, `y` MEDIUMINT, `z` MEDIUMINT, `world` VARCHAR(20), `time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, PRIMARY KEY(id)); ");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addKill(final String killer, final String killed, final String world, final int x, final int y, final int z) {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        try {
            sql.query("INSERT INTO kills(killer, killed, x, y, z, world, time) VALUES (\"" + killer + "\",\"" + killed + "\"," + x + "," + y + "," + z + ",\"" + world + "\", CURRENT_TIMESTAMP);");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public class KilListener implements Listener
    {
        @EventHandler
        public void onKill(final EntityDeathEvent e) {
            if (e.getEntity() instanceof Player && e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
                final String killer = e.getEntity().getKiller().getUniqueId().toString();
                final String killed = ((Player)e.getEntity()).getUniqueId().toString();
                final Location l = e.getEntity().getLocation();
                final String world = l.getWorld().getName();
                final int x = l.getBlockX();
                final int y = l.getBlockY();
                final int z = l.getBlockZ();
                if (world.equalsIgnoreCase("pvp2")) {
                    KillCounterPlugin.this.addKill(killer, killed, world, x, y, z);
                }
            }
        }
    }
}
