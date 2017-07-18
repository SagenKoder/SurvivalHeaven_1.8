package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class Killall implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("killall") && sender.hasPermission("sh.killall")) {
            for (final Entity element : ((Player)sender).getWorld().getEntities()) {
                if (element.getType() != EntityType.PLAYER && element.getType() != EntityType.ITEM_FRAME && element.getType() != EntityType.MINECART) {
                    element.remove();
                }
            }
            sender.sendMessage("Du fjernet " + ChatColor.YELLOW + " ALLE" + ChatColor.RESET + " monsterene og dyrene i denne verdenen.");
            return true;
        }
        return false;
    }
}
