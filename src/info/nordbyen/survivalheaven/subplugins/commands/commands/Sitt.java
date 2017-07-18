package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Sitt implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player && command.getName().equalsIgnoreCase("sitt") && Sender.hasPermission("sh.kick") && args.length == 1) {
            final Player p = Bukkit.getPlayer(args[0]);
            final Player tg = (Player)Sender;
            if (tg != p) {
                p.setPassenger((Entity)tg);
            }
            else {
                Sender.sendMessage(ChatColor.RED + "Fochoff");
            }
        }
        return false;
    }
}
