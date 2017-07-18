package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class K implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (p.hasPermission("sh.k") && command.getName().equalsIgnoreCase("k")) {
                if (args.length > 1) {
                    p.sendMessage(ChatColor.RED + "Pr\u00f8ve /k eller /k <spiller>??");
                }
                if (args.length == 0) {
                    if (p.getGameMode().equals((Object)GameMode.CREATIVE)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(ChatColor.GOLD + "[Alarm] Du skiftet spillermodus til survival");
                    }
                    else if (p.getGameMode().equals((Object)GameMode.SURVIVAL)) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.GOLD + "[Alarm] Du skiftet spillermodus til kreativ");
                        Bukkit.broadcast(ChatColor.GOLD + "[StabAlarm] LAV: " + ChatColor.WHITE + p.getName() + " skrudde p\u00e5 kreativ", "sh.kick");
                    }
                }
                if (args.length == 1) {
                    final Player targetPlayer = p.getServer().getPlayer(args[0]);
                    if (targetPlayer.getGameMode().equals((Object)GameMode.CREATIVE)) {
                        targetPlayer.setGameMode(GameMode.SURVIVAL);
                        targetPlayer.sendMessage(ChatColor.GOLD + "[Alarm] En i stabben skiftet ditt spillermodus til survival");
                    }
                    else if (targetPlayer.getGameMode().equals((Object)GameMode.SURVIVAL)) {
                        targetPlayer.setGameMode(GameMode.CREATIVE);
                        targetPlayer.sendMessage(ChatColor.GOLD + "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
                        Bukkit.broadcast(ChatColor.GOLD + "[StabAlarm] MIDDELS: " + ChatColor.WHITE + p.getName() + " skrudde p\u00e5 kreativ til " + targetPlayer.getName(), "sh.kick");
                    }
                }
            }
        }
        else if (args.length == 1) {
            final Player targetPlayer2 = Bukkit.getServer().getPlayer(args[0]);
            if (targetPlayer2.getGameMode().equals((Object)GameMode.CREATIVE)) {
                targetPlayer2.setGameMode(GameMode.SURVIVAL);
                targetPlayer2.sendMessage(ChatColor.GOLD + "[Alarm] En i stabben skiftet ditt spillermodus til survival");
            }
            else if (targetPlayer2.getGameMode().equals((Object)GameMode.SURVIVAL)) {
                targetPlayer2.setGameMode(GameMode.CREATIVE);
                targetPlayer2.sendMessage(ChatColor.GOLD + "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
            }
            else {
                Sender.sendMessage(ChatColor.RED + "Du er ikke en spiller");
            }
        }
        return true;
    }
}
