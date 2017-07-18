package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.*;

public class H implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        final StringBuffer me = new StringBuffer();
        for (final String arg : args) {
            me.append(String.valueOf(arg) + " ");
        }
        if (sender.hasPermission("sh.h") && command.getName().equalsIgnoreCase("h")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "/h <tekst>");
            }
            else if (args.length >= 1) {
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Kj\u00f8p/Salg" + ChatColor.DARK_GREEN + "] " + ChatColor.DARK_GRAY + ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + ": " + (Object)me);
            }
        }
        return true;
    }
}
