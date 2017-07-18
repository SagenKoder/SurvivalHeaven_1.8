package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.inventory.*;

public class Hatt implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Kan bare utf\u00f8res av In game spillere");
            return true;
        }
        final Player p = (Player)sender;
        final ItemStack helmet = p.getInventory().getItemInHand();
        if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(sender.getName()).getRank() > 1 && command.getName().equalsIgnoreCase("hatt") && args.length == 0) {
            p.sendMessage(ChatColor.GREEN + "Hatt satt!");
            p.getInventory().setItemInHand(p.getInventory().getHelmet());
            p.getInventory().setHelmet(helmet);
        }
        return true;
    }
}
