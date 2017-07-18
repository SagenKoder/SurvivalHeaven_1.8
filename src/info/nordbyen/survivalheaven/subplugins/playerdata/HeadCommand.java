package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.util.*;

public class HeadCommand extends AbstractCommand
{
    public HeadCommand(final String command, final String usage, final String description, final List<String> aliases) {
        super(command, usage, description, aliases);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (!((Player)sender).hasPermission("sh.head") && !((Player)sender).isOp()) {
                sender.sendMessage("Du har ikke perm!");
                return true;
            }
            if (args.length == 0) {
                ((Player)sender).getInventory().addItem(new ItemStack[] { PlayerDataManager.skull(sender.getName()) });
                sender.sendMessage("Du fikk ditt hode!");
                return true;
            }
            if (args.length == 1) {
                ((Player)sender).getInventory().addItem(new ItemStack[] { PlayerDataManager.skull(args[0]) });
                sender.sendMessage("Du fikk et hode!");
                return true;
            }
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final List<String> players = new ArrayList<String>();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            players.add(o.getName());
        }
        return players;
    }
}
