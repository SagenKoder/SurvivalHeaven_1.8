package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Kick implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (command.getName().equalsIgnoreCase("kick") && Sender.hasPermission("sh.kick")) {
            if (args.length == 0) {
                Sender.sendMessage(ChatColor.RED + "Feil: Bruk /kick <spiller>");
                return true;
            }
            if (args.length == 1) {
                final Player targetPlayer = Bukkit.getPlayer(args[0]);
                targetPlayer.kickPlayer("Du ble kicket av " + ChatColor.WHITE + Sender.getName() + ChatColor.GREEN + " Grunn: Hemmelig");
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + targetPlayer.getName() + " ble sparket ut av " + Sender.getName());
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "Grunn: Hemmelig");
                return true;
            }
            if (args.length >= 2) {
                final Player targetPlayer = Bukkit.getPlayer(args[0]);
                final StringBuffer me = new StringBuffer();
                for (int i = 1; i < args.length; ++i) {
                    me.append(String.valueOf(args[i]) + " ");
                }
                targetPlayer.kickPlayer("Du ble kicket av " + ChatColor.WHITE + Sender.getName() + ChatColor.GREEN + " Grunn: " + me.toString().trim());
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + targetPlayer.getName() + " ble sparket ut. Grunn: " + me.toString().trim());
                return true;
            }
        }
        return false;
    }
}
