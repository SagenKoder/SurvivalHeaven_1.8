package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Sudo implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (args.length > 1 && command.getName().equalsIgnoreCase("sudo") && Sender.hasPermission("sh.kick")) {
            final Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                Sender.sendMessage(ChatColor.RED + "Spilleren er ikke p\u00e5");
            }
            else {
                final StringBuffer me = new StringBuffer();
                for (int i = 1; i < args.length; ++i) {
                    me.append(String.valueOf(args[i]) + " ");
                }
                p.performCommand(me.toString());
            }
        }
        return false;
    }
}
