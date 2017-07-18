package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import java.sql.*;
import java.util.*;

public class PlayerDataManagerSuper implements IPlayerDataManager
{
    private static HashMap<String, IPlayerData> playerdatalist;
    
    static {
        PlayerDataManagerSuper.playerdatalist = new HashMap<String, IPlayerData>();
    }
    
    void createTable() {
        try {
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `players` (`id` INT(11) NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255) NOT NULL, `name` VARCHAR(255) NOT NULL, `ips` LONGTEXT NOT NULL, `gamemode` INT(2) NOT NULL DEFAULT 0, `firstlogin` VARCHAR(255) NOT NULL, `lastlogin` VARCHAR(255) NOT NULL, `timeplayed` BIGINT(11) DEFAULT 0 NOT NULL, `bank` INT(11) DEFAULT 300, `rank` INT(11) DEFAULT 1, `badges` VARCHAR(255) DEFAULT \"\", `level` INT(11) DEFAULT 1, `language` VARCHAR(255) DEFAULT \"norsk\", `lastlocation` VARCHAR(255) DEFAULT \"NO\" NOT NULL, PRIMARY KEY (`id`) );");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void createPlayerData(final Player p) {
        if (PlayerDataManagerSuper.playerdatalist.containsKey(p.getUniqueId().toString())) {
            return;
        }
        PlayerDataManagerSuper.playerdatalist.put(p.getUniqueId().toString(), new PlayerDataSuper(p.getUniqueId().toString(), p.getName()));
    }
    
    @Override
    public IPlayerData getPlayerData(final String uuid) {
        return PlayerDataManagerSuper.playerdatalist.get(uuid);
    }
    
    @Override
    public IPlayerData getPlayerDataFromName(final String name) {
        return null;
    }
    
    @Override
    public void saveDataToDatabase() {
    }
    
    @Override
    public void updateDataFromDatabase() {
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT name, uuid FROM players;");
            while (rs != null) {
                if (!rs.next()) {
                    break;
                }
                final String name = rs.getString("name");
                final String uuid = rs.getString("uuid");
                final PlayerDataSuper pds = new PlayerDataSuper(uuid, name);
                PlayerDataManagerSuper.playerdatalist.put(uuid, pds);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<IPlayerData> getAllPlayerData() {
        return null;
    }
}
