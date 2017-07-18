package info.nordbyen.survivalheaven.api.util;

import org.bukkit.enchantments.*;
import net.minecraft.server.v1_8_R3.*;

public class StoredEnchantment
{
    private Enchantment ench;
    private short lvl;
    
    public StoredEnchantment(final Enchantment ench, final int lvl) {
        this.setEnchantment(ench);
        this.setLevel((short)lvl);
    }
    
    public StoredEnchantment(final NBTTagCompound tag) {
        this.setEnchantment(tag.getShort("id"));
        this.setLevel(tag.getShort("lvl"));
    }
    
    public Enchantment getEnchantment() {
        return this.ench;
    }
    
    public short getLevel() {
        return this.lvl;
    }
    
    public NBTBase getTag() {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("id", (short)this.getEnchantment().getId());
        tag.setShort("lvl", this.getLevel());
        return (NBTBase)tag;
    }
    
    public void setEnchantment(final Enchantment ench) {
        this.ench = ench;
    }
    
    public void setEnchantment(final int ench) {
        this.ench = Enchantment.getById(ench);
    }
    
    public void setLevel(final short lvl) {
        this.lvl = lvl;
    }
}
