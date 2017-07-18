package info.nordbyen.survivalheaven.subplugins.title;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import java.lang.reflect.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

public class TitleAPI extends SubPlugin implements Listener
{
    public static void sendTabTitle(final Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);
        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());
        final PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        final IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        final IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        final PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try {
            final Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        finally {
            connection.sendPacket((Packet)headerPacket);
        }
        connection.sendPacket((Packet)headerPacket);
    }
    
    public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, String title, String subtitle) {
        final PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        final PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, (int)fadeIn, (int)stay, (int)fadeOut);
        connection.sendPacket((Packet)packetPlayOutTimes);
        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            final IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            final PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket((Packet)packetPlayOutSubTitle);
        }
        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            final IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            final PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket((Packet)packetPlayOutTitle);
        }
    }
    
    public TitleAPI(final String name) {
        super(name);
    }
    
    public void disable() {
    }
    
    public void enable() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this.getPlugin());
    }
    
    boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
