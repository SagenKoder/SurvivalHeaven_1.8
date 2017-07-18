package info.nordbyen.survivalheaven.subplugins.homes;

import info.nordbyen.survivalheaven.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import java.sql.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.subplugins.playerdata.*;
import java.util.*;

public class HomeManager
{
    public HashMap<String, ArrayList<Home>> homesOfPlayer;
    
    public HomeManager() {
        this.homesOfPlayer = new HashMap<String, ArrayList<Home>>();
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
        sql.query("CREATE TABLE IF NOT EXISTS home ( `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, `uuid` VARCHAR(45) NOT NULL, `world` VARCHAR(45) NOT NULL, `x` INT(11) NOT NULL,`y` INT(11) NOT NULL,`z` INT(11) NOT NULL );");
    }
    
    public void updateFromDatabase() throws SQLException {
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        final ResultSet rs = sql.query("SELECT name, uuid, world, x, y, z FROM home;");
        while (rs != null && rs.next()) {
            final String name = rs.getString("name");
            final String uuid = rs.getString("uuid");
            final World w = Bukkit.getWorld(rs.getString("world"));
            final int x = rs.getInt("x");
            final int y = rs.getInt("y");
            final int z = rs.getInt("z");
            final Location loc = new Location(w, (double)x, (double)y, (double)z);
            final Home home = new Home(name, uuid, loc);
            if (this.homesOfPlayer.get(uuid) == null) {
                this.homesOfPlayer.put(uuid, new ArrayList<Home>());
            }
            this.homesOfPlayer.get(uuid).add(home);
        }
    }
    
    public boolean addHome(final Home home) throws SQLException {
        final String uuid = home.getUuid();
        if (this.getHomeFromPlayer(home.getName(), uuid) != null) {
            return false;
        }
        if (this.homesOfPlayer.get(uuid) == null) {
            this.homesOfPlayer.put(uuid, new ArrayList<Home>());
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("INSERT INTO home ( name, uuid, world, x, y, z ) VALUES ( '" + home.getName() + "', '" + uuid + "', '" + home.getLocation().getWorld().getName() + "', " + home.getLocation().getBlockX() + ", " + home.getLocation().getBlockY() + ", " + home.getLocation().getBlockZ() + " )");
        this.homesOfPlayer.get(uuid).add(home);
        return true;
    }
    
    public void deleteHomeFromPlayer(final String home, final String uuid) throws SQLException {
        Home toBeDel = null;
        final ArrayList<Home> homes = this.homesOfPlayer.get(uuid);
        for (final Home h : homes) {
            if (h.getName().equalsIgnoreCase(home)) {
                toBeDel = h;
            }
        }
        if (toBeDel == null) {
            return;
        }
        final IMysqlManager sql = SH.getManager().getMysqlManager();
        sql.query("DELETE FROM home WHERE name = '" + toBeDel.getName() + "' AND uuid = '" + toBeDel.getUuid() + "';");
        homes.remove(toBeDel);
    }
    
    public Home getHomeFromPlayer(final String home, final PlayerData pd) {
        return this.getHomeFromPlayer(home, pd.getUUID());
    }
    
    public Home getHomeFromPlayer(final String home, final String uuid) {
        if (this.getHomesFromPlayer(uuid) == null) {
            this.homesOfPlayer.put(uuid, new ArrayList<Home>());
        }
        final ArrayList<Home> homes = this.getHomesFromPlayer(uuid);
        for (final Home h : homes) {
            if (h.getName().equalsIgnoreCase(home)) {
                return h;
            }
        }
        return null;
    }
    
    public ArrayList<Home> getHomesFromPlayer(final PlayerData pd) {
        return (ArrayList<Home>)this.getHomesFromPlayer(pd.getUUID()).clone();
    }
    
    public ArrayList<Home> getHomesFromPlayer(final String uuid) {
        return this.homesOfPlayer.get(uuid);
    }
    
    public ArrayList<Home> getHomes() {
        final ArrayList<Home> homes = new ArrayList<Home>();
        for (final Map.Entry<String, ArrayList<Home>> e : this.homesOfPlayer.entrySet()) {
            homes.addAll(e.getValue());
        }
        return homes;
    }
}
