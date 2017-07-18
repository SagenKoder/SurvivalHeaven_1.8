package info.nordbyen.survivalheaven.subplugins.shop;

import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.event.player.*;
import info.nordbyen.survivalheaven.*;
import java.util.*;
import org.bukkit.plugin.*;

public class ShopListener implements Listener
{
    public HashMap<String, ArrayList<Long>> hash;
    public boolean timerIsRunning;
    
    public ShopListener() {
        this.hash = new HashMap<String, ArrayList<Long>>();
        this.timerIsRunning = false;
    }
    
    @EventHandler
    public void mine(final BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.IRON_ORE || e.getBlock().getType() == Material.REDSTONE_ORE) {
            ArrayList<Long> blocktimelist = this.hash.get(e.getPlayer().getName());
            if (blocktimelist == null) {
                blocktimelist = new ArrayList<Long>();
                this.hash.put(e.getPlayer().getName(), blocktimelist);
            }
            blocktimelist.add(System.currentTimeMillis());
            Bukkit.broadcastMessage(ChatColor.GOLD + "DEBUG: " + e.getPlayer().getName() + ": " + blocktimelist.size());
        }
    }
    
    @EventHandler
    public void onClick(final PlayerInteractEvent e) {
        e.getPlayer().getName().equalsIgnoreCase("l0lkj");
    }
    
    @EventHandler
    public void onInventory(final InventoryClickEvent e) {
        final Inventory inv = e.getClickedInventory();
        final ItemStack item = inv.getItem(e.getSlot());
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        this.hash.put(e.getPlayer().getName(), new ArrayList<Long>());
    }
    
    @EventHandler
    public void onLeave(final PlayerQuitEvent e) {
        this.hash.remove(e.getPlayer().getName());
    }
    
    public void startTimer() {
        Bukkit.broadcastMessage(ChatColor.GREEN + "PR\u00d8VER");
        if (this.timerIsRunning) {
            return;
        }
        this.timerIsRunning = true;
        Bukkit.broadcastMessage(ChatColor.GREEN + "STARTER");
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ChatColor.GREEN + "KJ\u00d8RER");
                for (final Map.Entry<String, ArrayList<Long>> entry : ShopListener.this.hash.entrySet()) {
                    Bukkit.broadcastMessage(ChatColor.GREEN + "LOOPER: " + entry.getKey());
                    final String name = entry.getKey();
                    final ArrayList<Long> blocktimes = entry.getValue();
                    final ArrayList<Long> remove = new ArrayList<Long>();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Antall: " + entry.getValue().size());
                    for (final long time : blocktimes) {
                        if (time < System.currentTimeMillis() - 30000L) {
                            remove.add(time);
                        }
                    }
                    for (final long rem : remove) {
                        blocktimes.remove(rem);
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Antall etter: " + entry.getValue().size());
                    if (blocktimes.size() >= 1) {
                        Bukkit.broadcast(ChatColor.YELLOW + name + ChatColor.GRAY + " har minet " + ChatColor.YELLOW + blocktimes.size() + ChatColor.GRAY + " de siste 30 sekundene", "buildit.admin");
                    }
                }
            }
        }, 600L, 600L);
    }
}
