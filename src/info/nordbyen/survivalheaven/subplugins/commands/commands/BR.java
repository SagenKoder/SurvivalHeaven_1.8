package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class BR implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player && args.length > 0) {
            final StringBuffer me = new StringBuffer();
            for (int i = 0; i < args.length; ++i) {
                me.append(String.valueOf(args[i]) + " ");
            }
            if (command.getName().equalsIgnoreCase("bug")) {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("sh.kick")) {
                        if (p != null) {
                            p.sendMessage(ChatColor.RED + "[Bug] " + Sender.getName() + ": " + (Object)me);
                        }
                        else {
                            p.sendMessage(ChatColor.RED + "Det er ingen i staben p\u00e5");
                        }
                    }
                }
            }
        }
        return false;
    }
}
