package info.nordbyen.survivalheaven;

import info.nordbyen.survivalheaven.subplugins.blockdata.*;
import info.nordbyen.survivalheaven.subplugins.groupmanager.*;
import info.nordbyen.survivalheaven.subplugins.homes.*;
import info.nordbyen.survivalheaven.api.mysql.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import info.nordbyen.survivalheaven.api.regions.*;
import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.api.wand.*;
import info.nordbyen.survivalheaven.subplugins.ban.*;

public interface ISH
{
    IAnnoSubPluginManager getAnnoSubPluginManager();
    
    IBlockManager getBlockManager();
    
    FriendManager getFriendManager();
    
    HomeManager getHomeManager();
    
    IMysqlManager getMysqlManager();
    
    IPlayerDataManager getPlayerDataManager();
    
    String getPluginName();
    
    IRankManager getRankManager();
    
    IRegionManager getRegionManager();
    
    ISubPluginManager getSubPluginManager();
    
    String getVersion();
    
    IWandManager getWandManager();
    
    BanManager getBanManager();
}
