package info.nordbyen.survivalheaven.api.util;

import org.bukkit.help.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import java.lang.reflect.*;

public class CommandUtils
{
    public static List<HelpTopic> getHelpTopics() {
        final List<HelpTopic> helpTopics = new ArrayList<HelpTopic>();
        for (final HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
            helpTopics.add(cmdLabel);
        }
        return helpTopics;
    }
    
    public static Map<String, Command> getKnownCommands() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final PluginManager manager = Bukkit.getServer().getPluginManager();
        final SimplePluginManager spm = (SimplePluginManager)manager;
        List<Plugin> plugins = null;
        Map<String, Plugin> lookupNames = null;
        SimpleCommandMap commandMap = null;
        Map<String, Command> knownCommands = null;
        if (spm != null) {
            final Field pluginsField = spm.getClass().getDeclaredField("plugins");
            final Field lookupNamesField = spm.getClass().getDeclaredField("lookupNames");
            final Field commandMapField = spm.getClass().getDeclaredField("commandMap");
            pluginsField.setAccessible(true);
            lookupNamesField.setAccessible(true);
            commandMapField.setAccessible(true);
            plugins = (List<Plugin>)pluginsField.get(spm);
            lookupNames = (Map<String, Plugin>)lookupNamesField.get(spm);
            commandMap = (SimpleCommandMap)commandMapField.get(spm);
            final Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            knownCommands = (Map<String, Command>)knownCommandsField.get(commandMap);
        }
        return knownCommands;
    }
}
