package info.nordbyen.survivalheaven.subplugins.quest;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.subplugins.quest.first_encounter.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import info.nordbyen.survivalheaven.api.subplugin.annotations.*;

@SurvivalHeavenSubPlugin(name = "QuestHandler")
public class QuestHandler
{
    @SurvivalHeavenDisable
    private static void disble(final JavaPlugin plugin) {
    }
    
    @SurvivalHeavenEnable
    private static void enable(final JavaPlugin plugin) {
        FirstEncounterConfig.getInstance();
        Bukkit.getPluginManager().registerEvents((Listener)new FirstEncounterListener(), (Plugin)plugin);
        Godta_Command.initCommand();
    }
}
