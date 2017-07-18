package info.nordbyen.survivalheaven.subplugins.warps;

import info.nordbyen.survivalheaven.subplugins.homes.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import java.sql.*;
import org.bukkit.*;
import java.util.*;

public class WarpHandler
{
    public HashSet<Warp> warps;
    
    public WarpHandler() {
        this.warps = new HashSet<Warp>();
        try {
            final HomeManagerCommand hcm = new HomeManagerCommand();
            SH.getPlugin().getCommand("sethome").setExecutor((CommandExecutor)hcm);
            SH.getPlugin().getCommand("delhome").setExecutor((CommandExecutor)hcm);
            SH.getPlugin().getCommand("home").setExecutor((CommandExecutor)hcm);
            SH.getPlugin().getCommand("homes").setExecutor((CommandExecutor)hcm);
            SH.getPlugin().getCommand("homeradius").setExecutor((CommandExecutor)hcm);
            this.createTable();
            this.updateFromDatabase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTable() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("CREATE TABLE IF NOT EXISTS warp ( `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, `world` VARCHAR(45) NOT NULL, `x` INT(11) NOT NULL,`y` INT(11) NOT NULL,`z` INT(11) NOT NULL );");
    }
    
    public void updateFromDatabase() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        final ResultSet rs = sql.query("SELECT name, world, x, y, z FROM warp");
        while (rs != null && rs.next()) {
            final String name = rs.getString("name");
            final World w = Bukkit.getWorld(rs.getString("world"));
            final int x = rs.getInt("x");
            final int y = rs.getInt("y");
            final int z = rs.getInt("z");
            final Location loc = new Location(w, (double)x, (double)y, (double)z);
            final Warp warp = new Warp(name, loc);
            if (!this.warps.contains(this.getWarp(name))) {
                this.warps.add(warp);
            }
        }
    }
    
    public Warp getWarp(final String name) {
        for (final Warp w : this.warps) {
            if (w.getName().equalsIgnoreCase(name)) {
                return w;
            }
        }
        return null;
    }
}
