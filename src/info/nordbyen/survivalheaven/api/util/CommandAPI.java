package info.nordbyen.survivalheaven.api.util;

import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;

public class CommandAPI
{
    private static HashMap<String, CommandExecutor> commands;
    
    public CommandAPI() {
        CommandAPI.commands = new HashMap<String, CommandExecutor>();
    }
    
    public void addCommand(final String command, final CommandExecutor executor) {
        CommandAPI.commands.put(command, executor);
    }
    
    public void initCommand(final String command, final CommandExecutor executor) {
        Bukkit.getPluginCommand(command).setExecutor(executor);
    }
    
    public void initCommands() {
        for (final String command : CommandAPI.commands.keySet()) {
            Bukkit.getPluginCommand(command).setExecutor((CommandExecutor)CommandAPI.commands.get(command));
        }
    }
}
