package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.plugin.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import java.util.*;

public class FuckCommand extends AbstractCommand
{
    public FuckCommand(final String command) {
        super(command);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final IRankManager prm = SH.getManager().getRankManager();
        if (sender instanceof Player && prm.getRank(((Player)sender).getUniqueId().toString()).getId() < RankType.MODERATOR.getId()) {
            sender.sendMessage(ChatColor.RED + "Bare MODERATORER kan gj\u00f8re dette!");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/fuck <melding>");
            return true;
        }
        final String prefix = ChatColor.DARK_RED + "[" + sender.getName() + "] " + ChatColor.RED;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            sb.append(String.valueOf(args[i]) + " ");
        }
        final String msg = sb.toString();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            FancyMessages.sendActionBar(o, String.valueOf(prefix) + msg);
            FancyMessages.sendTitle(o, 20, 80, 20, prefix, ChatColor.RED + msg);
            final ArrayList<Location> locations = this.getCircle(o.getEyeLocation(), 2.0, 100);
            o.getLocation().getWorld().spigot().strikeLightning(o.getLocation().add(100.0, 0.0, 100.0), false);
            for (final Location l : locations) {
                l.getWorld().playEffect(l, Effect.SMOKE, 8);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    o.getLocation().getWorld().spigot().strikeLightning(o.getLocation().add(100.0, 0.0, 100.0), false);
                    for (final Location l : locations) {
                        l.getWorld().playEffect(l, Effect.SMOKE, 8);
                    }
                }
            }, 20L);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    o.getLocation().getWorld().spigot().strikeLightning(o.getLocation().add(100.0, 0.0, 100.0), false);
                    for (final Location l : locations) {
                        l.getWorld().playEffect(l, Effect.SMOKE, 8);
                    }
                }
            }, 60L);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    o.getLocation().getWorld().spigot().strikeLightning(o.getLocation().add(100.0, 0.0, 100.0), false);
                    for (final Location l : locations) {
                        l.getWorld().playEffect(l, Effect.SMOKE, 8);
                    }
                }
            }, 80L);
        }
        return true;
    }
    
    public ArrayList<Location> getCircle(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() + radius * Math.cos(angle);
            final double z = center.getZ() + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
