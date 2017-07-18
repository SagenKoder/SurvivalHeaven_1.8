package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class S implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        final Player p = (Player)Sender;
        if (command.getName().equalsIgnoreCase("s")) {
            if (Sender instanceof Player) {
                final Location pos = p.getWorld().getHighestBlockAt(p.getLocation()).getLocation();
                p.teleport(pos);
                p.sendMessage(ChatColor.GREEN + "Poff!");
            }
            else {
                Sender.sendMessage(ChatColor.RED + "Du er ikke en in-game spiller");
            }
        }
        return true;
    }
}
