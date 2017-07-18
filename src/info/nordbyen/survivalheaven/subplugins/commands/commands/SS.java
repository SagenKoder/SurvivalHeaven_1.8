package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import java.util.*;

public class SS implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String CommandLabel, final String[] args) {
        final RankType rank = SH.getManager().getRankManager().getRank(((Player)sender).getUniqueId().toString());
        if (rank != RankType.ADMINISTRATOR && rank != RankType.MODERATOR && rank != RankType.UTVIKLER) {
            sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til denne kommandoen");
            return true;
        }
        if (command.getName().equalsIgnoreCase("ss")) {
            if (args.length > 0) {
                final StringBuffer msg = new StringBuffer();
                for (final String arg : args) {
                    msg.append(String.valueOf(arg) + " ");
                }
                for (final Player b : Bukkit.getOnlinePlayers()) {
                    if (b.hasPermission("sh.ss.motta")) {
                        if (sender instanceof Player) {
                            b.sendMessage(ChatColor.GOLD + "[Stabsamtale] " + ChatColor.RED + ((Player)sender).getName() + ": " + ChatColor.GREEN + msg.toString());
                        }
                        else {
                            System.out.println(ChatColor.GOLD + "[Stabsamtale] " + ((Player)sender).getName() + ": " + ChatColor.GRAY + msg.toString());
                        }
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Feil: Bruk /ss <melding>");
            }
        }
        return false;
    }
}
