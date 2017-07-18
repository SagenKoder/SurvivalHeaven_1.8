package info.nordbyen.survivalheaven.subplugins.groupmanager;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class FriendManagerCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String arg, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "En konsoll kan ikke ha venner .-.");
            return true;
        }
        final Player p = (Player)sender;
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString());
        final FriendManager fm = SH.getManager().getFriendManager();
        if (args.length == 0) {
            p.sendMessage(ChatColor.GREEN + "************************************************");
            p.sendMessage(ChatColor.BLUE + "Kommandoer til vennesystemet");
            p.sendMessage(ChatColor.GRAY + "En spiller som er venn med deg kan \u00e5pne dine kister og knuse dine blokker. Pass p\u00e5 hvem du legger til som venn!");
            p.sendMessage(ChatColor.GREEN + "------------------------------------------------");
            p.sendMessage(ChatColor.YELLOW + "/venn " + ChatColor.GRAY + "Lister kommandoer");
            p.sendMessage(ChatColor.YELLOW + "/venn list " + ChatColor.GRAY + "Lister alle dine venner");
            p.sendMessage(ChatColor.YELLOW + "/venn sp\u00f8r <spiller> " + ChatColor.GRAY + "Sender en venneforesp\u00f8rsel");
            p.sendMessage(ChatColor.YELLOW + "/venn godta <spiller> " + ChatColor.GRAY + "Godtar en venneforesp\u00f8rsel");
            p.sendMessage(ChatColor.YELLOW + "/venn avsl\u00e5 <spiller> " + ChatColor.GRAY + "Avsl\u00e5r en venneforesp\u00f8rsel");
            p.sendMessage(ChatColor.YELLOW + "/venn foresp\u00f8rsler " + ChatColor.GRAY + "Lister alle dine venneforesp\u00f8rsler");
            p.sendMessage(ChatColor.RED + "/venn fjern <spiller> " + ChatColor.GRAY + "Sletter en spiller fra vennelisten din");
            if (p.hasPermission("sh.venn.force") || p.isOp()) {
                p.sendMessage(ChatColor.RED + "/venn makefuck <spiller> <spiller> " + ChatColor.GRAY + "Forcer to spillere til \u00e5 bli venner");
            }
            p.sendMessage(ChatColor.GREEN + "************************************************");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /venn list");
                return true;
            }
            final ArrayList<IPlayerData> list = fm.getFriendsWith(pd);
            if (list == null || list.size() == 0) {
                p.sendMessage(ChatColor.GREEN + "Du har for \u00f8yeblikket ingen spiller p\u00e5 vennelisten. Legg til venner med /venn sp\u00f8r <spiller>");
                return true;
            }
            final StringBuilder venner = new StringBuilder(ChatColor.GREEN + "F\u00f8lgende personer er venn med deg:\n" + ChatColor.AQUA);
            for (final IPlayerData venn : list) {
                venner.append(String.valueOf(venn.getName()) + ", ");
            }
            final String l = venner.substring(0, venner.length() - 2);
            p.sendMessage(l);
            return true;
        }
        else if (args[0].equalsIgnoreCase("sp\u00f8r")) {
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /venn sp\u00f8r <spiller>");
                return true;
            }
            final Player r = Bukkit.getPlayer(args[1]);
            if (r == null) {
                p.sendMessage(ChatColor.RED + "Fant ikke spilleren " + args[1]);
                return true;
            }
            if (r == p) {
                p.sendMessage(ChatColor.RED + "Du kan ikke legg til deg selv som venn!");
                return true;
            }
            final ArrayList<IPlayerData> list2 = fm.getFriendsWith(pd);
            for (final IPlayerData fpd : list2) {
                if (fpd.getUUID().equals(r.getUniqueId().toString())) {
                    p.sendMessage(ChatColor.RED + "Du er allerede venn med " + r.getName());
                    return true;
                }
            }
            final ArrayList<String> reql = fm.getFriendrequestsTo(args[1]);
            if (reql != null && reql.contains(p.getName())) {
                p.sendMessage(ChatColor.RED + "Du har allerede sendt en foresp\u00f8rsel til denne spilleren");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "Du sendte en venneforesp\u00f8rsel til " + r.getName());
            r.sendMessage(ChatColor.GREEN + "************************************************");
            r.sendMessage(ChatColor.BLUE + "Du har mottat en venneforesp\u00f8rsel fra " + p.getName());
            r.sendMessage(ChatColor.GRAY + "En spiller som er venn med deg kan \u00e5pne dine kister og knuse dine blokker. Pass p\u00e5 hvem du legger til som venn!");
            r.sendMessage(ChatColor.GREEN + "------------------------------------------------");
            r.sendMessage(ChatColor.YELLOW + "/venn godta " + p.getName() + ChatColor.GRAY + " For \u00e5 godta foresp\u00f8rselen");
            r.sendMessage(ChatColor.YELLOW + "/venn avsl\u00e5 " + p.getName() + ChatColor.GRAY + " For \u00e5 avsl\u00e5 foresp\u00f8rselen");
            r.sendMessage(ChatColor.GREEN + "************************************************");
            ArrayList<String> req = fm.getFriendrequestsTo(args[1]);
            if (req == null) {
                fm.setFriendrequestsTo(args[1], new ArrayList<String>());
                req = fm.getFriendrequestsTo(args[1]);
            }
            req.add(p.getName());
            return true;
        }
        else if (args[0].equalsIgnoreCase("godta")) {
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /venn godta <spiller>");
                return true;
            }
            if (!fm.getFriendrequestsTo(p.getName()).contains(args[1])) {
                p.sendMessage(ChatColor.RED + "Du har ingen foresp\u00f8rsler fra " + args[1]);
                return true;
            }
            final Player r = Bukkit.getPlayer(args[1]);
            if (r == null) {
                p.sendMessage(ChatColor.RED + args[0] + " m\u00e5 v\u00e6re online for \u00e5 gj\u00f8re dette");
                return true;
            }
            try {
                fm.setFriends(p.getUniqueId().toString(), r.getUniqueId().toString());
            }
            catch (SQLException e2) {
                p.sendMessage(ChatColor.RED + "Noe gikk galt... Si ifra til staben eller send en melding til alex.l0lkj p\u00e5 skype");
                return true;
            }
            fm.getFriendrequestsTo(p.getName()).remove(r.getName());
            p.sendMessage(ChatColor.GREEN + "Du godtok venneforesp\u00f8rselen fra " + args[0]);
            p.sendMessage(ChatColor.GRAY + "Dere er n\u00e5 venner og kan \u00f8delegge hverandres blokker");
            r.sendMessage(ChatColor.GREEN + p.getName() + " godtok venneforesp\u00f8rselen din");
            r.sendMessage(ChatColor.GRAY + "Dere er n\u00e5 venner og kan \u00f8delegge hverandres blokker");
            return true;
        }
        else if (args[0].equalsIgnoreCase("fjern")) {
            final IPlayerData fpd2 = SH.getManager().getPlayerDataManager().getPlayerDataFromName(args[1]);
            if (!fm.isFriends(pd, fpd2)) {
                p.sendMessage(ChatColor.RED + "Dere er ikke venner");
                return true;
            }
            try {
                fm.removeFriendship(pd.getUUID(), fpd2.getUUID());
            }
            catch (SQLException e2) {
                p.sendMessage(ChatColor.RED + "Noe gikk galt... Si ifra til staben eller send en melding til alex.l0lkj p\u00e5 skype");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "Du fjernet " + args[1] + " fra dine venner");
            final Player r2 = Bukkit.getPlayer(args[1]);
            if (r2 != null) {
                r2.sendMessage(ChatColor.RED + p.getName() + " fjernet deg fra sine venner!");
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("avsl\u00e5")) {
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /venn avsl\u00e5 <spiller>");
                return true;
            }
            ArrayList<String> requests = fm.getFriendrequestsTo(p.getName());
            if (requests == null) {
                fm.setFriendrequestsTo(p.getName(), new ArrayList<String>());
                requests = fm.getFriendrequestsTo(p.getName());
            }
            if (!requests.contains(args[1])) {
                p.sendMessage(ChatColor.RED + "Du har ingen foresp\u00f8rsler fra " + args[1]);
                return true;
            }
            requests.remove(args[1]);
            p.sendMessage(ChatColor.GREEN + "Avsl\u00e5 foresp\u00f8rselen fra " + args[1]);
            final Player r2 = Bukkit.getPlayer(args[1]);
            if (r2 != null) {
                r2.sendMessage(ChatColor.RED + "Din venneforesp\u00f8rsel ble avsl\u00e5tt av " + p.getName());
            }
            return true;
        }
        else {
            if (args[0].equalsIgnoreCase("foresp\u00f8rsler")) {
                ArrayList<String> requests = fm.getFriendrequestsTo(p.getName());
                if (requests == null) {
                    fm.setFriendrequestsTo(p.getName(), new ArrayList<String>());
                    requests = fm.getFriendrequestsTo(p.getName());
                }
                final StringBuilder sb = new StringBuilder(ChatColor.GREEN + "Alle spillere som \u00f8nsker \u00e5 legge deg til som venn:\n" + ChatColor.AQUA);
                for (final String re : requests) {
                    sb.append(String.valueOf(re) + ", ");
                }
                p.sendMessage(sb.substring(0, sb.length() - 2));
                return true;
            }
            if (args[0].equalsIgnoreCase("makefuck")) {
                if (!p.hasPermission("sh.venn.force") && !p.isOp()) {
                    p.sendMessage(ChatColor.RED + "NO PERM");
                    return true;
                }
                if (args.length != 3) {
                    p.sendMessage(ChatColor.RED + "Feil antall argumenter!");
                    return true;
                }
                final Player a = Bukkit.getPlayer(args[1]);
                final Player b = Bukkit.getPlayer(args[2]);
                if (a == null) {
                    p.sendMessage(ChatColor.RED + "Fant ikke spiller " + args[1]);
                }
                if (b == null) {
                    p.sendMessage(ChatColor.RED + "Fant ikke spiller " + args[2]);
                }
                if (a == null || b == null) {
                    return true;
                }
                if (fm.isFriends(a.getUniqueId().toString(), b.getUniqueId().toString())) {
                    p.sendMessage(ChatColor.RED + "Disse spillerne er venner!");
                    return true;
                }
                try {
                    fm.setFriends(a.getUniqueId().toString(), b.getUniqueId().toString());
                }
                catch (SQLException e) {
                    p.sendMessage(ChatColor.RED + "FUCK FUCK FUCK! CALL l0lkj NOW!");
                    e.printStackTrace();
                }
                p.sendMessage(ChatColor.GREEN + "De ble gjor til venner :D");
            }
            return true;
        }
    }
}
