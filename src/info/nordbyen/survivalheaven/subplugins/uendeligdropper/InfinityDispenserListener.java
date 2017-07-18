package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.event.player.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.entity.*;

public class InfinityDispenserListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDispenserIgniteBlock(final BlockIgniteEvent event) {
        if (event.isCancelled() || event.getIgnitingBlock() == null) {
            return;
        }
        if (event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            return;
        }
        if (!(event.getIgnitingBlock().getState() instanceof Dispenser)) {
            return;
        }
        final Block block = event.getIgnitingBlock();
        if (block.getType() == Material.DISPENSER && (Dispensers.isDispenser(block.getLocation()) || Util.getSign(block))) {
            final Dispenser disp = (Dispenser)block.getState();
            disp.getInventory().remove(Material.FLINT_AND_STEEL);
            disp.getInventory().addItem(new ItemStack[] { new ItemStack(Material.FLINT_AND_STEEL) });
            disp.update();
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDispenserUseItem(final BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getBlock().getType() != Material.DISPENSER && event.getBlock().getType() != Material.DROPPER) {
            return;
        }
        final Block block = event.getBlock();
        if (block.getType() == Material.DISPENSER) {
            if (Dispensers.isDispenser(block.getLocation()) || Util.getSign(block)) {
                final Dispenser disp = (Dispenser)block.getState();
                disp.getInventory().addItem(new ItemStack[] { event.getItem() });
                disp.update();
            }
        }
        else {
            try {
                if (Dispensers.isDispenser(block.getLocation()) || Util.getSign(block)) {
                    final Dropper drop = (Dropper)block.getState();
                    drop.getInventory().addItem(new ItemStack[] { event.getItem() });
                    drop.update();
                }
            }
            catch (ClassCastException ex) {}
        }
    }
    
    @EventHandler
    public void onDispenserInteract(final PlayerInteractEvent e) {
        final Block b = e.getClickedBlock();
        if (b == null) {
            return;
        }
        if (b.getType() != Material.DISPENSER && b.getType() != Material.DROPPER) {
            return;
        }
        if (!Dispensers.isDispenser(b.getLocation()) && !Util.getSign(b)) {
            return;
        }
        final Player p = e.getPlayer();
        final RankType rank = SH.getManager().getRankManager().getRank(p.getUniqueId().toString());
        if (rank.getId() >= RankType.MODERATOR.getId()) {
            return;
        }
        e.setCancelled(true);
        FancyMessages.sendActionBar(p, ChatColor.RED + "Dette er en UendeligDropper");
    }
}
