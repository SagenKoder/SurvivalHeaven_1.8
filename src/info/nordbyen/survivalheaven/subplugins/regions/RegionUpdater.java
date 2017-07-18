package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.material.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.util.*;
import info.nordbyen.survivalheaven.api.regions.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.event.*;
import java.sql.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import java.util.*;
import org.bukkit.event.block.*;
import org.bukkit.craftbukkit.v1_8_R3.block.*;
import org.bukkit.block.*;

public class RegionUpdater extends SubPlugin
{
    private final HashMap<String, RegionData> playerRegions;
    
    public static boolean isDoor(final Material type) {
        return type == Material.IRON_DOOR || type == Material.WOODEN_DOOR || type == Material.TRAP_DOOR || type == Material.IRON_TRAPDOOR || type == Material.BIRCH_DOOR || type == Material.BIRCH_DOOR_ITEM || type == Material.SPRUCE_DOOR || type == Material.SPRUCE_DOOR_ITEM || type == Material.JUNGLE_DOOR || type == Material.JUNGLE_DOOR_ITEM || type == Material.ACACIA_DOOR || type == Material.ACACIA_DOOR_ITEM || type == Material.DARK_OAK_DOOR;
    }
    
    public void toggleDoor(Block b, final boolean toggled) {
        if (!isDoor(b.getType())) {
            return;
        }
        if (!isDoor(b.getRelative(BlockFace.UP).getType())) {
            b = b.getRelative(BlockFace.DOWN);
        }
        final Material type = b.getType();
        final Door door = (Door)type.getNewData(b.getData());
        if (toggled != door.isOpen()) {
            door.setOpen(toggled);
            final Block above = b.getRelative(BlockFace.UP);
            final Block below = b.getRelative(BlockFace.DOWN);
            if (isDoor(above.getType())) {
                b.setData(door.getData(), true);
                door.setTopHalf(true);
                above.setData(door.getData(), true);
            }
            else if (isDoor(below.getType())) {
                door.setTopHalf(false);
                below.setData(door.getData(), true);
                door.setTopHalf(true);
                b.setData(door.getData(), true);
            }
            b.getWorld().playEffect(b.getLocation(), Effect.DOOR_TOGGLE, 0);
        }
    }
    
    public RegionUpdater(final String name) {
        super(name);
        this.playerRegions = new HashMap<String, RegionData>();
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        this.repeatingTask();
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), -232.0, 0.0, -6071.0), "Nord-byen", 150.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 145.0, 0.0, 6234.0), "S\u00f8r-byen", 150.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 6251.0, 0.0, 757.0), "\u00d8st-byen", 200.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), -5774.0, 0.0, 95.0), "Vest-byen", 150.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 140.0, 0.0, 89.0), "Spawn Sentrum", 230.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 140.0, 0.0, 89.0), "Spawn Utkant", 330.0, 95, false, false, true, false, true, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 140.0, 0.0, 89.0), "Survival", 6000.0, 90, false, true, true, false, true, false));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 140.0, 0.0, 89.0), "Villmark", 2.147483647E9, -2147483548, false, true, true, false, true, false));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), -1631.0, 0.0, 146.0), "BlockLag", 400.0, 1000, false, true, true, false, true, false));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden"), 1949.0, 0.0, 1296.0), "Spleef Arena", 150.0, 100, false, false, false, true, false, true));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("creative"), 3.0, 0.0, -112.0), "Creative Spawn", 250.0, 100, false, false, false, true, true, false));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("creative"), 3.0, 0.0, -112.0), "Creative Villmark", 2.147483647E9, 50, false, true, false, true, true, false));
        SH.getManager().getRegionManager().addRegion(RegionData.createRegion(new Location(Bukkit.getWorld("NyVerden_the_end"), 15.0, 0.0, 4.0), "Enden", 2.147483647E9, 50, true, true, true, false, false, false));
        SH.getManager().getRegionManager().addRegion(RegionSquareData.createRegion("PvP Spawn", Integer.MAX_VALUE, false, false, false, true, false, false, Bukkit.getWorld("pvp2"), 3401, -813, 3524, -936));
        SH.getManager().getRegionManager().addRegion(RegionSquareData.createRegion("PvP villmark", 100, true, true, true, false, false, false, Bukkit.getWorld("pvp2"), Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE));
        Bukkit.getPluginManager().registerEvents((Listener)new RegionUpdaterListener(), (Plugin)this.getPlugin());
        new RegionTeleportCommand();
    }
    
    private void repeatingTask() {
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask((Plugin)this.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    RegionData region = SH.getManager().getRegionManager().getRegionAt(o.getLocation());
                    if (region == null) {
                        region = new DefaultRegion();
                    }
                    if (RegionUpdater.this.playerRegions.get(o.getUniqueId().toString()) == null) {
                        RegionUpdater.this.playerRegions.put(o.getUniqueId().toString(), new DefaultRegion());
                    }
                    if (!region.getName().equals(RegionUpdater.this.playerRegions.get(o.getUniqueId().toString()).getName())) {
                        RegionUpdater.this.sendRegionName(o, region);
                        SH.getManager().getRankManager().updateNames();
                    }
                    RegionUpdater.this.playerRegions.put(o.getUniqueId().toString(), region);
                }
            }
        }, 1L, 1L);
    }
    
    public void sendRegionName(final Player p, final RegionData region) {
        SH.getManager().getRankManager().updateNames();
        if (region == null) {
            return;
        }
        if (region.getName().equals("BlockLag")) {
            FancyMessages.sendTitle(p, 20, 50, 20, ChatColor.GREEN + "---[ " + region.getName() + " ]---", ChatColor.GRAY + "Velkommen til " + ChatColor.GREEN + "BlockLag city" + ChatColor.GRAY + ". En by av spillere for spillere");
            return;
        }
        final boolean pvp = region.isPvp();
        FancyMessages.sendTitle(p, 20, 50, 20, ChatColor.GREEN + "---[ " + region.getName() + " ]---", pvp ? (ChatColor.RED + "Her er det PvP!") : (ChatColor.BLUE + "Her er du trygg fra andre spillere"));
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(e.getBlock().getLocation());
        if (rd.isBreakable()) {
            return;
        }
        final IRankManager rm = SH.getManager().getRankManager();
        final RankType rank = rm.getRank(e.getPlayer().getUniqueId().toString());
        if (rank == RankType.ADMINISTRATOR || rank == RankType.MODERATOR || rank == RankType.ARKITEKT) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(e.getBlock().getLocation());
        if (rd.isBreakable()) {
            return;
        }
        final IRankManager rm = SH.getManager().getRankManager();
        final RankType rank = rm.getRank(e.getPlayer().getUniqueId().toString());
        if (rank == RankType.ADMINISTRATOR || rank == RankType.MODERATOR || rank == RankType.ARKITEKT) {
            return;
        }
        e.setCancelled(true);
    }
    
    public class RegionUpdaterListener implements Listener
    {
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
        
        @EventHandler
        public void onDamage(final EntityDamageEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            final Player p = (Player)e.getEntity();
            final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(p.getLocation());
            if (rd == null) {
                return;
            }
            if (!rd.isInvincible()) {
                return;
            }
            e.setCancelled(true);
        }
        
        @EventHandler
        public void onInteract(final PlayerInteractEvent e) {
            if (e.getClickedBlock() == null) {
                return;
            }
            if (this.isShop(e.getClickedBlock().getLocation())) {
                return;
            }
            final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(e.getClickedBlock().getLocation());
            if (rd == null) {
                return;
            }
            if (rd.isBreakable()) {
                return;
            }
            final IRankManager rm = SH.getManager().getRankManager();
            final RankType rank = rm.getRank(e.getPlayer().getUniqueId().toString());
            if (rank != RankType.ADMINISTRATOR && rank != RankType.MODERATOR && rank != RankType.ARKITEKT && e.getClickedBlock().getType() != Material.ENDER_CHEST) {
                e.setCancelled(true);
            }
        }
        
        @EventHandler
        public void onMonsterSpawn(final CreatureSpawnEvent e) {
            if (e.getEntityType() == EntityType.BLAZE || e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.SKELETON || e.getEntityType() == EntityType.SLIME || e.getEntityType() == EntityType.SPIDER || e.getEntityType() == EntityType.CREEPER || e.getEntityType() == EntityType.ENDERMAN || e.getEntityType() == EntityType.GHAST || e.getEntityType() == EntityType.WITHER || e.getEntityType() == EntityType.WITCH || e.getEntityType() == EntityType.GUARDIAN) {
                final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(e.getLocation());
                if (rd == null) {
                    return;
                }
                if (!rd.isMonsters()) {
                    e.setCancelled(true);
                }
            }
        }
        
        @EventHandler
        public void onPvP(final EntityDamageByEntityEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            if (!(e.getDamager() instanceof Player)) {
                return;
            }
            final Player s = (Player)e.getEntity();
            final Player o = (Player)e.getDamager();
            final IRegionData rd1 = SH.getManager().getRegionManager().getRegionAt(o.getLocation());
            final IRegionData rd2 = SH.getManager().getRegionManager().getRegionAt(s.getLocation());
            if (rd1 == null && rd2 == null) {
                return;
            }
            if (rd1.isPvp() && rd2.isPvp()) {
                return;
            }
            e.setCancelled(true);
            FancyMessages.sendActionBar(o, ChatColor.RED + "PvP er av i denne regionen");
        }
        
        @EventHandler
        public void onQuit(final PlayerQuitEvent e) {
            RegionUpdater.this.playerRegions.remove(e.getPlayer().getUniqueId().toString());
        }
        
        @EventHandler
        public void onHunger(final FoodLevelChangeEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            final Player p = (Player)e.getEntity();
            if (!SH.getManager().getRegionManager().getRegionAt(p.getLocation()).isInvincible()) {
                return;
            }
            if (e.getFoodLevel() > p.getFoodLevel()) {
                return;
            }
            e.setCancelled(true);
        }
        
        @EventHandler
        public void onBowPvP(final EntityDamageByEntityEvent e) {
            final Entity damager = e.getDamager();
            final Entity target = e.getEntity();
            if (damager instanceof Arrow && target instanceof Player) {
                final Arrow arrow = (Arrow)damager;
                final Player player = (Player)target;
                if (arrow.getShooter() instanceof Player) {
                    final Player shooter = (Player)arrow.getShooter();
                    final RegionData region = SH.getManager().getRegionManager().getRegionAt(player.getLocation());
                    if (!region.isPvp()) {
                        e.setCancelled(true);
                        FancyMessages.sendActionBar(shooter, ChatColor.RED + "Det er ikke PvP her!");
                    }
                }
            }
        }
        
        @EventHandler
        public void onExplode(final EntityExplodeEvent e) {
            final Location loc = e.getLocation();
            final List<Block> blocks = (List<Block>)e.blockList();
            if (loc.getWorld().getName().equalsIgnoreCase("creative")) {
                blocks.clear();
                return;
            }
            if (!SH.getManager().getRegionManager().getRegionAt(loc).isBp()) {
                return;
            }
            blocks.clear();
        }
        
        @EventHandler
        public void onPlayerDoorOpen(final PlayerInteractEvent event) {
            final Action action = event.getAction();
            final Block b = event.getClickedBlock();
            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) {
                if (!SH.getManager().getRegionManager().getRegionAt(b.getLocation()).isAuto_door()) {
                    return;
                }
                if (b.getType() == Material.IRON_DOOR || b.getType() == Material.WOODEN_DOOR || b.getType() == Material.TRAP_DOOR || b.getType() == Material.BIRCH_DOOR || b.getType() == Material.BIRCH_DOOR_ITEM || b.getType() == Material.SPRUCE_DOOR || b.getType() == Material.SPRUCE_DOOR_ITEM || b.getType() == Material.JUNGLE_DOOR || b.getType() == Material.JUNGLE_DOOR_ITEM || b.getType() == Material.ACACIA_DOOR || b.getType() == Material.ACACIA_DOOR_ITEM || b.getType() == Material.DARK_OAK_DOOR) {
                    final BlockState blockState = b.getState();
                    if (blockState.getData() instanceof Door) {
                        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        scheduler.scheduleSyncDelayedTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                RegionUpdater.this.toggleDoor(b, false);
                            }
                        }, 200L);
                    }
                }
            }
        }
    }
}
