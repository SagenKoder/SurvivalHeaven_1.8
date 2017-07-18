package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import org.bukkit.block.*;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import info.nordbyen.survivalheaven.*;

public class BlockListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDispenserBreak(final BlockBreakEvent event) {
        if (event.isCancelled() || !(event.getBlock().getState() instanceof Dispenser) || !(event.getBlock().getState() instanceof Dropper)) {
            return;
        }
        Dispensers.getInstance();
        if (Dispensers.isDispenser(event.getBlock().getLocation())) {
            Dispensers.getInstance().getList("dispensers").remove(event.getBlock().getLocation().toString());
            Dispensers.getInstance().save();
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(final SignChangeEvent event) {
        if (event.isCancelled() || event.getPlayer() == null) {
            return;
        }
        if (event.getPlayer().hasPermission("infdispenser.signs")) {
            return;
        }
        if (event.getLines()[0].equalsIgnoreCase("[infdisp]")) {
            event.getPlayer().sendMessage(String.valueOf(SH.PREFIX) + "Du har ikke nok rettigheter til dette");
            event.setCancelled(true);
        }
    }
}
