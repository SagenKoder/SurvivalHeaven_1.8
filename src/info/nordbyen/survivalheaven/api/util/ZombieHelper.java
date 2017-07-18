package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;

public class ZombieHelper
{
    public static boolean isBaby(final Zombie zombie) {
        final EntityZombie ent = ((CraftZombie)zombie).getHandle();
        return ent.isBaby();
    }
    
    public static boolean isVillager(final Zombie zombie) {
        final EntityZombie ent = ((CraftZombie)zombie).getHandle();
        return ent.isVillager();
    }
    
    public static void setBaby(final Zombie zombie, final boolean value) {
        final EntityZombie ent = ((CraftZombie)zombie).getHandle();
        ent.setBaby(value);
    }
    
    public static void setVillager(final Zombie zombie, final boolean value) {
        final EntityZombie ent = ((CraftZombie)zombie).getHandle();
        ent.setVillager(value);
    }
}
