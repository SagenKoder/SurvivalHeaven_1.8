package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import java.util.*;
import org.bukkit.*;

public class FirstEncounter
{
    private static final List<String> waiting;
    
    static {
        waiting = new ArrayList<String>();
    }
    
    public static void addWaitingPlayer(final String uuid) {
        FirstEncounter.waiting.add(uuid);
    }
    
    public static List<String> getWaitingPlayers() {
        return FirstEncounter.waiting;
    }
    
    public static boolean isInside(final Location location, final Location min, final Location max) {
        final double xmin = Math.min(min.getX(), max.getX());
        final double xmax = Math.max(min.getX(), max.getX());
        final double ymin = Math.min(min.getY(), max.getY());
        final double ymax = Math.max(min.getY(), max.getY());
        final double zmin = Math.min(min.getZ(), max.getZ());
        final double zmax = Math.max(min.getZ(), max.getZ());
        if (location.getBlockX() <= xmax && location.getBlockX() >= xmin) {
            if (location.getBlockY() > ymax || location.getBlockY() < ymin) {
                return false;
            }
            if (location.getBlockZ() <= zmax && location.getBlockZ() >= zmin) {
                return true;
            }
        }
        return false;
    }
    
    public static void removeWaitingPlayer(final String uuid) {
        FirstEncounter.waiting.remove(uuid);
    }
}
