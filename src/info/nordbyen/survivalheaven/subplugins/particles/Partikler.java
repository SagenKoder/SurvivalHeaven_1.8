package info.nordbyen.survivalheaven.subplugins.particles;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Partikler implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (!(Sender instanceof Player)) {
            return false;
        }
        if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(Sender.getName()).getRank() >= 2) {
            final Inventory i = Bukkit.createInventory((InventoryHolder)null, 27, "Tilgjengelige partikler");
            final ItemStack is1 = new ItemStack(Material.INK_SACK, 1, (short)1);
            final ItemStack is2 = new ItemStack(Material.BARRIER);
            final ItemStack is3 = new ItemStack(Material.DIAMOND_SWORD);
            final ItemStack is4 = new ItemStack(Material.POISONOUS_POTATO);
            final ItemStack is5 = new ItemStack(Material.REDSTONE);
            final ItemStack is6 = new ItemStack(Material.EMERALD);
            final ItemStack is7 = new ItemStack(Material.EXP_BOTTLE);
            final ItemStack is8 = new ItemStack(Material.TNT);
            final ItemStack is9 = new ItemStack(Material.FIREWORK_CHARGE);
            final ItemStack is10 = new ItemStack(Material.INK_SACK, 1, (short)15);
            final ItemStack is11 = new ItemStack(Material.WATER_BUCKET);
            final ItemMeta im1 = is1.getItemMeta();
            final ItemMeta im2 = is2.getItemMeta();
            final ItemMeta im3 = is3.getItemMeta();
            final ItemMeta im4 = is4.getItemMeta();
            final ItemMeta im5 = is5.getItemMeta();
            final ItemMeta im6 = is6.getItemMeta();
            final ItemMeta im7 = is7.getItemMeta();
            final ItemMeta im8 = is8.getItemMeta();
            final ItemMeta im9 = is9.getItemMeta();
            final ItemMeta im10 = is10.getItemMeta();
            final ItemMeta im11 = is11.getItemMeta();
            im1.setDisplayName("Hjerter");
            im2.setDisplayName("Ingen");
            im3.setDisplayName("Critical");
            im4.setDisplayName("Mob spell");
            im5.setDisplayName("Redstone");
            im6.setDisplayName("Happy Villager");
            im7.setDisplayName("Critical Magic");
            im8.setDisplayName("Eksplosjon");
            im9.setDisplayName("Happy Villager");
            im10.setDisplayName("Sky");
            im11.setDisplayName("Suspended Depth");
            is1.setItemMeta(im1);
            is2.setItemMeta(im2);
            is3.setItemMeta(im3);
            is4.setItemMeta(im4);
            is5.setItemMeta(im5);
            is6.setItemMeta(im6);
            is7.setItemMeta(im7);
            is8.setItemMeta(im8);
            is9.setItemMeta(im9);
            is10.setItemMeta(im10);
            is11.setItemMeta(im11);
            i.setItem(9, is1);
            i.setItem(4, is2);
            i.setItem(12, is3);
            i.setItem(13, is4);
            i.setItem(14, is5);
            i.setItem(15, is6);
            i.setItem(16, is7);
            i.setItem(17, is8);
            i.setItem(10, is9);
            i.setItem(11, is10);
            i.setItem(18, is11);
            ((Player)Sender).openInventory(i);
        }
        return false;
    }
}
