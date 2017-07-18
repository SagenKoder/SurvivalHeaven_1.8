package info.nordbyen.survivalheaven.api.util;

import org.bukkit.inventory.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import net.minecraft.server.v1_8_R3.*;

public class EnchantGlow
{
    public static ItemStack addGlow(final ItemStack item) {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) {
            tag = nmsStack.getTag();
        }
        final NBTTagList ench = new NBTTagList();
        tag.set("ench", (NBTBase)ench);
        nmsStack.setTag(tag);
        return (ItemStack)CraftItemStack.asCraftMirror(nmsStack);
    }
    
    public static ItemStack removeGlow(final ItemStack item) {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            return item;
        }
        tag = nmsStack.getTag();
        tag.set("ench", (NBTBase)null);
        nmsStack.setTag(tag);
        return (ItemStack)CraftItemStack.asCraftMirror(nmsStack);
    }
}
