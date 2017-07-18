package info.nordbyen.survivalheaven.subplugins.DenyPlayerMode;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;

public class DenyPlayerMode extends SubPlugin
{
    public DenyPlayerMode(final String name) {
        super(name);
    }
    
    public void disable() {
    }
    
    public void enable() {
        Bukkit.getPluginManager().registerEvents((Listener)new DenyPlayerModeListener(), (Plugin)this.getPlugin());
    }
    
    public boolean isDenied(final Player p) {
        final RankType rank = SH.getManager().getRankManager().getRank(p.getUniqueId().toString());
        return rank == RankType.BANNED;
    }
    
    class DenyPlayerModeListener implements Listener
    {
        private final String denymessage;
        
        DenyPlayerModeListener() {
            this.denymessage = new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append("Du er bannet og har ikke lov til dette!").toString();
        }
        
        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
        public void onChat(final AsyncPlayerChatEvent e) {
            if (!DenyPlayerMode.this.isDenied(e.getPlayer())) {
                return;
            }
            FancyMessages.sendActionBar(e.getPlayer(), this.denymessage);
            e.setCancelled(true);
            e.setMessage((String)null);
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onCommand(final PlayerCommandPreprocessEvent e) {
            if (!DenyPlayerMode.this.isDenied(e.getPlayer())) {
                return;
            }
            FancyMessages.sendActionBar(e.getPlayer(), this.denymessage);
            e.setCancelled(true);
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onEntityDamage(final EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player) {
                final Player p = (Player)e.getDamager();
                if (!DenyPlayerMode.this.isDenied(p)) {
                    return;
                }
                FancyMessages.sendActionBar(p, this.denymessage);
                e.setCancelled(true);
            }
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onInteract(final PlayerInteractEvent e) {
            if (!DenyPlayerMode.this.isDenied(e.getPlayer())) {
                return;
            }
            FancyMessages.sendActionBar(e.getPlayer(), this.denymessage);
            e.setCancelled(true);
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onPlace(final BlockPlaceEvent e) {
            if (!DenyPlayerMode.this.isDenied(e.getPlayer())) {
                return;
            }
            FancyMessages.sendActionBar(e.getPlayer(), this.denymessage);
            e.setCancelled(true);
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onTestEntityDamage(final EntityDamageEvent e) {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player)e.getEntity();
                if (!DenyPlayerMode.this.isDenied(p)) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }
}
