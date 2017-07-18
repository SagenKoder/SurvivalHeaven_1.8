package info.nordbyen.survivalheaven.subplugins.serverutil;

import org.bukkit.plugin.java.*;
import info.nordbyen.survivalheaven.api.subplugin.annotations.*;
import info.nordbyen.survivalheaven.subplugins.commands.commands.*;

@SurvivalHeavenSubPlugin(name = "ServerUtils")
public final class ServerUtils
{
    @SurvivalHeavenDisable
    private static void disable(final JavaPlugin plugin) {
        unregisterCommands();
        unregisterListeners();
    }
    
    @SurvivalHeavenEnable
    private static void enable(final JavaPlugin plugin) {
        registerCommands();
        registerListeners();
    }
    
    private static void registerCommands() {
        ServerCommand.initCommand();
    }
    
    private static void registerListeners() {
    }
    
    private static void unregisterCommands() {
        ServerCommand.clearCommand();
    }
    
    private static void unregisterListeners() {
    }
}
