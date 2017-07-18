package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class TPAccept implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player && args.length == 0 && command.getName().equalsIgnoreCase("tpaccept")) {
            if (TPA.tpa.containsKey(Sender.getName())) {
                final Player p = Bukkit.getPlayer((String)TPA.tpa.get(Sender.getName()));
                p.teleport(((Player)Sender).getLocation());
                p.sendMessage(ChatColor.GREEN + "poff");
                TPA.tpa.remove(Sender.getName());
                Sender.sendMessage(ChatColor.GREEN + p.getName() + " ble teleportert til deg");
            }
            else {
                Sender.sendMessage(ChatColor.RED + "Du har ingen teleport foresp\u00f8rsel");
            }
        }
        return false;
    }
}
