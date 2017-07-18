package info.nordbyen.survivalheaven.subplugins.bossbar;

import java.util.*;
import java.lang.reflect.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class FDragon
{
    private static Constructor<?> packetPlayOutSpawnEntityLiving;
    private static Constructor<?> entityEnderdragon;
    private static Method setLocation;
    private static Method setCustomName;
    private static Method setHealth;
    private static Method setInvisible;
    private static Method getWorldHandle;
    private static Method getPlayerHandle;
    private static Field playerConnection;
    private static Method sendPacket;
    private static Method getDatawatcher;
    private static Method a;
    private static Field d;
    public static Map<String, Object> playerDragons;
    public static Map<String, String> playerTextDragon;
    
    static {
        FDragon.playerDragons = new HashMap<String, Object>();
        FDragon.playerTextDragon = new HashMap<String, String>();
        try {
            FDragon.packetPlayOutSpawnEntityLiving = getMCClass("PacketPlayOutSpawnEntityLiving").getConstructor(getMCClass("EntityLiving"));
            FDragon.entityEnderdragon = getMCClass("EntityEnderDragon").getConstructor(getMCClass("World"));
            FDragon.setLocation = getMCClass("EntityEnderDragon").getMethod("setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
            FDragon.setCustomName = getMCClass("EntityEnderDragon").getMethod("setCustomName", String.class);
            FDragon.setHealth = getMCClass("EntityEnderDragon").getMethod("setHealth", Float.TYPE);
            FDragon.setInvisible = getMCClass("EntityEnderDragon").getMethod("setInvisible", Boolean.TYPE);
            FDragon.getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle", (Class<?>[])new Class[0]);
            FDragon.getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle", (Class<?>[])new Class[0]);
            FDragon.playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
            FDragon.sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));
            FDragon.getDatawatcher = getMCClass("EntityEnderDragon").getMethod("getDataWatcher", (Class<?>[])new Class[0]);
            FDragon.a = getMCClass("DataWatcher").getMethod("a", Integer.TYPE, Object.class);
            (FDragon.d = getMCClass("DataWatcher").getDeclaredField("d")).setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void changeWatcher(final Object nms_entity, final String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Object nms_watcher = FDragon.getDatawatcher.invoke(nms_entity, new Object[0]);
        final Map<?, ?> map = (Map<?, ?>)FDragon.d.get(nms_watcher);
        map.remove(10);
        FDragon.a.invoke(nms_watcher, 10, text);
    }
    
    public static String getCardinalDirection(final Player player) {
        double rotation = (player.getLocation().getYaw() - 180.0f) % 360.0f;
        if (rotation < 0.0) {
            rotation += 360.0;
        }
        if (0.0 <= rotation && rotation < 22.5) {
            return "N";
        }
        if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        }
        if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        }
        if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        }
        if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        }
        if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        }
        if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        }
        if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        }
        if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        }
        return null;
    }
    
    private static Class<?> getCraftClass(final String name) throws ClassNotFoundException {
        final String version = String.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]) + ".";
        final String className = "org.bukkit.craftbukkit." + version + name;
        return Class.forName(className);
    }
    
    public static Object getEnderDragon(final Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        if (FDragon.playerDragons.containsKey(p.getName())) {
            return FDragon.playerDragons.get(p.getName());
        }
        final Object nms_world = FDragon.getWorldHandle.invoke(p.getWorld(), new Object[0]);
        FDragon.playerDragons.put(p.getName(), FDragon.entityEnderdragon.newInstance(nms_world));
        return getEnderDragon(p);
    }
    
    private static Class<?> getMCClass(final String name) throws ClassNotFoundException {
        final String version = String.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]) + ".";
        final String className = "net.minecraft.server." + version + name;
        return Class.forName(className);
    }
    
    public static Location getPlayerLoc(final Player p) {
        final Location loc = p.getLocation();
        final String cardinalDirection;
        switch (cardinalDirection = getCardinalDirection(p)) {
            case "E": {
                loc.add(150.0, 0.0, 0.0);
                break;
            }
            case "N": {
                loc.add(0.0, 0.0, -150.0);
                break;
            }
            case "S": {
                loc.add(0.0, 0.0, 150.0);
                break;
            }
            case "W": {
                loc.add(-150.0, 0.0, 0.0);
                break;
            }
            case "NE": {
                loc.add(150.0, 0.0, -150.0);
                break;
            }
            case "NW": {
                loc.add(-150.0, 0.0, -150.0);
                break;
            }
            case "SE": {
                loc.add(150.0, 0.0, 150.0);
                break;
            }
            case "SW": {
                loc.add(-150.0, 0.0, 150.0);
                break;
            }
            default:
                break;
        }
        return loc;
    }
    
    public static void removeBossBar(final Player p) {
        FDragon.playerTextDragon.remove(p.getName());
        try {
            final Object nms_dragon = getEnderDragon(p);
            FDragon.setLocation.invoke(nms_dragon, p.getLocation().getX(), -5000, p.getLocation().getZ(), 0.0f, 0.0f);
            FDragon.setCustomName.invoke(nms_dragon, " ");
            FDragon.setHealth.invoke(nms_dragon, 0);
            FDragon.setInvisible.invoke(nms_dragon, true);
            changeWatcher(nms_dragon, " ");
            final Object nms_packet = FDragon.packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
            final Object nms_player = FDragon.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FDragon.playerConnection.get(nms_player);
            FDragon.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void removehorligneD(final Player p) {
        FDragon.playerDragons.remove(p.getName());
        FDragon.playerTextDragon.remove(p.getName());
    }
    
    public static void setBossBar(final Player p, final String text, final float vie) {
        FDragon.playerTextDragon.put(p.getName(), text);
        try {
            final Object nms_dragon = getEnderDragon(p);
            FDragon.setLocation.invoke(nms_dragon, getPlayerLoc(p).getX(), getPlayerLoc(p).getY() + 800.0, getPlayerLoc(p).getZ(), 0.0f, 0.0f);
            FDragon.setCustomName.invoke(nms_dragon, text);
            FDragon.setHealth.invoke(nms_dragon, vie);
            FDragon.setInvisible.invoke(nms_dragon, true);
            changeWatcher(nms_dragon, text);
            final Object nms_packet = FDragon.packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
            final Object nms_player = FDragon.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FDragon.playerConnection.get(nms_player);
            FDragon.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setBossBartext(final Player p, final String text) {
        FDragon.playerTextDragon.put(p.getName(), text);
        try {
            final Object nms_dragon = getEnderDragon(p);
            FDragon.setLocation.invoke(nms_dragon, getPlayerLoc(p).getX(), getPlayerLoc(p).getY() + 800.0, getPlayerLoc(p).getZ(), 0.0f, 0.0f);
            FDragon.setCustomName.invoke(nms_dragon, text);
            FDragon.setHealth.invoke(nms_dragon, 200);
            FDragon.setInvisible.invoke(nms_dragon, true);
            changeWatcher(nms_dragon, text);
            final Object nms_packet = FDragon.packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
            final Object nms_player = FDragon.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FDragon.playerConnection.get(nms_player);
            FDragon.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
