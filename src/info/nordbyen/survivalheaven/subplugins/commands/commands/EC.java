package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class EC implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (p.hasPermission("sh.ec")) {
                if (command.getName().equalsIgnoreCase("ec")) {
                    if (args.length == 0) {
                        p.openInventory(p.getEnderChest());
                    }
                    else {
                        p.sendMessage(ChatColor.RED + "Feil bruk av kommando");
                    }
                }
            }
            else {
                p.sendMessage(ChatColor.RED + "Du har ikke permission!");
            }
        }
        return true;
    }
}
