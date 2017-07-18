package info.nordbyen.survivalheaven.subplugins.bossbar;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class BossbarAPI extends SubPlugin implements Listener
{
    public static Plugin plugin;
    public static Map<Player, String> playerdragonbartask;
    public static Map<Player, Float> healthdragonbartask;
    public static Map<Player, Integer> cooldownsdragonbar;
    public static Map<Player, Integer> starttimerdragonbar;
    public static Map<Player, String> playerwitherbartask;
    public static Map<Player, Float> healthwitherbartask;
    public static Map<Player, Integer> cooldownswitherbar;
    public static Map<Player, Integer> starttimerwitherbar;
    
    static {
        BossbarAPI.playerdragonbartask = new HashMap<Player, String>();
        BossbarAPI.healthdragonbartask = new HashMap<Player, Float>();
        BossbarAPI.cooldownsdragonbar = new HashMap<Player, Integer>();
        BossbarAPI.starttimerdragonbar = new HashMap<Player, Integer>();
        BossbarAPI.playerwitherbartask = new HashMap<Player, String>();
        BossbarAPI.healthwitherbartask = new HashMap<Player, Float>();
        BossbarAPI.cooldownswitherbar = new HashMap<Player, Integer>();
        BossbarAPI.starttimerwitherbar = new HashMap<Player, Integer>();
    }
    
    public static Plugin getInstance() {
        return BossbarAPI.plugin;
    }
    
    public static String getMessage(final Player p) {
        if (BossbarAPI.playerdragonbartask.containsKey(p)) {
            return BossbarAPI.playerdragonbartask.get(p);
        }
        return " ";
    }
    
    public static String getMessageDragon(final Player p) {
        if (BossbarAPI.playerdragonbartask.containsKey(p)) {
            return BossbarAPI.playerdragonbartask.get(p);
        }
        return " ";
    }
    
    public static String getMessageWither(final Player p) {
        if (BossbarAPI.playerwitherbartask.containsKey(p)) {
            return BossbarAPI.playerwitherbartask.get(p);
        }
        return " ";
    }
    
    public static boolean hasBar(final Player p) {
        if (McVersion(p)) {
            return BossbarAPI.playerwitherbartask.containsKey(p) && BossbarAPI.playerdragonbartask.containsKey(p);
        }
        return BossbarAPI.playerdragonbartask.get(p) != null;
    }
    
    public static boolean hasBarDragon(final Player p) {
        return BossbarAPI.playerdragonbartask.get(p) != null;
    }
    
    public static boolean hasBarWither(final Player p) {
        return BossbarAPI.playerwitherbartask.get(p) != null;
    }
    
    public static boolean McVersion(final Player p) {
        return true;
    }
    
    public static void removeBar(final Player p) {
        if (McVersion(p)) {
            BossbarAPI.playerwitherbartask.remove(p);
            BossbarAPI.healthwitherbartask.remove(p);
            BossbarAPI.cooldownswitherbar.remove(p);
            BossbarAPI.starttimerwitherbar.remove(p);
            FWither.removeBossBar(p);
        }
        BossbarAPI.playerdragonbartask.remove(p);
        BossbarAPI.healthdragonbartask.remove(p);
        BossbarAPI.cooldownsdragonbar.remove(p);
        BossbarAPI.starttimerdragonbar.remove(p);
        FDragon.removeBossBar(p);
    }
    
    public static void removeBarDragon(final Player p) {
        BossbarAPI.playerdragonbartask.remove(p);
        BossbarAPI.healthdragonbartask.remove(p);
        BossbarAPI.cooldownsdragonbar.remove(p);
        BossbarAPI.starttimerdragonbar.remove(p);
        FDragon.removeBossBar(p);
    }
    
    public static void removeBarWither(final Player p) {
        BossbarAPI.playerwitherbartask.remove(p);
        BossbarAPI.healthwitherbartask.remove(p);
        BossbarAPI.cooldownswitherbar.remove(p);
        BossbarAPI.starttimerwitherbar.remove(p);
        FWither.removeBossBar(p);
    }
    
    public static void setBar(final Player p, final String text) {
        if (McVersion(p)) {
            BossbarAPI.playerwitherbartask.put(p, text);
            FWither.setBossBartext(p, text);
        }
        BossbarAPI.playerdragonbartask.put(p, text);
        FDragon.setBossBartext(p, text);
    }
    
    public static void setBarDragon(final Player p, final String text) {
        BossbarAPI.playerdragonbartask.put(p, text);
        FDragon.setBossBartext(p, text);
    }
    
    public static void setBarDragonHealth(final Player p, String text, float health) {
        if (health <= 0.0f || health > 100.0f) {
            health = 100.0f;
            text = "health must be between 1 and 100 it's a %";
        }
        BossbarAPI.playerdragonbartask.put(p, text);
        BossbarAPI.healthdragonbartask.put(p, health / 100.0f * 200.0f);
        FDragon.setBossBar(p, text, health);
    }
    
    public static void setBarDragonTimer(final Player p, final String text, final int timer) {
        BossbarAPI.playerdragonbartask.put(p, text);
        BossbarAPI.cooldownsdragonbar.put(p, timer);
        if (!BossbarAPI.starttimerdragonbar.containsKey(p)) {
            BossbarAPI.starttimerdragonbar.put(p, timer);
        }
        final int unite = Math.round(200 / BossbarAPI.starttimerdragonbar.get(p));
        FDragon.setBossBar(p, text, unite * timer);
    }
    
    public static void setBarHealth(final Player p, String text, float health) {
        if (health <= 0.0f || health > 100.0f) {
            health = 100.0f;
            text = "health must be between 1 and 100 it's a %";
        }
        if (McVersion(p)) {
            BossbarAPI.playerwitherbartask.put(p, text);
            BossbarAPI.healthwitherbartask.put(p, health / 100.0f * 300.0f);
            FWither.setBossBar(p, text, health);
        }
        BossbarAPI.playerdragonbartask.put(p, text);
        BossbarAPI.healthdragonbartask.put(p, health / 100.0f * 200.0f);
        FDragon.setBossBar(p, text, health);
    }
    
    public static void setBarTimer(final Player p, final String text, final int timer) {
        if (McVersion(p)) {
            BossbarAPI.playerwitherbartask.put(p, text);
            BossbarAPI.cooldownswitherbar.put(p, timer);
            if (!BossbarAPI.starttimerwitherbar.containsKey(p)) {
                BossbarAPI.starttimerwitherbar.put(p, timer);
            }
            final int unite = Math.round(300 / BossbarAPI.starttimerwitherbar.get(p));
            FWither.setBossBar(p, text, unite * timer);
        }
        BossbarAPI.playerdragonbartask.put(p, text);
        BossbarAPI.cooldownsdragonbar.put(p, timer);
        if (!BossbarAPI.starttimerdragonbar.containsKey(p)) {
            BossbarAPI.starttimerdragonbar.put(p, timer);
        }
        final int unite2 = Math.round(200 / BossbarAPI.starttimerdragonbar.get(p));
        FDragon.setBossBar(p, text, unite2 * timer);
    }
    
    public static void setBarWither(final Player p, final String text) {
        BossbarAPI.playerwitherbartask.put(p, text);
        FWither.setBossBartext(p, text);
    }
    
    public static void setBarWitherHealth(final Player p, String text, float health) {
        if (health <= 0.0f || health > 100.0f) {
            health = 100.0f;
            text = "health must be between 1 and 100 it's a %";
        }
        BossbarAPI.playerwitherbartask.put(p, text);
        BossbarAPI.healthwitherbartask.put(p, health / 100.0f * 300.0f);
        FWither.setBossBar(p, text, health);
    }
    
    public static void setBarWitherTimer(final Player p, final String text, final int timer) {
        BossbarAPI.playerwitherbartask.put(p, text);
        BossbarAPI.cooldownswitherbar.put(p, timer);
        if (!BossbarAPI.starttimerwitherbar.containsKey(p)) {
            BossbarAPI.starttimerwitherbar.put(p, timer);
        }
        final int unite = Math.round(300 / BossbarAPI.starttimerwitherbar.get(p));
        FWither.setBossBar(p, text, unite * timer);
    }
    
    public BossbarAPI(final String name) {
        super(name);
    }
    
    public void disable() {
    }
    
    public void DragonBarTask() {
        new BukkitRunnable() {
            public void run() {
                for (final Player p : BossbarAPI.plugin.getServer().getOnlinePlayers()) {
                    if (!BossbarAPI.cooldownsdragonbar.containsKey(p)) {
                        if (BossbarAPI.playerdragonbartask.containsKey(p) && !BossbarAPI.healthdragonbartask.containsKey(p)) {
                            BossbarAPI.setBarDragon(p, BossbarAPI.playerdragonbartask.get(p));
                        }
                        else if (BossbarAPI.playerdragonbartask.containsKey(p) && BossbarAPI.healthdragonbartask.containsKey(p)) {
                            BossbarAPI.setBarDragonHealth(p, BossbarAPI.playerdragonbartask.get(p), BossbarAPI.healthdragonbartask.get(p));
                        }
                    }
                    if (!BossbarAPI.cooldownswitherbar.containsKey(p)) {
                        if (BossbarAPI.playerwitherbartask.containsKey(p) && !BossbarAPI.healthwitherbartask.containsKey(p)) {
                            BossbarAPI.setBarWither(p, BossbarAPI.playerwitherbartask.get(p));
                        }
                        else {
                            if (!BossbarAPI.playerwitherbartask.containsKey(p) || !BossbarAPI.healthwitherbartask.containsKey(p)) {
                                continue;
                            }
                            BossbarAPI.setBarWitherHealth(p, BossbarAPI.playerwitherbartask.get(p), BossbarAPI.healthwitherbartask.get(p));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)this.getPlugin(), 0L, 40L);
        new BukkitRunnable() {
            public void run() {
                for (final Player p : BossbarAPI.plugin.getServer().getOnlinePlayers()) {
                    if (BossbarAPI.cooldownsdragonbar.containsKey(p)) {
                        if (BossbarAPI.cooldownsdragonbar.get(p) > 0) {
                            BossbarAPI.cooldownsdragonbar.put(p, BossbarAPI.cooldownsdragonbar.get(p) - 1);
                            BossbarAPI.setBarDragonTimer(p, BossbarAPI.playerdragonbartask.get(p), BossbarAPI.cooldownsdragonbar.get(p));
                        }
                        else {
                            BossbarAPI.removeBarDragon(p);
                        }
                    }
                    if (BossbarAPI.cooldownswitherbar.containsKey(p)) {
                        if (BossbarAPI.cooldownswitherbar.get(p) > 0) {
                            BossbarAPI.cooldownswitherbar.put(p, BossbarAPI.cooldownswitherbar.get(p) - 1);
                            BossbarAPI.setBarWitherTimer(p, BossbarAPI.playerwitherbartask.get(p), BossbarAPI.cooldownswitherbar.get(p));
                        }
                        else {
                            BossbarAPI.removeBarWither(p);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)this.getPlugin(), 0L, 20L);
    }
    
    public void enable() {
        this.getPlugin().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.getPlugin());
        BossbarAPI.plugin = (Plugin)this.getPlugin();
        this.DragonBarTask();
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PlayerKick(final PlayerKickEvent event) {
        final Player p = event.getPlayer();
        removeBar(p);
        FDragon.removehorligneD(p);
        FWither.removehorligneW(p);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PlayerQuit(final PlayerQuitEvent event) {
        final Player p = event.getPlayer();
        removeBar(p);
        FDragon.removehorligneD(p);
        FWither.removehorligneW(p);
    }
}
