package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class Jobb implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player && command.getName().equalsIgnoreCase("jobb") && Sender.hasPermission("sh.kick")) {
            final Player p = (Player)Sender;
            final Inventory i = Bukkit.createInventory((InventoryHolder)p, 9);
            i.setItem(0, new ItemStack(Material.WATCH));
            i.setItem(1, new ItemStack(Material.COMPASS));
            i.setItem(2, new ItemStack(Material.WOOD_AXE));
            i.setItem(4, new ItemStack(Material.BLAZE_ROD));
            i.setItem(6, new ItemStack(Material.BEDROCK));
            p.openInventory(i);
        }
        return false;
    }
}
