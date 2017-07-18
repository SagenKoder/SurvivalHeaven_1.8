package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import java.util.*;

public class R implements CommandExecutor
{
    public String arrayToString(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        for (final String element : array) {
            sb.append(element);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private Player findPlayerByName(final String playerName) {
        final Player player = Bukkit.getServer().getPlayer(playerName);
        return (player == null || !player.isOnline()) ? null : player;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("r")) {
            final Player msender = (Player)sender;
            if (M.resend.containsKey(msender.getName())) {
                final Player reciver = this.findPlayerByName(M.resend.get(msender.getName()));
                sender.sendMessage(ChatColor.DARK_GREEN + "Fra [" + msender.getName() + "] til [" + reciver.getName() + "] " + ChatColor.GREEN + this.arrayToString(args));
                reciver.sendMessage(ChatColor.DARK_GREEN + "Til [" + reciver.getName() + "] fra [" + msender.getName() + "] " + ChatColor.GREEN + this.arrayToString(args));
                final IRankManager rm = SH.getManager().getRankManager();
                final RankType senderRank = rm.getRank(((Player)sender).getUniqueId().toString());
                final RankType reciverRank = rm.getRank(reciver.getUniqueId().toString());
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    final RankType listenerRank = rm.getRank(o.getUniqueId().toString());
                    if (listenerRank.getId() >= RankType.UTVIKLER.getId() && listenerRank.getId() > senderRank.getId() && listenerRank.getId() > reciverRank.getId()) {
                        o.sendMessage(ChatColor.DARK_GRAY + "Fra [" + msender.getName() + "] til [" + reciver.getName() + "] " + ChatColor.GRAY + this.arrayToString(args));
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Hvem skal du svare?");
            }
        }
        return false;
    }
}
