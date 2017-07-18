package info.nordbyen.survivalheaven.api.util;

import org.bukkit.inventory.meta.*;
import org.bukkit.util.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class BukkitHelperAPI
{
    public static ItemStack createBook(final String title, final String owner, final String... pages) {
        final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        final BookMeta bm = (BookMeta)book.getItemMeta();
        for (final String page : pages) {
            bm.addPage(new String[] { page });
        }
        bm.setTitle(title);
        bm.setDisplayName(title);
        bm.setAuthor(owner);
        book.setItemMeta((ItemMeta)bm);
        return book;
    }
    
    public static Location getLocFromPlayer(final Player p, final double dist) {
        final Location eyelocation = p.getEyeLocation();
        eyelocation.add(eyelocation.getDirection().multiply(dist));
        return eyelocation;
    }
    
    public static void shootArrow(final Location start, final Vector dir) {
        final Entity bullet = start.getWorld().spawnEntity(start, EntityType.ARROW);
        bullet.setVelocity(dir.multiply(4.0));
    }
    
    public static void updateInventoryLater(final Player p) {
        new BukkitRunnable() {
            public void run() {
                p.updateInventory();
            }
        }.runTaskLater((Plugin)SH.getPlugin(), 1L);
    }
    
    public Inventory getInventory(final FileConfiguration file, final String path) {
        if (file.contains(path)) {
            final Inventory inv = Bukkit.createInventory((InventoryHolder)file.get(String.valueOf(path) + ".inventoryholder"), file.getInt(String.valueOf(path) + ".inventorysize"), file.getString(String.valueOf(path) + ".inventorytitle"));
            inv.setMaxStackSize(file.getInt(String.valueOf(path) + ".maxstacksize"));
            for (int i = 0; i < inv.getSize(); ++i) {
                if (file.contains(String.valueOf(path) + ".contents." + i)) {
                    inv.setItem(i, file.getItemStack(String.valueOf(path) + ".contents." + i));
                }
            }
            return inv;
        }
        return null;
    }
    
    public void saveInventory(final Inventory inventory, final FileConfiguration file, final String path) {
        int i = 0;
        ItemStack[] contents;
        for (int length = (contents = inventory.getContents()).length, j = 0; j < length; ++j) {
            final ItemStack im = contents[j];
            file.set(String.valueOf(path) + ".contents." + i, (Object)im);
            ++i;
        }
        file.set(String.valueOf(path) + ".maxstacksize", (Object)inventory.getMaxStackSize());
        file.set(String.valueOf(path) + ".inventorytitle", (Object)inventory.getTitle());
        file.set(String.valueOf(path) + ".inventorysize", (Object)inventory.getSize());
        file.set(String.valueOf(path) + ".inventoryholder", (Object)inventory.getHolder());
    }
}
