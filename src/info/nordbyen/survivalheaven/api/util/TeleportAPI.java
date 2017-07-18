package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class TeleportAPI
{
    public Location newLocation(final Player target) {
        final Location location = target.getLocation();
        return location;
    }
    
    public Location newLocation(final World world, final double x, final double y, final double z) {
        final Location location = new Location(world, x, y, z);
        return location;
    }
    
    public Location newLocation(final World world, final double x, final double y, final double z, final float pitch, final float yaw) {
        final Location location = new Location(world, x, y, z, pitch, yaw);
        return location;
    }
    
    public void teleport(final Player player, final double x, final double y, final double z) {
        final Location location = new Location(player.getWorld(), x, y, z);
        player.teleport(location);
    }
    
    public void teleport(final Player player, final Location location) {
        player.teleport(location);
    }
    
    public void teleport(final Player player, final Player target) {
        player.teleport((Entity)target);
    }
    
    public void teleport(final Player player, final World world, final double x, final double y, final double z) {
        final Location location = new Location(world, x, y, z);
        player.teleport(location);
    }
    
    public void teleport(final Player player, final World world, final double x, final double y, final double z, final float pitch, final float yaw) {
        final Location location = new Location(world, x, y, z, pitch, yaw);
        player.teleport(location);
    }
    
    public void teleportAll(final Location location) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(location);
        }
    }
    
    public void teleportAll(final Player[] players, final Location location) {
        for (final Player player : players) {
            if (player.isOnline()) {
                player.teleport(location);
            }
        }
    }
    
    public void teleportAll(final String[] players, final Location location) {
        for (final String target : players) {
            final Player player = Bukkit.getPlayer(target);
            if (player.isOnline()) {
                player.teleport(location);
            }
        }
    }
}
