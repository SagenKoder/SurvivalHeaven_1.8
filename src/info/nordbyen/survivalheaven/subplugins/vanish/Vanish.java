package info.nordbyen.survivalheaven.subplugins.vanish;

import info.nordbyen.survivalheaven.api.subplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class Vanish extends SubPlugin
{
    public static List<String> vanishedPlayers;
    
    static {
        Vanish.vanishedPlayers = new ArrayList<String>();
    }
    
    private static boolean listContainsString(final List<String> l, final String s) {
        for (final String ls : l) {
            if (ls.equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean getPlayerVanished(final String uuid) {
        return listContainsString(Vanish.vanishedPlayers, uuid);
    }
    
    public static void setPlayerVanished(final String uuid) {
    }
    
    public Vanish(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
        for (final String s : Vanish.vanishedPlayers) {
            final Player p = Bukkit.getPlayer(UUID.fromString(s));
            if (p == null) {
                continue;
            }
            p.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.YELLOW).append("En reload tvang deg til \u00e5 bli synelig...").toString());
        }
        Vanish.vanishedPlayers.clear();
        Vanish.vanishedPlayers = null;
    }
    
    @Override
    protected void enable() {
        Vanish.vanishedPlayers = new ArrayList<String>();
    }
    
    public static class VanishListener implements Listener
    {
        @EventHandler
        public void playerPickupItem() {
        }
        
        @EventHandler
        public void playerLeave(final PlayerQuitEvent e) {
            if (listContainsString(Vanish.vanishedPlayers, e.getPlayer().getUniqueId().toString())) {
                Vanish.vanishedPlayers.remove(e.getPlayer().getUniqueId().toString());
            }
        }
        
        public void playerJoin(final PlayerJoinEvent e) {
        }
    }
}
