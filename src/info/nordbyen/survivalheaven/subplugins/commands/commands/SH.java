package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class SH implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String CommandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (command.getName().equalsIgnoreCase("sh")) {
                if (args.length > 0) {
                    final StringBuffer msg = new StringBuffer();
                    for (final String arg : args) {
                        msg.append(String.valueOf(arg) + " ");
                    }
                    p.sendMessage(ChatColor.DARK_RED + "Meldingen din har blitt sendt til alle p\u00e5 logget Stab medlemmer. En Stab medlem vil ta kontakt s\u00e5 fort som mulig!");
                    for (final Player r : Bukkit.getOnlinePlayers()) {
                        if (r.hasPermission("sh.kick")) {
                            if (r != null) {
                                r.sendMessage(ChatColor.DARK_RED + "[StabHjelp] " + p.getName() + ": " + ChatColor.RED + msg.toString());
                            }
                            else {
                                p.sendMessage(ChatColor.RED + "Finner ingen online stabmedlemmer");
                            }
                        }
                    }
                }
                else {
                    Sender.sendMessage(ChatColor.RED + "Feil: Bruk /sh <melding>");
                }
            }
        }
        else {
            Sender.sendMessage(ChatColor.RED + "Du er ikke en in game spiller");
        }
        return true;
    }
}
