package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import java.sql.*;
import org.bukkit.*;
import java.util.*;

public final class PlayerDataManager implements IPlayerDataManager
{
    private static HashMap<String, IPlayerData> playerdatalist;
    
    static {
        PlayerDataManager.playerdatalist = new HashMap<String, IPlayerData>();
    }
    
    public static ItemStack skull(final String skullOwner) {
        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)(byte)SkullType.PLAYER.ordinal());
        final SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setOwner(skullOwner);
        skull.setItemMeta((ItemMeta)skullMeta);
        return skull;
    }
    
    @Override
    public void createPlayerData(final Player p) {
        if (this.getPlayerData(p.getUniqueId().toString()) != null) {
            return;
        }
        SH.getManager().getMysqlManager().insert("players", new Object[] { "uuid", "name", "ips", "gamemode", "firstlogin", "lastlogin" }, new Object[] { p.getUniqueId().toString(), p.getName(), p.getAddress().toString().replace("/", "").split(":")[0], p.getGameMode().getValue(), SH.getManager().getMysqlManager().getDate(new Date()), SH.getManager().getMysqlManager().getDate(new Date()) });
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT * FROM `players` WHERE `uuid` = \"" + p.getUniqueId().toString() + "\"");
            if (rs.next()) {
                final int id = rs.getInt("id");
                final String ips = rs.getString("ips");
                final String name = rs.getString("name");
                final String uuid = rs.getString("uuid");
                final Date firstlogin = SH.getManager().getMysqlManager().getDate(rs.getString("firstlogin"));
                final Date lastlogin = SH.getManager().getMysqlManager().getDate(rs.getString("lastlogin"));
                final long timeplayed = rs.getLong("timeplayed");
                final int rank = rs.getInt("rank");
                final ArrayList<Integer> badges = new ArrayList<Integer>();
                final String[] ba = rs.getString("badges").split(",");
                String[] array;
                for (int length = (array = ba).length, i = 0; i < length; ++i) {
                    final String badge = array[i];
                    try {
                        badges.add(Integer.parseInt(badge));
                    }
                    catch (Exception ex) {}
                }
                final Location lastlocation = SH.getManager().getMysqlManager().getLocation(rs.getString("lastlocation"));
                final int level = rs.getInt("level");
                final int gamemode = rs.getInt("gamemode");
                final PlayerData data = new PlayerData(id, name, ips, uuid, firstlogin, lastlogin, timeplayed, rank, badges, lastlocation, level, gamemode);
                PlayerDataManager.playerdatalist.put(data.getUUID(), data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void createTable() {
        try {
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `players` (`id` SMALLINT NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255) NOT NULL, `name` VARCHAR(255) NOT NULL, `ips` VARCHAR(15) NOT NULL, `gamemode` INT(2) NOT NULL DEFAULT 0, `firstlogin` VARCHAR(255) NOT NULL, `lastlogin` VARCHAR(255) NOT NULL, `timeplayed` BIGINT(11) DEFAULT 0 NOT NULL, `bank` MEDIUMINT DEFAULT 300, `rank` TINYINT DEFAULT 1, `badges` VARCHAR(255) DEFAULT \"\", `level` SMALLINT DEFAULT 1, `language` VARCHAR(255) DEFAULT \"norsk\", `lastlocation` VARCHAR(255) DEFAULT \"NO\" NOT NULL, PRIMARY KEY (`id`) );");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public IPlayerData getPlayerData(final String uuid) {
        return PlayerDataManager.playerdatalist.get(uuid);
    }
    
    @Override
    public IPlayerData getPlayerDataFromName(final String name) {
        if (name == null) {
            return null;
        }
        for (final Map.Entry<String, IPlayerData> entry : PlayerDataManager.playerdatalist.entrySet()) {
            final IPlayerData data = entry.getValue();
            if (data.getName().equalsIgnoreCase(name)) {
                return data;
            }
        }
        return null;
    }
    
    @Override
    public List<IPlayerData> getAllPlayerData() {
        final List<IPlayerData> list = new ArrayList<IPlayerData>();
        for (final String name : PlayerDataManager.playerdatalist.keySet()) {
            list.add(PlayerDataManager.playerdatalist.get(name));
        }
        return list;
    }
    
    @Override
    public void saveDataToDatabase() {
        for (final Map.Entry<String, IPlayerData> entry : PlayerDataManager.playerdatalist.entrySet()) {
            final IPlayerData pd = entry.getValue();
            try {
                SH.getManager().getMysqlManager().query("UPDATE players SET `name` = \"" + pd.getName() + "\", " + "`ips` = \"" + pd.getIp() + "\", " + "`gamemode` = " + pd.getGamemode() + ", " + "`lastlogin` = \"" + SH.getManager().getMysqlManager().getDate(pd.getLastlogin()) + "\", " + "`timeplayed` = " + pd.getTimeplayed() + ", " + "`rank` = " + pd.getRank() + ", " + "`badges` = \"" + pd.getBadgesAsString() + "\", " + "`level` = " + pd.getLevel() + " WHERE `uuid` = \"" + pd.getUUID() + "\";");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void updateDataFromDatabase() {
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT * FROM `players`");
            while (rs.next()) {
                final int id = rs.getInt("id");
                final String ips = rs.getString("ips");
                final String name = rs.getString("name");
                final String uuid = rs.getString("uuid");
                final Date firstlogin = SH.getManager().getMysqlManager().getDate(rs.getString("firstlogin"));
                final Date lastlogin = SH.getManager().getMysqlManager().getDate(rs.getString("lastlogin"));
                final long timeplayed = rs.getLong("timeplayed");
                final int rank = rs.getInt("rank");
                final ArrayList<Integer> badges = new ArrayList<Integer>();
                final String[] ba = rs.getString("badges").split(",");
                String[] array;
                for (int length = (array = ba).length, i = 0; i < length; ++i) {
                    final String badge = array[i];
                    try {
                        badges.add(Integer.parseInt(badge));
                    }
                    catch (Exception ex) {}
                }
                final Location lastlocation = SH.getManager().getMysqlManager().getLocation(rs.getString("lastlocation"));
                final int level = rs.getInt("level");
                final int gamemode = rs.getInt("gamemode");
                final PlayerData data = new PlayerData(id, name, ips, uuid, firstlogin, lastlogin, timeplayed, rank, badges, lastlocation, level, gamemode);
                PlayerDataManager.playerdatalist.put(data.getUUID(), data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
