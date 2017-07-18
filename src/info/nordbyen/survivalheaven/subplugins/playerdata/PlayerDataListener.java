package info.nordbyen.survivalheaven.subplugins.playerdata;

import org.bukkit.block.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.subplugins.commands.commands.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import java.sql.*;
import org.bukkit.event.*;
import info.nordbyen.survivalheaven.api.util.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.event.server.*;
import org.bukkit.scheduler.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.util.*;
import org.bukkit.event.player.*;

public class PlayerDataListener implements Listener
{
    BlockFace[] faces;
    ArrayList<Block> blocks;
    private ArrayList<Block> blocksFound;
    
    public PlayerDataListener() {
        this.faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };
        this.blocks = new ArrayList<Block>();
        this.blocksFound = new ArrayList<Block>();
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent e) throws SQLException {
        final Player p = e.getPlayer();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o.getName().equals(p.getName()) && !o.getUniqueId().toString().equals(p.getUniqueId().toString())) {
                o.kickPlayer("En bug oppsto. Det fantes to av deg :o");
                SH.warning("Fant en duplikat spiller: " + o.getName(), "Begge spiller objektene ble kicket....");
            }
        }
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString());
        if (pd != null) {
            pd.setLastlogin(new Date());
            pd.setName(p.getName());
            pd.setIp(p.getAddress().toString().replace("/", "").split(":")[0]);
        }
        else {
            SH.getManager().getPlayerDataManager().createPlayerData(p);
        }
        if (AFK.hashmap.contains(e.getPlayer().getName())) {
            AFK.hashmap.remove(e.getPlayer().getName());
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + e.getPlayer().getName() + ChatColor.GREEN + " er ikke lenger AFK.");
        }
        for (final Player h : Bukkit.getOnlinePlayers()) {
            if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(p.getName()).getRank() > 1 && h != e.getPlayer()) {
                final EntityPlayer ep = ((CraftPlayer)e.getPlayer()).getHandle();
                ep.setCustomName(String.valueOf(SH.getManager().getRankManager().getRank(p.getUniqueId().toString()).getColor()) + p.getName());
                ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutNamedEntitySpawn((EntityHuman)ep));
            }
        }
    }
    
    @EventHandler
    public void onJoin2(final PlayerJoinEvent e) {
        e.setJoinMessage((String)null);
        if (e.getPlayer().hasPlayedBefore()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " logget inn");
        }
        else {
            Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " logget inn for f\u00f8rste gang!");
            Bukkit.broadcastMessage(ChatColor.BLUE + "\u00d8nsk " + e.getPlayer().getName() + " velkommen");
        }
        FancyMessages.sendActionBar(e.getPlayer(), new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("VELKOMMEN TIL ").append(SH.NAME).toString());
        FancyMessages.sendTitle(e.getPlayer(), 10, 70, 40, ChatColor.GREEN + "Velkommen til " + SH.NAME, SH.MOTTO);
        if (SH.getManager().getRankManager().getRank(e.getPlayer().getUniqueId().toString()).getId() > RankType.ARKITEKT.getId()) {
            e.getPlayer().sendMessage(ChatColor.RED + "FINNER DU NOEN MANGLENDE FEILMELDINGER? SKRIV P\u00c5 FORUMET!!!");
        }
    }
    
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        if (e.getPlayer().getName().equalsIgnoreCase("CecilieL2003")) {
            final String[] words = { "fack", "fuck", "fu", "fuku", "pus", "s\u00f8t", "hater", "d\u00f8", "d\u00e5rlig", "sl\u00e5", "elsker", "l0lkj", "Svenniiss", "Svenn", "Alex" };
            String[] array;
            for (int length = (array = words).length, i = 0; i < length; ++i) {
                final String s = array[i];
                if (e.getMessage().toLowerCase().replace(" ", "").replace(".", "").replace(",", "").replace("-", "").replace("_", "").replace(":", "").contains(s.toLowerCase())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "FY!!!");
                }
            }
        }
        if (SH.mutedPlayers.contains(e.getPlayer().getName())) {
            FancyMessages.sendActionBar(e.getPlayer(), ChatColor.RED + "Du er mutet og kan ikke snakke");
            e.setCancelled(true);
        }
        if (AFK.hashmap.contains(e.getPlayer().getName())) {
            AFK.hashmap.remove(e.getPlayer().getName());
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + e.getPlayer().getName() + ChatColor.GREEN + " er ikke lenger AFK.");
        }
    }
    
    @EventHandler
    public void onPing(final ServerListPingEvent e) {
        e.setMotd(ChatColor.GOLD + "X--===[ " + ChatColor.RED + "Survival" + ChatColor.GRAY + "Heaven " + ChatColor.DARK_GREEN + "1.8" + ChatColor.GOLD + " ]===--X");
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause().equals((Object)EntityDamageEvent.DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
        }
        if (SH.fallprotected.contains(event.getEntity().getName())) {
            event.setCancelled(true);
        }
    }
    
    public int countOres(final Block seed, final Material type, final List<Block> counted) {
        this.blocks = new ArrayList<Block>();
        if (seed.getType() == type && !counted.contains(seed)) {
            counted.add(seed);
            int num = 0;
            BlockFace[] faces;
            for (int length = (faces = this.faces).length, i = 0; i < length; ++i) {
                final BlockFace face = faces[i];
                num += this.countOres(seed.getRelative(face), type, counted);
            }
            return num + 1;
        }
        return 0;
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.LAPIS_ORE) {
            if (this.blocksFound.contains(e.getBlock())) {
                this.blocksFound.remove(e.getBlock());
                return;
            }
            final int count = this.countOres(e.getBlock(), e.getBlock().getType(), this.blocksFound);
            new BukkitRunnable() {
                public void run() {
                    PlayerDataListener.this.blocksFound.removeAll(PlayerDataListener.this.blocks);
                }
            }.runTaskLater((Plugin)SH.getPlugin(), 1200L);
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()).getRank() > RankType.UTVIKLER.getId()) {
                    p.sendMessage(ChatColor.GOLD + "[StabAlarm] " + e.getPlayer().getName() + " fant " + count + " " + e.getBlock().getType().name());
                }
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[StabAlarm] " + e.getPlayer().getName() + " fant " + count + " " + e.getBlock().getType().name());
        }
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent e) {
        if (e.getReason().contains("Floating too long") || e.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(p.getName()).getRank() > 3) {
                    p.sendMessage(ChatColor.GOLD + "[StabAlarm] H\u00d8Y: " + ChatColor.RED + e.getPlayer().getName() + ChatColor.WHITE + " ble kicka for flymodus");
                }
            }
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString());
        if (pd != null) {
            pd.setGamemode(p.getGameMode().getValue());
            pd.setTimeplayed(pd.getTimeplayed() + new Date().getTime() - pd.getLastlogin().getTime());
        }
        else {
            SH.getManager().getPlayerDataManager().createPlayerData(p);
        }
    }
    
    @EventHandler
    public void onQuit2(final PlayerQuitEvent e) {
        e.setQuitMessage((String)null);
        Bukkit.broadcastMessage(ChatColor.RED + e.getPlayer().getName() + " logget av");
    }
    
    @EventHandler
    public void onKill(final PlayerDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }
        if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), PlayerDataManager.skull(e.getEntity().getName()));
        }
        e.setDeathMessage((String)null);
    }
    
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/pl") || args[0].equalsIgnoreCase("/plugins") || args[0].equalsIgnoreCase("/bukkit:plugins") || args[0].equalsIgnoreCase("/bukkit:pl")) {
            if (SH.getManager().getRankManager().getRank(e.getPlayer().getUniqueId().toString()).getId() > RankType.ADMINISTRATOR.getId()) {
                return;
            }
            final List<String> pl = new ArrayList<String>();
            pl.add("SurvivalHeaven-CORE");
            pl.add("WorldEdit");
            pl.add("VoxelSniper");
            pl.add("WorldGuard");
            String s = "Plugins (" + pl.size() + "): ";
            for (final String plugin : pl) {
                s = String.valueOf(s) + ChatColor.GREEN + plugin + ChatColor.WHITE + ", ";
            }
            s = s.substring(0, s.length() - 2);
            e.getPlayer().sendMessage(s);
            e.setCancelled(true);
        }
        else {
            args[0].equalsIgnoreCase("");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(final PlayerTeleportEvent e) {
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(final PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = e.getEntity();
        final IPlayerDataManager pdm = SH.getManager().getPlayerDataManager();
        final IPlayerData pd = pdm.getPlayerData(p.getUniqueId().toString());
        pd.setLastlocation(e.getEntity().getLocation());
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR && e.getPlayer().getPassenger() instanceof Player) {
            final Player p = (Player)e.getPlayer().getPassenger();
            e.getPlayer().eject();
            p.setVelocity(this.giveVektor(e.getPlayer().getLocation()).multiply(4));
            SH.fallprotected.add(p.getName());
        }
    }
    
    public Vector giveVektor(final Location loc) {
        final double pitch = (loc.getPitch() + 90.0f) * 3.141592653589793 / 180.0;
        final double yaw = (loc.getYaw() + 90.0f) * 3.141592653589793 / 180.0;
        final double x = Math.sin(pitch) * Math.cos(yaw);
        final double y = Math.sin(pitch) * Math.sin(yaw);
        final double z = Math.cos(pitch);
        final Vector vector = new Vector(x, z, y);
        return vector;
    }
    
    @EventHandler
    public void onSmash(final PlayerMoveEvent event) {
        if (event.getFrom().getBlockY() > event.getTo().getBlockY() && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            Bukkit.getServer().getScheduler().runTaskLater((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    SH.fallprotected.remove(event.getPlayer().getName());
                }
            }, 10L);
        }
    }
}
