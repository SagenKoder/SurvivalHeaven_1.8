package info.nordbyen.survivalheaven.subplugins.ligg;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.event.*;
import com.comphenix.protocol.injector.*;
import com.comphenix.protocol.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import com.comphenix.protocol.reflect.*;
import com.comphenix.protocol.events.*;
import org.bukkit.command.*;
import java.lang.reflect.*;
import org.bukkit.*;
import java.util.*;

public class Ligg extends SubPlugin implements Listener
{
    private static Ligg instance;
    private PacketConstructor useBedConstructor;
    private PacketConstructor relativeMoveConstructor;
    private ProtocolManager manager;
    private final Map<String, Integer> offsetY;
    
    public static Ligg getInstance() {
        return Ligg.instance;
    }
    
    public Ligg(final String name) {
        super(name);
        this.offsetY = new HashMap<String, Integer>();
    }
    
    public void disable() {
    }
    
    public void enable() {
        Ligg.instance = this;
        LiggCommand.initCommand();
        (this.manager = ProtocolLibrary.getProtocolManager()).addPacketListener((PacketListener)new PacketAdapter(this.getPlugin(), ConnectionSide.SERVER_SIDE, new Integer[] { 34 }) {
            public void onPacketSending(final PacketEvent event) {
                final PacketContainer packet = event.getPacket();
                final Player receiver = event.getPlayer();
                try {
                    final Entity target = (Entity)packet.getEntityModifier(receiver.getWorld()).read(0);
                    final int offset = Ligg.this.getOffsetY(target);
                    if (offset != 0) {
                        final StructureModifier<Integer> ints = (StructureModifier<Integer>)packet.getSpecificModifier((Class)Integer.TYPE);
                        ints.write(2, (Object)((int)ints.read(2) + offset));
                    }
                }
                catch (FieldAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private int getOffsetY(final Entity target) {
        if (target instanceof Player) {
            final String name = ((Player)target).getName();
            final Integer result = this.offsetY.get(name);
            if (result != null) {
                return result;
            }
        }
        return 0;
    }
    
    public void performAction(final CommandSender sender) {
        sender.sendMessage("Cannot perform command for console!");
    }
    
    public void performAction(final Player player) {
        final Location loc = player.getLocation();
        if (this.useBedConstructor == null) {
            this.useBedConstructor = this.manager.createPacketConstructor(17, new Object[] { player, 0, 0, 0, 0 });
        }
        if (this.relativeMoveConstructor == null) {
            this.relativeMoveConstructor = this.manager.createPacketConstructor(31, new Object[] { 0, (byte)0, (byte)0, (byte)0 });
        }
        try {
            final int OFFSET_Y = 2;
            final PacketContainer sleepPacket = this.useBedConstructor.createPacket(new Object[] { player, 0, loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ() });
            final PacketContainer movePacket = this.relativeMoveConstructor.createPacket(new Object[] { player.getEntityId(), (byte)0, (byte)64, (byte)0 });
            for (final Player other : this.getPlugin().getServer().getOnlinePlayers()) {
                if (!other.equals(player)) {
                    this.manager.sendServerPacket(other, sleepPacket);
                    this.manager.sendServerPacket(other, movePacket);
                }
            }
            this.offsetY.put(player.getName(), 2);
        }
        catch (FieldAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e2) {
            e2.printStackTrace();
        }
    }
}
