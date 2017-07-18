package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Viktig implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final StringBuffer me = new StringBuffer();
            for (int i = 0; i < args.length; ++i) {
                me.append(String.valueOf(args[i]) + " ");
            }
            if (Sender.hasPermission("Blokker.viktig") && command.getName().equalsIgnoreCase("viktig")) {
                if (args.length == 0) {
                    Sender.sendMessage(ChatColor.RED + "Feil: Bruk /viktig <melding>");
                }
                else if (args.length >= 1) {
                    Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "Viktig" + ChatColor.RED + "] " + ChatColor.WHITE + (Object)me);
                }
            }
        }
        return true;
    }
}
