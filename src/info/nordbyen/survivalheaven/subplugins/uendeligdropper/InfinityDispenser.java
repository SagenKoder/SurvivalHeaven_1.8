package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import org.bukkit.configuration.file.*;
import java.util.logging.*;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.*;
import org.bukkit.plugin.java.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import info.nordbyen.survivalheaven.api.subplugin.annotations.*;

@SurvivalHeavenSubPlugin(name = "InfDisp")
public class InfinityDispenser
{
    public static Plugin plugin;
    public static InfinityDispenser instance;
    public static FileConfiguration config;
    public static final Logger log;
    private static Dispensers dispensers;
    
    static {
        log = Logger.getLogger("Minecraft");
    }
    
    @SurvivalHeavenDisable
    private static void disable(final JavaPlugin plugin) {
    }
    
    @SurvivalHeavenEnable
    private static void enable(final JavaPlugin plugin) {
        InfinityDispenser.dispensers = new Dispensers();
        final PluginManager pluginmanager = plugin.getServer().getPluginManager();
        pluginmanager.registerEvents((Listener)new InfinityDispenserListener(), (Plugin)plugin);
        pluginmanager.registerEvents((Listener)new BlockListener(), (Plugin)plugin);
        plugin.getCommand("infdisp").setExecutor((CommandExecutor)new Commands());
    }
    
    public static Dispensers getDispensers() {
        return InfinityDispenser.dispensers;
    }
}
