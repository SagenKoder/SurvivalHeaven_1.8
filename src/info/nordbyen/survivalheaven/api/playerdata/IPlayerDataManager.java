package info.nordbyen.survivalheaven.api.playerdata;

import org.bukkit.entity.*;
import java.util.*;

public interface IPlayerDataManager
{
    void createPlayerData(final Player p0);
    
    IPlayerData getPlayerData(final String p0);
    
    IPlayerData getPlayerDataFromName(final String p0);
    
    void saveDataToDatabase();
    
    void updateDataFromDatabase();
    
    List<IPlayerData> getAllPlayerData();
}
