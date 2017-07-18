package info.nordbyen.survivalheaven.subplugins.homes;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import java.sql.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;
import java.io.*;

public class HomeManagerCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bare spillere kan ha hjem!");
            return true;
        }
        final Player p = (Player)sender;
        final HomeManager hm = SH.getManager().getHomeManager();
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString());
        if (command.getName().equalsIgnoreCase("home")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /home <navn>");
                return true;
            }
            final Home home = hm.getHomeFromPlayer(args[0], pd.getUUID());
            if (home == null) {
                p.sendMessage(ChatColor.RED + "Fant ikke hjemmet " + args[0]);
                return true;
            }
            p.teleport(home.getLocation());
            p.sendMessage(ChatColor.GREEN + "Du ble teleportert til hjemmet " + home.getName());
            return true;
        }
        else if (command.getName().equalsIgnoreCase("sethome")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Feil syntax: /sethome <navn>");
                return true;
            }
            final String name = args[0];
            final Home home2 = new Home(name, pd.getUUID(), p.getLocation());
            boolean done = false;
            try {
                done = hm.addHome(home2);
            }
            catch (SQLException e) {
                e.printStackTrace();
                p.sendMessage(ChatColor.RED + "En feil sjedde. Si ifra til stab!");
                return true;
            }
            if (!done) {
                p.sendMessage(ChatColor.RED + "Du har allerede et hjem ved navn " + home2.getName());
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "Laget hjemmet " + home2.getName() + " i " + home2.getLocation().getWorld().getName() + " " + home2.getLocation().getBlockX() + " " + home2.getLocation().getBlockY() + " " + home2.getLocation().getBlockZ());
            return true;
        }
        else {
            if (!command.getName().equalsIgnoreCase("homes")) {
                if (command.getName().equalsIgnoreCase("homeradius")) {
                    if (!p.isOp() && !p.hasPermission("sh.home.radius")) {
                        p.sendMessage(ChatColor.RED + "Du kan ike gj\u00f8re dette!");
                        return true;
                    }
                    if (args.length != 1) {
                        p.sendMessage(ChatColor.RED + "/homeradius <radius>");
                        return true;
                    }
                    int r = 0;
                    try {
                        r = Integer.parseInt(args[0]);
                    }
                    catch (Exception e3) {
                        p.sendMessage(ChatColor.RED + args[0] + " er ikke et tall!");
                        return true;
                    }
                    final int rs = r * r;
                    for (final Map.Entry<String, ArrayList<Home>> playerhomes : hm.homesOfPlayer.entrySet()) {
                        try {
                            String player = playerhomes.getKey();
                            player = SH.getManager().getPlayerDataManager().getPlayerData(player).getName();
                            final ArrayList<Home> homes = playerhomes.getValue();
                            for (final Home home3 : homes) {
                                if (home3 != null) {
                                    if (home3.getLocation() == null) {
                                        continue;
                                    }
                                    if (!home3.getLocation().getWorld().getName().equals(p.getLocation().getWorld().getName()) || home3.getLocation().distanceSquared(p.getLocation()) >= rs) {
                                        continue;
                                    }
                                    p.sendMessage(ChatColor.LIGHT_PURPLE + player + " - " + ChatColor.GREEN + home3.getName() + ChatColor.GRAY + ": [" + ChatColor.YELLOW + home3.getLocation().getBlockX() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home3.getLocation().getBlockY() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home3.getLocation().getBlockZ() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home3.getLocation().getWorld().getName() + ChatColor.GRAY + "]");
                                }
                            }
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                else if (command.getName().equalsIgnoreCase("delhome")) {
                    if (args.length != 1) {
                        if (args.length == 2) {
                            if (!p.hasPermission("sh.delhome.other") && !p.isOp()) {
                                p.sendMessage(ChatColor.RED + "Du har ikke rettigheter til \u00e5 slette andres hjem");
                                return true;
                            }
                            final Player r2 = Bukkit.getPlayer(args[0]);
                            String uuid = null;
                            if (r2 == null) {
                                final IPlayerData rpd = SH.getManager().getPlayerDataManager().getPlayerDataFromName(args[0]);
                                if (rpd == null) {
                                    p.sendMessage(ChatColor.RED + "Finner ikke spilleren " + args[0] + " online eller i databasen");
                                    return true;
                                }
                                uuid = rpd.getUUID();
                            }
                            if (uuid == null) {
                                uuid = r2.getUniqueId().toString();
                            }
                            final Home home4 = hm.getHomeFromPlayer(args[1], uuid);
                            if (home4 == null) {
                                p.sendMessage(ChatColor.RED + "Finner ikke hjemmet " + args[1] + " hos " + r2.getName());
                                return true;
                            }
                            try {
                                hm.deleteHomeFromPlayer(home4.getName(), uuid);
                                p.sendMessage(ChatColor.GRAY + "Slettet hjemmet " + args[1] + " fra " + args[0]);
                                return true;
                            }
                            catch (SQLException e) {
                                p.sendMessage(ChatColor.RED + "Noe gikk galt");
                                e.printStackTrace();
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "Feil syntax: /delhome <navn>");
                        return true;
                    }
                    final String name = args[0];
                    final ArrayList<Home> homes2 = hm.getHomesFromPlayer(p.getUniqueId().toString());
                    if (homes2 == null || homes2.size() == 0) {
                        p.sendMessage(ChatColor.RED + "Du har ingen hjem \u00e5 slette");
                        return true;
                    }
                    boolean hasHome = false;
                    for (final Home home5 : homes2) {
                        if (home5.getName().equalsIgnoreCase(name)) {
                            hasHome = true;
                        }
                    }
                    if (!hasHome) {
                        p.sendMessage(ChatColor.RED + "Finner ikke hjemmet " + name);
                        return true;
                    }
                    try {
                        hm.deleteHomeFromPlayer(name, p.getUniqueId().toString());
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                        p.sendMessage(ChatColor.RED + "Noe galt skjedde. Si ifra til staben!");
                        return true;
                    }
                    p.sendMessage(ChatColor.GREEN + "Du slettet hjemmet " + args[0]);
                    return true;
                }
                return true;
            }
            if (args.length != 0) {
                if (args.length != 1) {
                    p.sendMessage(ChatColor.RED + "Feil syntax: /homes");
                    return true;
                }
                if (!p.hasPermission("sh.homes.other") && !p.isOp()) {
                    p.sendMessage(ChatColor.RED + "Du har ikke rettigheter til \u00e5 se andres hjem");
                    return true;
                }
                final Player r2 = Bukkit.getPlayer(args[0]);
                String uuid = null;
                if (r2 == null) {
                    final IPlayerData rpd = SH.getManager().getPlayerDataManager().getPlayerDataFromName(args[0]);
                    if (rpd == null) {
                        p.sendMessage(ChatColor.RED + "Finner ikke spilleren " + args[0] + " online eller i databasen");
                        return true;
                    }
                    uuid = rpd.getUUID();
                }
                if (uuid == null) {
                    uuid = r2.getUniqueId().toString();
                }
                final ArrayList<Home> homes3 = hm.getHomesFromPlayer(uuid);
                p.sendMessage(ChatColor.GRAY + "---------------------------------------");
                p.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GRAY + " sine hjem");
                p.sendMessage(ChatColor.GRAY + "---------------------------------------");
                for (final Home home5 : homes3) {
                    p.sendMessage(ChatColor.GREEN + home5.getName() + ChatColor.GRAY + ": [" + ChatColor.YELLOW + home5.getLocation().getBlockX() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home5.getLocation().getBlockY() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home5.getLocation().getBlockZ() + ChatColor.GRAY + ", " + ChatColor.YELLOW + home5.getLocation().getWorld().getName() + ChatColor.GRAY + "]");
                }
                p.sendMessage(ChatColor.GRAY + "---------------------------------------");
                return true;
            }
            else {
                final ArrayList<Home> homes4 = hm.getHomesFromPlayer(p.getUniqueId().toString());
                if (homes4 == null || homes4.size() == 0) {
                    p.sendMessage(ChatColor.RED + "Du har ingen hjem, men kan lage et hjem med /sethome <navn>");
                    return true;
                }
                final StringBuilder sb = new StringBuilder(ChatColor.GREEN + "Du har f\u00f8lgende hjem:\n" + ChatColor.AQUA);
                for (final Home home4 : homes4) {
                    sb.append(String.valueOf(home4.getName()) + ", ");
                }
                p.sendMessage(sb.substring(0, sb.length() - 2));
                return true;
            }
        }
    }
}
