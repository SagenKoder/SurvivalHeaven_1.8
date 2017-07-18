package info.nordbyen.survivalheaven.subplugins.commands.commands;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.wand.*;

public class AdminStick implements CommandExecutor
{
    public AdminStickWand asw;
    public static List<String> hashmap;
    
    static {
        AdminStick.hashmap = new ArrayList<String>();
    }
    
    public AdminStick() {
        this.asw = null;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (this.asw == null) {
            this.asw = new AdminStickWand();
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bare spillere kan gj\u00f8re dette!");
        }
        final Player p = (Player)sender;
        if (p.hasPermission("sh.adminstick")) {
            final ItemStack item = new ItemStack(Material.STICK, 1);
            SH.getManager().getWandManager().createWand(item, this.asw, p);
        }
        return true;
    }
}
