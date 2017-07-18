package info.nordbyen.survivalheaven.api.util;

import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class InventoryStringDeSerializer
{
    public static String InventoryToString(final Inventory invInventory) {
        String serialization = String.valueOf(invInventory.getSize()) + ";";
        for (int i = 0; i < invInventory.getSize(); ++i) {
            final ItemStack is = invInventory.getItem(i);
            if (is != null) {
                String serializedItemStack = new String();
                final String isType = String.valueOf(is.getType().getId());
                serializedItemStack = String.valueOf(serializedItemStack) + "t@" + isType;
                if (is.getDurability() != 0) {
                    final String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack = String.valueOf(serializedItemStack) + ":d@" + isDurability;
                }
                if (is.getAmount() != 1) {
                    final String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack = String.valueOf(serializedItemStack) + ":a@" + isAmount;
                }
                final Map<Enchantment, Integer> isEnch = (Map<Enchantment, Integer>)is.getEnchantments();
                if (isEnch.size() > 0) {
                    for (final Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                        serializedItemStack = String.valueOf(serializedItemStack) + ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                    }
                }
                serialization = String.valueOf(serialization) + i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }
    
    public static Inventory StringToInventory(final String invString, final String title) {
        final String[] serializedBlocks = invString.split(";");
        final String invInfo = serializedBlocks[0];
        final Inventory deserializedInventory = Bukkit.getServer().createInventory((InventoryHolder)null, (int)Integer.valueOf(invInfo), title);
        for (int i = 1; i < serializedBlocks.length; ++i) {
            final String[] serializedBlock = serializedBlocks[i].split("#");
            final int stackPosition = Integer.valueOf(serializedBlock[0]);
            if (stackPosition < deserializedInventory.getSize()) {
                ItemStack is = null;
                Boolean createdItemStack = false;
                final String[] serializedItemStack = serializedBlock[1].split(":");
                String[] array;
                for (int length = (array = serializedItemStack).length, j = 0; j < length; ++j) {
                    final String itemInfo = array[j];
                    final String[] itemAttribute = itemInfo.split("@");
                    if (itemAttribute[0].equals("t")) {
                        is = new ItemStack(Material.getMaterial((int)Integer.valueOf(itemAttribute[1])));
                        createdItemStack = true;
                    }
                    else if (itemAttribute[0].equals("d") && createdItemStack) {
                        is.setDurability((short)Short.valueOf(itemAttribute[1]));
                    }
                    else if (itemAttribute[0].equals("a") && createdItemStack) {
                        is.setAmount((int)Integer.valueOf(itemAttribute[1]));
                    }
                    else if (itemAttribute[0].equals("e") && createdItemStack) {
                        is.addEnchantment(Enchantment.getById((int)Integer.valueOf(itemAttribute[1])), (int)Integer.valueOf(itemAttribute[2]));
                    }
                }
                deserializedInventory.setItem(stackPosition, is);
            }
        }
        return deserializedInventory;
    }
}
