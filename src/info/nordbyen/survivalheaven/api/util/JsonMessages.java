package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import java.util.*;

public class JsonMessages
{
    public static PacketPlayOutChat createPacketPlayOutChat(final String s) {
        return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(s));
    }
    
    public static void SendJsonMessage(final Player p, final String s) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)createPacketPlayOutChat(s));
    }
    
    public static void SendPlayerListJsonMessage(final Player[] players, final String s) {
        final PacketPlayOutChat a = createPacketPlayOutChat(s);
        for (final Player p : players) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)a);
        }
    }
    
    public static void SendAllJsonMessage(final String s) {
        final PacketPlayOutChat a = createPacketPlayOutChat(s);
        for (final Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)a);
        }
    }
}
