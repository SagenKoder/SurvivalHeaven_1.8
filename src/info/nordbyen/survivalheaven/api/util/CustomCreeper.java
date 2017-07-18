package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import java.lang.reflect.*;

public class CustomCreeper
{
    public static int getFuse(final Creeper creeper) {
        final EntityCreeper entCreeper = ((CraftCreeper)creeper).getHandle();
        Field fuseF = null;
        try {
            fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e2) {
            e2.printStackTrace();
        }
        fuseF.setAccessible(true);
        int fuse = 0;
        try {
            fuse = fuseF.getInt(entCreeper);
        }
        catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
        return fuse;
    }
    
    public static int getRadius(final Creeper creeper) {
        final EntityCreeper entCreeper = ((CraftCreeper)creeper).getHandle();
        Field radiusF = null;
        try {
            radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e2) {
            e2.printStackTrace();
        }
        radiusF.setAccessible(true);
        int radius = 0;
        try {
            radius = radiusF.getInt(entCreeper);
        }
        catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
        return radius;
    }
    
    public static void setFuse(final Creeper creeper, final int fuse) {
        final EntityCreeper entCreeper = ((CraftCreeper)creeper).getHandle();
        Field fuseF = null;
        try {
            fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e2) {
            e2.printStackTrace();
        }
        fuseF.setAccessible(true);
        try {
            fuseF.setInt(entCreeper, fuse);
        }
        catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
    }
    
    public static void setRadius(final Creeper creeper, final int radius) {
        final EntityCreeper entCreeper = ((CraftCreeper)creeper).getHandle();
        Field radiusF = null;
        try {
            radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e2) {
            e2.printStackTrace();
        }
        radiusF.setAccessible(true);
        try {
            radiusF.setInt(entCreeper, radius);
        }
        catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
    }
}
