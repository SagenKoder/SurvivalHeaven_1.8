package info.nordbyen.survivalheaven.subplugins.serverutil.listeners;

import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class ServerUtilsListener implements Listener
{
    @EventHandler
    public void onSignChange(final SignChangeEvent e) {
        if (e.getPlayer().hasPermission("sh.colorsign")) {
            final String[] lines = e.getLines();
            for (int i = 0; i < lines.length; ++i) {
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i]));
            }
        }
    }
    
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().startsWith("me") || e.getMessage().toLowerCase().startsWith("/me")) {
            e.getPlayer().sendMessage(ChatColor.RED + "Du har kommet til en kommando som ikke er i bruk");
            e.setCancelled(true);
        }
    }
}
