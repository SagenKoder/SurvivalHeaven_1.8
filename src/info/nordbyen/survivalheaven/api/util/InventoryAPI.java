package info.nordbyen.survivalheaven.api.util;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public class InventoryAPI
{
    public void add(final Inventory inventory, final Material material) {
        if (!this.isFull(inventory)) {
            inventory.addItem(new ItemStack[] { new ItemStack(material, 1) });
        }
    }
    
    public void add(final Inventory inventory, final Material material, final int amount) {
        int count = 0;
        final ItemStack items = new ItemStack(material, 1);
        do {
            inventory.addItem(new ItemStack[] { items });
            ++count;
        } while (!this.isFull(inventory) && count < amount);
    }
    
    public void add(final Player player, final Inventory inventory, final Material material, final int amount) {
        int count = 0;
        final ItemStack item = new ItemStack(material, 1);
        do {
            if (!this.isFull(inventory)) {
                inventory.addItem(new ItemStack[] { item });
                ++count;
            }
            else {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
                ++count;
            }
        } while (count < amount);
    }
    
    public void addStack(final Inventory inventory, final Material material) {
        if (this.hasEmptySlot(inventory)) {
            ItemStack item = new ItemStack(material);
            final int max = item.getMaxStackSize();
            item = new ItemStack(material, max);
            if (max != -1) {
                inventory.addItem(new ItemStack[] { item });
            }
        }
    }
    
    public void clear(final Inventory inventory) {
        inventory.clear();
    }
    
    public boolean contains(final Inventory inventory, final ItemStack itemstack) {
        return inventory.contains(itemstack);
    }
    
    public boolean contains(final Inventory inventory, final Material material) {
        return inventory.contains(material);
    }
    
    public boolean contains(final Inventory inventory, final Material material, final int amount) {
        return inventory.contains(material, amount);
    }
    
    public void copy(final Inventory source, final Inventory target) {
        final ItemStack[] items = source.getContents();
        target.setContents(items);
    }
    
    public void copy(final Inventory source, final Player target) {
        final Inventory targetInv = (Inventory)target.getInventory();
        this.copy(source, targetInv);
    }
    
    public void copy(final Player player, final Player target) {
        final Inventory sourceInv = (Inventory)player.getInventory();
        final Inventory targetInv = (Inventory)target.getInventory();
        this.copy(sourceInv, targetInv);
    }
    
    public String getName(final Inventory inventory) {
        return inventory.getName();
    }
    
    public String getTitle(final Inventory inventory) {
        return inventory.getTitle();
    }
    
    public boolean hasEmptySlot(final Inventory inventory) {
        ItemStack[] contents;
        for (int length = (contents = inventory.getContents()).length, i = 0; i < length; ++i) {
            final ItemStack items = contents[i];
            if (items == null) {
                return false;
            }
        }
        return true;
    }
    
    boolean isFull(final Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }
    
    public void remove(final Inventory inventory, final Material material, int amount) {
        if (this.contains(inventory, material, amount)) {
            do {
                inventory.remove(new ItemStack(material, 1));
            } while (--amount > 0);
        }
    }
    
    public void removeAll(final Inventory inventory, final Material material) {
        if (this.contains(inventory, material)) {
            inventory.remove(material);
        }
    }
}
