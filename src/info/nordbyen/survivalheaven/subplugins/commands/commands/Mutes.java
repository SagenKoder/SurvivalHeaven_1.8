package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import java.util.*;

public class Mutes implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender.hasPermission("sh.mutes")) {
            Sender.sendMessage(ChatColor.GREEN + "Muta spillere:");
            final StringBuffer m = new StringBuffer();
            final SH sh = (SH)SH.getManager();
            for (final String s : SH.mutedPlayers) {
                m.append(String.valueOf(s) + ", ");
            }
            Sender.sendMessage(ChatColor.AQUA + m.toString());
        }
        return false;
    }
}
