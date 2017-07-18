package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public class PlayerDataManagerPlugin extends SubPlugin
{
    private boolean scheduler_running;
    
    public PlayerDataManagerPlugin(final String name) {
        super(name);
        this.scheduler_running = false;
    }
    
    public void disable() {
        SH.getManager().getPlayerDataManager().saveDataToDatabase();
    }
    
    public void enable() {
        ((PlayerDataManager)SH.getManager().getPlayerDataManager()).createTable();
        ((PlayerDataManager)SH.getManager().getPlayerDataManager()).updateDataFromDatabase();
        this.startScheduler();
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerDataListener(), (Plugin)this.getPlugin());
    }
    
    private void startScheduler() {
        if (this.scheduler_running) {
            return;
        }
        this.scheduler_running = true;
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin)this.getPlugin(), (Runnable)new PlayerDataTask((PlayerDataTask)null), 6000L, 6000L);
    }
    
    private class PlayerDataTask extends BukkitRunnable
    {
        public void run() {
            SH.getManager().getPlayerDataManager().saveDataToDatabase();
        }
    }
}
