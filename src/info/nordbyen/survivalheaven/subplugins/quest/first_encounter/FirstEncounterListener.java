package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import org.bukkit.event.player.*;
import info.nordbyen.survivalheaven.subplugins.quest.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class FirstEncounterListener implements Listener
{
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.getName().equals("l0lkj") && FirstEncounter.isInside(e.getTo(), FirstEncounterConfig.getDoor1_1(), FirstEncounterConfig.getDoor1_2())) {
            e.setCancelled(true);
            if (Godta_Command.players.containsKey(p.getUniqueId().toString())) {
                return;
            }
            p.sendMessage(ChatColor.RED + "Er du sikker p\u00e5 at du t\u00f8r \u00e5 g\u00e5 inn her? skriv /godta for \u00e5 g\u00e5 inn.");
            Godta_Command.players.put(p.getUniqueId().toString(), new AcceptHandler());
        }
    }
}
