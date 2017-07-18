package info.nordbyen.survivalheaven.subplugins.commands.commands;

import java.util.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class AFK implements CommandExecutor
{
    public static List<String> hashmap;
    
    static {
        AFK.hashmap = new ArrayList<String>();
    }
    
    public AFK() {
        Bukkit.getPluginManager().registerEvents((Listener)new AFKListener(), (Plugin)SH.getPlugin());
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re en spiller.");
            return true;
        }
        if (!(sender instanceof Player)) {
            return false;
        }
        final String player = sender.getName();
        if (!AFK.hashmap.contains(player)) {
            AFK.hashmap.add(player);
            Bukkit.broadcastMessage(ChatColor.DARK_RED + player + ChatColor.RED + " er n\u00e5 AFK.");
            return true;
        }
        AFK.hashmap.remove(player);
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + player + ChatColor.GREEN + " er ikke lenger AFK.");
        return true;
    }
    
    public class AFKListener implements Listener
    {
        @EventHandler
        public void onMove(final PlayerMoveEvent e) {
            final Location from = e.getFrom();
            final Location to = e.getTo();
            if (from.getX() == to.getX()) {
                return;
            }
            if (from.getY() == to.getY()) {
                return;
            }
            if (from.getZ() == to.getZ()) {
                return;
            }
            if (from.getWorld().getName().equals(to.getWorld().getName())) {
                return;
            }
            AFK.hashmap.remove(e.getPlayer().getName());
        }
    }
}
