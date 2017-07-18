package info.nordbyen.survivalheaven.subplugins.wand;

import info.nordbyen.survivalheaven.api.wand.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class WandManager implements IWandManager
{
    private static HashMap<String, Wand> Wands;
    
    static {
        WandManager.Wands = new HashMap<String, Wand>();
    }
    
    public WandManager() {
        Bukkit.getPluginManager().registerEvents((Listener)new WandListener(), (Plugin)SH.getPlugin());
    }
    
    @Override
    public void add(final Wand wand) {
        WandManager.Wands.put(wand.getName(), wand);
    }
    
    @Override
    public boolean createWand(final ItemStack target, final Wand wand, final Player player) {
        if (this.isWand(target)) {
            return false;
        }
        if (wand.canCreate(target, player)) {
            final ItemMeta meta = target.getItemMeta();
            meta.setDisplayName(SH.NAME);
            List<String> data = new ArrayList<String>();
            if (meta.hasLore()) {
                data = (List<String>)meta.getLore();
            }
            if (!data.contains(wand.getName())) {
                data.add(wand.getName());
            }
            meta.setLore((List)data);
            target.setItemMeta(meta);
            return true;
        }
        return false;
    }
    
    @Override
    public Wand get(final String id) {
        if (WandManager.Wands.containsKey(id)) {
            return WandManager.Wands.get(id);
        }
        return null;
    }
    
    @Override
    public boolean isWand(final ItemStack itemStack) {
        for (final String o : WandManager.Wands.keySet()) {
            if (itemStack == null) {
                return false;
            }
            final ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasLore() && meta.getLore().contains(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Wand search(final ItemStack itemStack) {
        for (final Object o : WandManager.Wands.keySet()) {
            final String s = o.toString();
            if (itemStack.getItemMeta().getLore().contains(s)) {
                return this.get(s);
            }
        }
        return null;
    }
}
