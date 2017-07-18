package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import java.lang.reflect.*;
import net.minecraft.server.v1_8_R3.*;

public class SkeletonHelper
{
    private static void changeIntoNormal(final Skeleton skeleton, final boolean giveRandomEnchantments) {
        final EntitySkeleton ent = ((CraftSkeleton)skeleton).getHandle();
        try {
            ent.setSkeletonType(0);
            final Method be = EntitySkeleton.class.getDeclaredMethod("bE", (Class<?>[])new Class[0]);
            be.setAccessible(true);
            be.invoke(ent, new Object[0]);
            if (giveRandomEnchantments) {
                final Method bf = EntityLiving.class.getDeclaredMethod("bF", (Class<?>[])new Class[0]);
                bf.setAccessible(true);
                bf.invoke(ent, new Object[0]);
            }
            final Field selector = EntitySkeleton.class.getDeclaredField("goalSelector");
            selector.setAccessible(true);
            final Field d = EntitySkeleton.class.getDeclaredField("d");
            d.setAccessible(true);
            final PathfinderGoalSelector goals = (PathfinderGoalSelector)selector.get(ent);
            goals.a(4, (PathfinderGoal)d.get(ent));
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    private static void changeIntoWither(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton)skeleton).getHandle();
        try {
            ent.setSkeletonType(1);
            final Field selector = EntitySkeleton.class.getDeclaredField("goalSelector");
            selector.setAccessible(true);
            final Field e = EntitySkeleton.class.getDeclaredField("e");
            e.setAccessible(true);
            final PathfinderGoalSelector goals = (PathfinderGoalSelector)selector.get(ent);
            goals.a(4, (PathfinderGoal)e.get(ent));
            ent.setEquipment(0, new ItemStack(Item.getById(272)));
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    public static Skeleton.SkeletonType getType(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton)skeleton).getHandle();
        return Skeleton.SkeletonType.getType(ent.getSkeletonType());
    }
    
    public static boolean isWither(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton)skeleton).getHandle();
        return ent.getSkeletonType() == 1;
    }
    
    public static void setType(final Skeleton skeleton, final Skeleton.SkeletonType type) {
        if (type == Skeleton.SkeletonType.NORMAL) {
            changeIntoNormal(skeleton, true);
        }
        else {
            changeIntoWither(skeleton);
        }
    }
}
