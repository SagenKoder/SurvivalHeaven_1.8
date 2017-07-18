package info.nordbyen.survivalheaven.subplugins.playerheads;

import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;

public class PlayerHeads extends SubPlugin implements Listener
{
    public PlayerHeads(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)SH.getPlugin());
    }
    
    @EventHandler
    public void PlayerDeath(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        if (e.getEntity().getKiller() instanceof Player) {
            final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            final SkullMeta hd = (SkullMeta)head.getItemMeta();
            hd.setDisplayName(ChatColor.YELLOW + p.getName() + "s hode");
            hd.setOwner(p.getName());
            head.setItemMeta((ItemMeta)hd);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), head);
        }
    }
}
