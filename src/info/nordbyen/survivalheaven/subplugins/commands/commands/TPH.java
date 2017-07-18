package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class TPH implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            final Player targetPlayer = p.getServer().getPlayer(args[0]);
            if (p.hasPermission("sh.tph") && command.getName().equalsIgnoreCase("tph")) {
                if (args.length == 0 || args.length > 1) {
                    p.sendMessage(ChatColor.RED + "/tph <spiller>");
                }
                else if (args.length == 1) {
                    targetPlayer.teleport(p.getLocation());
                    targetPlayer.sendMessage(ChatColor.GOLD + "[Alarm] Du ble teleportert til " + ChatColor.WHITE + p.getName());
                    p.sendMessage(ChatColor.GOLD + "[Alarm] Du teleporterte " + ChatColor.WHITE + targetPlayer.getName() + ChatColor.GOLD + " til deg.");
                }
            }
        }
        else {
            Sender.sendMessage(ChatColor.RED + "Du er ikke en in game spiller");
        }
        return true;
    }
}
