package info.nordbyen.survivalheaven.subplugins.wand;

import org.bukkit.event.player.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.event.block.*;
import info.nordbyen.survivalheaven.api.wand.*;
import org.bukkit.event.*;

public class WandListener implements Listener
{
    @EventHandler
    public void onWandInteract(final PlayerInteractEvent event) {
        if (SH.getManager().getWandManager().isWand(event.getItem())) {
            final Wand wand = SH.getManager().getWandManager().search(event.getItem());
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                wand.onLeftClick(event.getItem(), event.getPlayer(), event.getClickedBlock(), event.getBlockFace());
            }
            else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                wand.onRightClick(event.getItem(), event.getPlayer(), event.getClickedBlock(), event.getBlockFace());
            }
        }
    }
}
