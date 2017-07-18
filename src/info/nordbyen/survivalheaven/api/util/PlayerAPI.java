package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.*;

public class PlayerAPI
{
    public boolean addHealth(final Player player, final int amount) {
        final double health = player.getHealth();
        if (health == 20.0) {
            player.setHealth(20.0);
            return true;
        }
        if (health > 20 - amount) {
            player.setHealth(20.0);
            return true;
        }
        if (health < 20 - amount) {
            player.setHealth(health + amount);
            return true;
        }
        return false;
    }
    
    public void award(final Player player, final Achievement achievement) {
        player.awardAchievement(achievement);
    }
    
    public void burn(final Player player) {
        player.setFireTicks(20);
    }
    
    public void burn(final Player player, final int ticks) {
        player.setFireTicks(ticks);
    }
    
    public void extinguish(final Player player) {
        player.setFireTicks(0);
    }
    
    public void kill(final Player player) {
        player.setHealth(0.0);
    }
    
    public boolean removeHealth(final Player player, final int amount) {
        final double health = player.getHealth();
        if (health > health - amount) {
            player.setHealth(health - amount);
            return true;
        }
        if (health < health - amount) {
            player.setHealth(0.0);
            return true;
        }
        return false;
    }
}
