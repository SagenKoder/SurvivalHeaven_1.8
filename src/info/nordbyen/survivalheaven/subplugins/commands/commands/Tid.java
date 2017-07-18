package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.plugin.java.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Tid extends JavaPlugin implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        final Player p = (Player)sender;
        final World w = p.getWorld();
        if (p.hasPermission("cd.tid") && command.getName().equalsIgnoreCase("tid")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.GOLD + "Tidssystemet:");
                p.sendMessage(ChatColor.BLUE + "/tid dag - " + ChatColor.GOLD + "stiller din tid til dag.");
                p.sendMessage(ChatColor.BLUE + "/tid natt - " + ChatColor.GOLD + "stiller din tid til natt.");
                p.sendMessage(ChatColor.BLUE + "/tid morgen - " + ChatColor.GOLD + "stiller din tid til morgen.");
                p.sendMessage(ChatColor.BLUE + "/tid kveld - " + ChatColor.GOLD + "stiller din tid til kveld.");
                p.sendMessage(ChatColor.BLUE + "/tid reset - " + ChatColor.GOLD + "stiller din tid til servertid.");
                p.sendMessage(ChatColor.BLUE + "/tid info - " + ChatColor.GOLD + "gir deg litt info om tiden din.");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("dag")) {
                    p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Din tid har blitt endret til dag.");
                    p.getWorld().setTime(6000L);
                    w.setWeatherDuration(0);
                    return true;
                }
                if (args[0].equalsIgnoreCase("natt")) {
                    p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Din tid har blitt endret til natt.");
                    p.getWorld().setTime(18000L);
                    return true;
                }
                if (args[0].equalsIgnoreCase("morgen")) {
                    p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Din tid har blitt endret til morgen.");
                    p.getWorld().setTime(0L);
                    return true;
                }
                if (args[0].equalsIgnoreCase("kveld")) {
                    p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Din tid har blitt endret til kveld.");
                    p.getWorld().setTime(12000L);
                    return true;
                }
                if (args[0].equalsIgnoreCase("info")) {
                    p.sendMessage(ChatColor.GOLD + "Din tid er satt p\u00e5 " + ChatColor.BLUE + p.getPlayerTime() + ChatColor.GOLD + ".");
                    p.sendMessage(ChatColor.GOLD + "Forskjellen mellom serverertiden og spillertiden er " + ChatColor.BLUE + p.getPlayerTimeOffset() + ChatColor.GOLD + ".");
                    p.sendMessage(ChatColor.GOLD + "Du er i verden:" + p.getWorld().getName() + ChatColor.GOLD + ".");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reset")) {
                    p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Funker bare p\u00e5 egen spillertid.");
                    return true;
                }
                p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Du har skrevet feil, skriv /tid for mere info.");
                return true;
            }
            else {
                p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Denne kommandoen er deaktivert i denne worlden.");
            }
        }
        return true;
    }
}
