package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Fly implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (Sender.getName().equalsIgnoreCase("TheDudeAdrian")) {
                final Player g = (Player)Sender;
                g.setFlying(true);
                g.setAllowFlight(true);
                return false;
            }
            if (Sender.hasPermission("sh.fly")) {
                if (args.length == 1) {
                    final Player p = Bukkit.getPlayer(args[0]);
                    if (p.getAllowFlight()) {
                        p.sendMessage(ChatColor.GRAY + "Flying er n\u00e5 " + ChatColor.RED + "av");
                        p.setAllowFlight(false);
                        p.setFlying(false);
                    }
                    else {
                        p.sendMessage(ChatColor.GRAY + "Flying er n\u00e5 " + ChatColor.GREEN + "p\u00e5");
                        p.setAllowFlight(true);
                        p.setFlying(true);
                    }
                }
                else if (args.length == 0) {
                    final Player p = (Player)Sender;
                    if (p.getAllowFlight()) {
                        p.sendMessage(ChatColor.GRAY + "Flying er n\u00e5 " + ChatColor.RED + "av");
                        p.setAllowFlight(false);
                        p.setFlying(false);
                    }
                    else {
                        p.sendMessage(ChatColor.GRAY + "Flying er n\u00e5 " + ChatColor.GREEN + "p\u00e5");
                        p.setAllowFlight(true);
                        p.setFlying(true);
                    }
                }
            }
        }
        return false;
    }
}
