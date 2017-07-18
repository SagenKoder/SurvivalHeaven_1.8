package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Who implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("sh.who") && command.getName().equalsIgnoreCase("who")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Antall p\u00e5logget: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size());
                return true;
            }
            if (args.length == 1) {
                final Player v = Bukkit.getServer().getPlayer(args[0]);
                sender.sendMessage(((v == null) ? (ChatColor.GOLD + "[Alarm] ") : ChatColor.GREEN) + args[0] + " er " + ((v == null) ? "ikke " : "") + "p\u00e5logget");
                return true;
            }
        }
        return false;
    }
}
