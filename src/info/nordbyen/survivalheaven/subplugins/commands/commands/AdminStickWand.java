package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.wand.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.block.*;

public class AdminStickWand implements Wand
{
    public AdminStickWand() {
        SH.getManager().getWandManager().add(this);
    }
    
    @Override
    public boolean canCreate(final ItemStack itemStack, final Player player) {
        return true;
    }
    
    @Override
    public String getName() {
        return ChatColor.GOLD + "AdminStick";
    }
    
    @Override
    public void onLeftClick(final ItemStack itemStack, final Player player, final Block bockedClick, final BlockFace face) {
        player.sendMessage(ChatColor.GOLD + "Du venstreklikket :D");
    }
    
    @Override
    public void onRightClick(final ItemStack itemStack, final Player player, final Block bockedClick, final BlockFace face) {
        player.sendMessage(ChatColor.GOLD + "Du h\u00f8yreklikket D:");
    }
}
