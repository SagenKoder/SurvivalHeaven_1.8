package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;

public class Mute implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Bukkit.getPlayer(args[0]) == null) {
            return false;
        }
        if (Sender.hasPermission("sh.kick") && args.length > 0) {
            if (args.length > 1) {
                if (Sender.hasPermission("sh.kick")) {
                    final SH sh = (SH)SH.getManager();
                    if (!SH.mutedPlayers.contains(args[0])) {
                        final SH sh2 = (SH)SH.getManager();
                        SH.mutedPlayers.add(args[0]);
                        final StringBuffer me = new StringBuffer();
                        for (int i = 1; i < args.length; ++i) {
                            me.append(String.valueOf(args[i]) + " ");
                        }
                        Bukkit.getPlayer(args[0]).sendMessage(ChatColor.RED + "Du ble mutet av " + Sender.getName() + ". Grunn: " + (Object)me);
                    }
                    else {
                        Sender.sendMessage(ChatColor.RED + "Denne spilleren er allerede mutet. Fjern grunnen \u00e5 pr\u00f8v igjen");
                    }
                }
            }
            else {
                final SH sh3 = (SH)SH.getManager();
                if (!SH.mutedPlayers.contains(args[0])) {
                    final SH sh4 = (SH)SH.getManager();
                    SH.mutedPlayers.add(args[0]);
                    Bukkit.getPlayer(args[0]).sendMessage(ChatColor.RED + "Du ble mutet av " + Sender.getName() + ".");
                    Sender.sendMessage(ChatColor.GREEN + "Du mutet " + args[0]);
                }
                else {
                    final SH sh5 = (SH)SH.getManager();
                    SH.mutedPlayers.remove(args[0]);
                    Bukkit.getPlayer(args[0]).sendMessage(ChatColor.GREEN + "Du ble un-mutet av " + Sender.getName() + ".");
                    Sender.sendMessage(ChatColor.GREEN + "Du un-mutet " + args[0]);
                }
            }
        }
        return false;
    }
}
