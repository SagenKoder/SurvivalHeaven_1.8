package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class Blokker implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (p.hasPermission("sh.blokker") && command.getName().equalsIgnoreCase("blokker") && args.length == 1) {
                final Inventory b = Bukkit.createInventory((InventoryHolder)p, 90, "Blokker");
                for (int i = 0; i < 91; ++i) {
                    final Material m = Material.valueOf(args[0].toUpperCase());
                    final ItemStack r = new ItemStack(m, 64);
                    b.setItem(i, r);
                }
                p.openInventory(b);
            }
        }
        return false;
    }
}
