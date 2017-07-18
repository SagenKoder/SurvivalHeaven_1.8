package info.nordbyen.survivalheaven.api.util;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import info.nordbyen.survivalheaven.subplugins.title.*;

public class FancyMessages
{
    public static void sendActionBar(final Player player, final String message) {
        final CraftPlayer p = (CraftPlayer)player;
        final IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        final PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        p.getHandle().playerConnection.sendPacket((Packet)ppoc);
    }
    
    public static void sendTabTitle(final Player player, final String header, final String footer) {
        TitleAPI.sendTabTitle(player, header, footer);
    }
    
    public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, final String title, final String subtitle) {
        TitleAPI.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }
}
