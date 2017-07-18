package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import java.util.*;
import java.io.*;

public class ListCommand extends AbstractCommand
{
    public ListCommand() {
        super("list");
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final TreeMap<Integer, RankType> ranks = new TreeMap<Integer, RankType>();
        RankType[] values;
        for (int length = (values = RankType.values()).length, i = 0; i < length; ++i) {
            final RankType rank = values[i];
            ranks.put(rank.getId(), rank);
        }
        for (final Map.Entry<Integer, RankType> entry : ranks.descendingMap().entrySet()) {
            final RankType rank2 = entry.getValue();
            final List<String> players = new ArrayList<String>();
            for (final Player o : Bukkit.getOnlinePlayers()) {
                final RankType rt = SH.getManager().getRankManager().getRank(o.getUniqueId().toString());
                if (rt == rank2) {
                    players.add(o.getName());
                }
            }
            if (players.size() == 0) {
                continue;
            }
            Collections.sort(players);
            final String prefix = rank2.getPrefix();
            final StringBuilder list = new StringBuilder(String.valueOf(rank2.getPrefix()) + rank2.getName() + ": ");
            for (final String player : players) {
                list.append(prefix).append(player).append(", ");
            }
            list.delete(list.length() - 2, list.length());
            sender.sendMessage(list.toString());
        }
        return false;
    }
}
