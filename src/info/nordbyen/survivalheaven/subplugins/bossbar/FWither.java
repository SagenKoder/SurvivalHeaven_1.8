package info.nordbyen.survivalheaven.subplugins.bossbar;

import java.util.*;
import java.lang.reflect.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.concurrent.*;

public class FWither
{
    private static Constructor<?> packetPlayOutSpawnEntityLiving;
    private static Constructor<?> entityEntityWither;
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
    private static Map<String, Object> playerWithers;
    private static Map<String, Object> playerWithers2;
    private static Map<String, String> playerTextWither;
    
    static {
        FWither.playerWithers = new HashMap<String, Object>();
        FWither.playerWithers2 = new HashMap<String, Object>();
        FWither.playerTextWither = new HashMap<String, String>();
        try {
            FWither.packetPlayOutSpawnEntityLiving = getMCClass("PacketPlayOutSpawnEntityLiving").getConstructor(getMCClass("EntityLiving"));
            FWither.entityEntityWither = getMCClass("EntityWither").getConstructor(getMCClass("World"));
            FWither.setLocation = getMCClass("EntityWither").getMethod("setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
            FWither.setCustomName = getMCClass("EntityWither").getMethod("setCustomName", String.class);
            FWither.setHealth = getMCClass("EntityWither").getMethod("setHealth", Float.TYPE);
            FWither.setInvisible = getMCClass("EntityWither").getMethod("setInvisible", Boolean.TYPE);
            FWither.getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle", (Class<?>[])new Class[0]);
            FWither.getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle", (Class<?>[])new Class[0]);
            FWither.playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
            FWither.sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));
            FWither.getDatawatcher = getMCClass("EntityWither").getMethod("getDataWatcher", (Class<?>[])new Class[0]);
            FWither.a = getMCClass("DataWatcher").getMethod("a", Integer.TYPE, Object.class);
            (FWither.d = getMCClass("DataWatcher").getDeclaredField("d")).setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void changeWatcher(final Object nms_entity, final String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Object nms_watcher = FWither.getDatawatcher.invoke(nms_entity, new Object[0]);
        final Map<?, ?> map = (Map<?, ?>)FWither.d.get(nms_watcher);
        map.remove(10);
        FWither.a.invoke(nms_watcher, 10, text);
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
        if (337.5 <= rotation && rotation <= 360.0) {
            return "N";
        }
        return "N";
    }
    
    private static Class<?> getCraftClass(final String name) throws ClassNotFoundException {
        final String version = String.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]) + ".";
        final String className = "org.bukkit.craftbukkit." + version + name;
        return Class.forName(className);
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
                loc.add(50.0, 0.0, 0.0);
                break;
            }
            case "N": {
                loc.add(0.0, 0.0, -50.0);
                break;
            }
            case "S": {
                loc.add(0.0, 0.0, 50.0);
                break;
            }
            case "W": {
                loc.add(-50.0, 0.0, 0.0);
                break;
            }
            case "NE": {
                loc.add(50.0, 0.0, -50.0);
                break;
            }
            case "NW": {
                loc.add(-50.0, 0.0, -50.0);
                break;
            }
            case "SE": {
                loc.add(50.0, 0.0, 50.0);
                break;
            }
            case "SW": {
                loc.add(-50.0, 0.0, 50.0);
                break;
            }
            default:
                break;
        }
        return loc;
    }
    
    public static Object getWither(final Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        if (FWither.playerWithers.containsKey(p.getName())) {
            return FWither.playerWithers.get(p.getName());
        }
        final Object nms_world = FWither.getWorldHandle.invoke(p.getWorld(), new Object[0]);
        FWither.playerWithers.put(p.getName(), FWither.entityEntityWither.newInstance(nms_world));
        return getWither(p);
    }
    
    public static Object getWither2(final Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        if (FWither.playerWithers2.containsKey(p.getName())) {
            return FWither.playerWithers2.get(p.getName());
        }
        final Object nms_world = FWither.getWorldHandle.invoke(p.getWorld(), new Object[0]);
        FWither.playerWithers2.put(p.getName(), FWither.entityEntityWither.newInstance(nms_world));
        return getWither2(p);
    }
    
    public static void removeBossBar(final Player p) {
        FWither.playerTextWither.remove(p.getName());
        try {
            final Object nms_wither = getWither(p);
            FWither.setLocation.invoke(nms_wither, p.getLocation().getX(), -5000, p.getLocation().getZ(), 0.0f, 0.0f);
            FWither.setCustomName.invoke(nms_wither, " ");
            FWither.setHealth.invoke(nms_wither, 0);
            FWither.setInvisible.invoke(nms_wither, true);
            changeWatcher(nms_wither, " ");
            final Object nms_packet = FWither.packetPlayOutSpawnEntityLiving.newInstance(nms_wither);
            final Object nms_player = FWither.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FWither.playerConnection.get(nms_player);
            FWither.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void removehorligneW(final Player p) {
        FWither.playerWithers.remove(p.getName());
        FWither.playerTextWither.remove(p.getName());
    }
    
    public static void setBossBar(final Player p, final String text, final float vie) {
        FWither.playerTextWither.put(p.getName(), text);
        final int xr = ThreadLocalRandom.current().nextInt(0, 2);
        final int xr2 = ThreadLocalRandom.current().nextInt(0, 2);
        try {
            final Object nms_wither = getWither(p);
            FWither.setLocation.invoke(nms_wither, getPlayerLoc(p).getX() + xr, getPlayerLoc(p).getY() - 3.0, getPlayerLoc(p).getZ() + xr2, 0.0f, 0.0f);
            FWither.setCustomName.invoke(nms_wither, text);
            FWither.setHealth.invoke(nms_wither, vie);
            FWither.setInvisible.invoke(nms_wither, true);
            changeWatcher(nms_wither, text);
            final Object nms_packet = FWither.packetPlayOutSpawnEntityLiving.newInstance(nms_wither);
            final Object nms_player = FWither.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FWither.playerConnection.get(nms_player);
            FWither.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setBossBartext(final Player p, final String text) {
        FWither.playerTextWither.put(p.getName(), text);
        final int xr = ThreadLocalRandom.current().nextInt(-3, 3);
        final int xr2 = ThreadLocalRandom.current().nextInt(-3, 3);
        try {
            final Object nms_wither = getWither(p);
            FWither.setLocation.invoke(nms_wither, getPlayerLoc(p).getX() + xr, getPlayerLoc(p).getY() - 3.0, getPlayerLoc(p).getZ() + xr2, 0.0f, 0.0f);
            FWither.setCustomName.invoke(nms_wither, text);
            FWither.setHealth.invoke(nms_wither, 300);
            FWither.setInvisible.invoke(nms_wither, true);
            changeWatcher(nms_wither, text);
            final Object nms_packet = FWither.packetPlayOutSpawnEntityLiving.newInstance(nms_wither);
            final Object nms_player = FWither.getPlayerHandle.invoke(p, new Object[0]);
            final Object nms_connection = FWither.playerConnection.get(nms_player);
            FWither.sendPacket.invoke(nms_connection, nms_packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
