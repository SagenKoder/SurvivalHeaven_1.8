package info.nordbyen.survivalheaven.subplugins.chairs;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class ChairsListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getPlayer().getItemInHand().getTypeId() == 280 && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Block block = event.getClickedBlock();
            if (block.getType() == Material.WOOD_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en eikestol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.JUNGLE_WOOD_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en jungel stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.BIRCH_WOOD_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en birch stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.SPRUCE_WOOD_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en spruce stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.COBBLESTONE_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en cobblestone stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.BRICK_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en murstein stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.SMOOTH_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en smooth brick stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.NETHER_BRICK_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en nether brick stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if (block.getType() == Material.SANDSTONE_STAIRS) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(event.getPlayer().getName()).getRank() < RankType.DIAMANT.getId()) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re smaragd for \u00e5 sette deg i stoler!");
                    return;
                }
                player.sendMessage(ChatColor.BLUE + "Du satte deg i en sandstone stol.");
                player.getPlayer();
                final Arrow arrow = (Arrow)player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)player);
            }
            else if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getVehicle() != null && player.getVehicle() instanceof Arrow) {
                player.sendMessage(ChatColor.DARK_BLUE + "Du gikk ut av stolen din");
                player.getVehicle().remove();
            }
        }
    }
}
