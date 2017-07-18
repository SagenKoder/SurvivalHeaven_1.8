package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class WB implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String CommandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (p.hasPermission("sh.wb") && command.getName().equalsIgnoreCase("wb")) {
                if (args.length == 0) {
                    p.openWorkbench(p.getLocation(), true);
                }
                else {
                    p.sendMessage(ChatColor.RED + "/wb");
                }
            }
        }
        return true;
    }
}
