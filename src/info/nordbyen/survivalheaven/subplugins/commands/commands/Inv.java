package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class Inv implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        final Player p = (Player)Sender;
        final Player targetPlayer = p.getServer().getPlayer(args[0]);
        if (p.hasPermission("cd.inv") && command.getName().equalsIgnoreCase("inv")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "/inv <spiller>");
            }
            else if (args.length == 1) {
                p.openInventory((Inventory)targetPlayer.getInventory());
            }
        }
        return true;
    }
}
