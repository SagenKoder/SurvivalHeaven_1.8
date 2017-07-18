package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Tips implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final StringBuffer me = new StringBuffer();
            for (int i = 0; i < args.length; ++i) {
                me.append(String.valueOf(args[i]) + " ");
            }
            if (Sender.hasPermission("Blokker.tips") && command.getName().equalsIgnoreCase("tips")) {
                if (args.length == 0) {
                    Sender.sendMessage(ChatColor.RED + "Feil: Bruk /tips <melding>");
                }
                else if (args.length >= 1) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Tips] " + ChatColor.WHITE + (Object)me);
                }
            }
        }
        return true;
    }
}
