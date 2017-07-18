package info.nordbyen.survivalheaven.subplugins.rankmanager;

import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import info.nordbyen.survivalheaven.api.playerdata.*;

public class RankManagerCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String arg, final String[] args) {
        SH.getManager().getRankManager().updateNames();
        final IRankManager rm = SH.getManager().getRankManager();
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            final RankType rank = rm.getRank(p.getUniqueId().toString());
            if (rank != RankType.ADMINISTRATOR) {
                p.sendMessage(ChatColor.RED + "Du har ikke h\u00f8y nok rank til dette");
                return true;
            }
        }
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Feil syntax! /rank <spiller> <BANNED|BRUKER|SPONSOR|ARKITEKT|MOD|ADMIN>");
            return true;
        }
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerDataFromName(args[0]);
        if (pd == null) {
            sender.sendMessage(ChatColor.RED + "Finner ikke spilleren " + args[0]);
            return true;
        }
        final RankType rt = RankType.getRankFromName(args[1]);
        if (rt == null) {
            sender.sendMessage(ChatColor.RED + "Finner ikke ranken " + args[1]);
            return true;
        }
        pd.setRank(rt.getId());
        sender.sendMessage(ChatColor.GREEN + args[0] + " ble satt til " + rt.getName());
        final Player r = Bukkit.getPlayer(args[0]);
        if (r != null) {
            r.sendMessage(ChatColor.GREEN + "Du ble satt til " + rt.getName() + " av " + sender.getName());
        }
        SH.getManager().getRankManager().updateNames();
        return true;
    }
}
