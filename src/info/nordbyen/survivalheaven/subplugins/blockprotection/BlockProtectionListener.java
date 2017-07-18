package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.*;
import java.sql.*;
import info.nordbyen.survivalheaven.api.util.*;
import info.nordbyen.survivalheaven.subplugins.regions.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.event.*;
import org.bukkit.event.hanging.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import info.nordbyen.survivalheaven.api.blockdata.*;
import org.bukkit.*;

public class BlockProtectionListener implements Listener
{
    public boolean isDoor(final Block b) {
        final Material m = b.getType();
        switch (m) {
            case WOODEN_DOOR:
            case IRON_DOOR_BLOCK:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isShop(final Location loc) {
        try {
            final PreparedStatement stmt = SH.getManager().getMysqlManager().getConnection().prepareStatement("SELECT id FROM shops WHERE x_location=? AND y_location=? AND z_location=?");
            stmt.setInt(1, loc.getBlockX());
            stmt.setInt(2, loc.getBlockY());
            stmt.setInt(3, loc.getBlockZ());
            Throwable t = null;
            try {
                final ResultSet rs = stmt.executeQuery();
                try {
                    return rs.first();
                }
                finally {
                    if (rs != null) {
                        rs.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable t2;
                    t = t2;
                }
                else {
                    final Throwable t2;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(final BlockBreakEvent e) {
        Block b = e.getBlock();
        final Player p = e.getPlayer();
        if (this.isShop(b.getLocation())) {
            return;
        }
        if (this.isDoor(b) && this.isDoor(b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0)))) {
            b = b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0));
        }
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(b.getLocation());
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, b)) {
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, b)) {
            return;
        }
        if (!region.isBp()) {
            return;
        }
        if (e.getPlayer().hasPermission("sh.blockbreak") && e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) {
            return;
        }
        if (b.getType() != Material.AIR && b.getType() != Material.WATER && b.getType() != Material.LAVA) {
            final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(b);
            if (pd != null) {
                final String uuid = pd.getUUID();
                if (!uuid.equals(p.getUniqueId().toString())) {
                    final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                    if (!canBreak) {
                        FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        Block b = e.getClickedBlock();
        final Player p = e.getPlayer();
        if (b == null) {
            return;
        }
        if (this.isShop(b.getLocation())) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() == Material.WATCH && e.getAction() == Action.RIGHT_CLICK_BLOCK && SH.getManager().getBlockManager().getBlockOwner(b) != null) {
            e.getPlayer().sendMessage(ChatColor.GREEN + SH.getManager().getBlockManager().getBlockOwner(b).getName() + " eier denne blokken");
        }
        if (this.isDoor(b) && this.isDoor(b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0)))) {
            b = b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0));
        }
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(b.getLocation());
        if (e.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET && !e.getPlayer().hasPermission("sh.lava") && !e.getPlayer().isOp() && region.isBp()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke plassere lava i survival. Sp\u00f8r stab");
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, b)) {
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, b)) {
            return;
        }
        if (e.getAction() == Action.PHYSICAL && (e.getClickedBlock().getType() == Material.CROPS || e.getClickedBlock().getType() == Material.POTATO || e.getClickedBlock().getType() == Material.SOIL)) {
            e.setCancelled(true);
            return;
        }
        if (b.getType() == Material.CHEST || b.getType() == Material.FURNACE || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.DARK_OAK_DOOR || b.getType() == Material.DARK_OAK_DOOR_ITEM || b.getType() == Material.ACACIA_DOOR || b.getType() == Material.ACACIA_DOOR_ITEM || b.getType() == Material.ACACIA_FENCE_GATE || b.getType() == Material.DARK_OAK_FENCE_GATE || b.getType() == Material.ANVIL || b.getType() == Material.ARMOR_STAND || b.getType() == Material.BEACON || b.getType() == Material.BIRCH_DOOR || b.getType() == Material.BIRCH_DOOR_ITEM || b.getType() == Material.BIRCH_FENCE_GATE || b.getType() == Material.WOODEN_DOOR || b.getType() == Material.WOOD_DOOR || b.getType() == Material.WOOD_BUTTON || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.TRAP_DOOR || b.getType() == Material.TNT || b.getType() == Material.STORAGE_MINECART || b.getType() == Material.STONE_BUTTON || b.getType() == Material.SPRUCE_FENCE_GATE || b.getType() == Material.SPRUCE_DOOR || b.getType() == Material.SPRUCE_DOOR_ITEM || b.getType() == Material.SNOW || b.getType() == Material.LEVER || b.getType() == Material.JUNGLE_DOOR || b.getType() == Material.JUNGLE_DOOR_ITEM || b.getType() == Material.JUNGLE_FENCE_GATE || b.getType() == Material.BREWING_STAND || b.getType() == Material.BURNING_FURNACE || b.getType() == Material.DIODE_BLOCK_OFF || b.getType() == Material.DIODE_BLOCK_ON || b.getType() == Material.DISPENSER || b.getType() == Material.DROPPER || b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.ENDER_CHEST || b.getType() == Material.FENCE_GATE || b.getType() == Material.HOPPER || b.getType() == Material.IRON_DOOR || b.getType() == Material.IRON_DOOR_BLOCK || b.getType() == Material.IRON_TRAPDOOR || b.getType() == Material.WOOD_PLATE || b.getType() == Material.STONE_PLATE || b.getType() == Material.ITEM_FRAME) {
            if ((region.getName().equalsIgnoreCase("Spawn Sentrum") || region.getName().equalsIgnoreCase("Spawn Utkant")) && (b.getType() == Material.WOOD_BUTTON || b.getType() == Material.STONE_BUTTON)) {
                return;
            }
            final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(b);
            if (pd != null) {
                final String uuid = pd.getUUID();
                if (!uuid.equals(p.getUniqueId().toString())) {
                    final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                    if (!canBreak) {
                        FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onItemBreak(final HangingBreakByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.ITEM_FRAME) {
            return;
        }
        if (!(e.getEntity() instanceof ItemFrame)) {
            return;
        }
        if (!(e.getRemover() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getRemover();
        final ItemFrame frame = (ItemFrame)e.getEntity();
        final Block block = frame.getLocation().getWorld().getBlockAt(frame.getLocation());
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(block.getLocation());
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, block)) {
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, block)) {
            return;
        }
        if (!region.isBp()) {
            return;
        }
        final Block back = block.getRelative(frame.getAttachedFace());
        final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(back);
        if (pd != null) {
            final String uuid = pd.getUUID();
            if (!uuid.equals(p.getUniqueId().toString())) {
                final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                if (!canBreak) {
                    FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemFrameInteract(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() != EntityType.ITEM_FRAME) {
            return;
        }
        if (!(e.getRightClicked() instanceof ItemFrame)) {
            return;
        }
        final Player p = e.getPlayer();
        final ItemFrame frame = (ItemFrame)e.getRightClicked();
        final Block block = frame.getLocation().getWorld().getBlockAt(frame.getLocation());
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(block.getLocation());
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, block)) {
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, block)) {
            return;
        }
        if (!region.isBp()) {
            return;
        }
        final Block back = block.getRelative(frame.getAttachedFace());
        final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(back);
        if (pd != null) {
            final String uuid = pd.getUUID();
            if (!uuid.equals(p.getUniqueId().toString())) {
                final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                if (!canBreak) {
                    FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemRemove(final EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.ITEM_FRAME) {
            return;
        }
        if (!(e.getEntity() instanceof ItemFrame)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getDamager();
        final ItemFrame frame = (ItemFrame)e.getEntity();
        final Block block = frame.getLocation().getWorld().getBlockAt(frame.getLocation());
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(block.getLocation());
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, block)) {
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, block)) {
            return;
        }
        if (!region.isBp()) {
            return;
        }
        final Block back = block.getRelative(frame.getAttachedFace());
        final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(back);
        if (pd != null) {
            final String uuid = pd.getUUID();
            if (!uuid.equals(p.getUniqueId().toString())) {
                final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                if (!canBreak) {
                    FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        Block b = e.getBlock();
        final Player p = e.getPlayer();
        if (this.isDoor(b) && this.isDoor(b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0)))) {
            b = b.getWorld().getBlockAt(b.getLocation().add(0.0, -1.0, 0.0));
        }
        final RegionData region = SH.getManager().getRegionManager().getRegionAt(b.getLocation());
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().canBreak(p, b)) {
            SH.debug("p=" + p, "b=" + b, "kan ikke \u00f8delegge");
            e.setCancelled(true);
            FancyMessages.sendActionBar(p, ChatColor.RED + "Bare medlemmer i byen kan endre blokker her");
            return;
        }
        if (region.getName().equals("BlockLag") && !BlockLagManager.getInstance().useBlockProtection(p, b)) {
            return;
        }
        if (!region.isBp()) {
            return;
        }
        if ((b.getType() == Material.LAVA || b.getType() == Material.LAVA_BUCKET) && !p.hasPermission("sh.lava") && !p.isOp()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke plassere lava i survival. Sp\u00f8r stab");
        }
        if (b.getType() == Material.HOPPER) {
            final Location aboveL = b.getLocation();
            aboveL.add(0.0, 1.0, 0.0);
            final Block above = aboveL.getWorld().getBlockAt(aboveL);
            final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(above);
            if (pd != null) {
                final String uuid = pd.getUUID();
                if (!uuid.equals(p.getUniqueId().toString())) {
                    final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                    if (!canBreak) {
                        FancyMessages.sendActionBar(p, ChatColor.RED + "Du kan ikke plassere hoppere under " + pd.getName() + " sine blokker");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        else if (b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.CHEST) {
            final BlockFace[] blockfaces = { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
            BlockFace[] array;
            for (int length = (array = blockfaces).length, i = 0; i < length; ++i) {
                final BlockFace bf = array[i];
                final Block bs = b.getRelative(bf);
                if (b.getType() == bs.getType()) {
                    final IPlayerData pd2 = SH.getManager().getBlockManager().getBlockOwner(bs);
                    if (pd2 != null) {
                        final String uuid2 = pd2.getUUID();
                        if (!uuid2.equals(p.getUniqueId().toString())) {
                            final boolean canBreak2 = SH.getManager().getFriendManager().isFriends(pd2, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                            if (!canBreak2) {
                                FancyMessages.sendActionBar(p, ChatColor.RED + "Du kan ikke plassere kister ved siden av " + pd2.getName() + " sine kister");
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
        final Location belL = b.getLocation();
        belL.add(0.0, -1.0, 0.0);
        final Block bel = belL.getWorld().getBlockAt(belL);
        if (bel.getType() == Material.CHEST || bel.getType() == Material.TRAPPED_CHEST) {
            final IPlayerData pd = SH.getManager().getBlockManager().getBlockOwner(bel);
            if (pd != null) {
                final String uuid = pd.getUUID();
                if (!uuid.equals(p.getUniqueId().toString())) {
                    final boolean canBreak = SH.getManager().getFriendManager().isFriends(pd, SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()));
                    if (!canBreak) {
                        FancyMessages.sendActionBar(p, ChatColor.RED + "Du kan ikke plassere blokker over " + pd.getName() + " sine kister");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        BlockPlacedType type = BlockPlacedType.SURVIVAL;
        if (p.getGameMode().equals((Object)GameMode.CREATIVE)) {
            type = BlockPlacedType.CREATIVE;
        }
        SH.getManager().getBlockManager().setBlockOwner(b, p, type);
    }
}
