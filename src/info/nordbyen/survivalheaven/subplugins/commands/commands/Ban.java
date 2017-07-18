package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import java.util.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.sql.*;

public class Ban implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equals("tempban")) {
            if (sender.hasPermission("sh.ban") && args.length == 2) {
                if (args[1].contains("d")) {
                    final String s = args[1];
                    int i = Integer.parseInt(s.split("d")[0]);
                    final Calendar d = Calendar.getInstance();
                    d.add(10, i *= 24);
                    final Timestamp t = new Timestamp(d.getTimeInMillis());
                    SH.getManager().getMysqlManager().insert("bandata", new String[] { "player", "until", "grunn" }, new Object[] { this.getPlayerID(args[0]), t.toString(), "Det ble ikke oppgitt en grunn" });
                    Bukkit.broadcastMessage(ChatColor.RED + args[0] + " ble tempbannet til " + t.toString());
                    try {
                        final Player p = Bukkit.getPlayer(args[0]);
                        p.kickPlayer("Du ble tempbannet til " + t.toString());
                    }
                    catch (Exception f) {
                        sender.sendMessage(ChatColor.RED + "Finner ikke spiller");
                    }
                }
                else if (args[1].contains("h")) {
                    final String s = args[1];
                    final int i = Integer.parseInt(s.split("h")[0]);
                    final Calendar d = Calendar.getInstance();
                    d.add(10, i);
                    final Timestamp t = new Timestamp(d.getTimeInMillis());
                    SH.getManager().getMysqlManager().insert("bandata", new String[] { "player", "until", "grunn" }, new Object[] { this.getPlayerID(args[0]), t.toString(), "Det ble ikke oppgitt en grunn" });
                    Bukkit.broadcastMessage(ChatColor.RED + args[0] + " ble tempbannet til " + t.toString());
                    try {
                        final Player p = Bukkit.getPlayer(args[0]);
                        p.kickPlayer("Du ble tempbannet til " + t.toString());
                    }
                    catch (Exception f) {
                        sender.sendMessage(ChatColor.RED + "Finner ikke spiller");
                    }
                }
            }
        }
        else if (cmd.getName().equalsIgnoreCase("ban")) {
            if (args.length == 1) {
                final Calendar d2 = Calendar.getInstance();
                d2.add(1, 100);
                final Timestamp ts = new Timestamp(d2.getTimeInMillis());
                SH.getManager().getMysqlManager().insert("bandata", new String[] { "player", "until", "grunn" }, new Object[] { this.getPlayerID(args[0]), ts.toString(), "Det ble ikke oppgitt en grunn" });
                final Player tg = Bukkit.getServer().getPlayer(args[0]);
                tg.kickPlayer("Du ble bannet fra serveren for alltid. Grunn: Det ble ikke oppgitt en grunn");
                tg.getWorld().strikeLightning(tg.getLocation());
                tg.getWorld().strikeLightning(tg.getLocation());
                tg.getWorld().strikeLightning(tg.getLocation());
                tg.getWorld().strikeLightning(tg.getLocation());
                Bukkit.broadcast(ChatColor.GOLD + "[StabAlarm] MIDDELS: " + ChatColor.WHITE + sender.getName() + " bannet " + args[0] + "for grunn: " + "Det ble ikke oppgitt en grunn", "sh.kick");
                return true;
            }
            if (args.length > 1) {
                final StringBuffer me = new StringBuffer();
                for (int i = 1; i < args.length; ++i) {
                    me.append(String.valueOf(args[i]) + " ");
                }
                final Calendar d3 = Calendar.getInstance();
                d3.add(1, 100);
                final Timestamp ts2 = new Timestamp(d3.getTimeInMillis());
                SH.getManager().getMysqlManager().insert("bandata", new String[] { "player", "until", "grunn" }, new Object[] { this.getPlayerID(args[0]), ts2.toString(), me.toString() });
                final Player tg2 = Bukkit.getServer().getPlayer(args[0]);
                tg2.kickPlayer("Du ble bannet fra serveren for alltid. Grunn: " + (Object)me);
                tg2.getWorld().strikeLightning(tg2.getLocation());
                tg2.getWorld().strikeLightning(tg2.getLocation());
                tg2.getWorld().strikeLightning(tg2.getLocation());
                tg2.getWorld().strikeLightning(tg2.getLocation());
                Bukkit.broadcast(ChatColor.GOLD + "[StabAlarm] MIDDELS: " + ChatColor.WHITE + sender.getName() + " bannet " + args[0] + "for grunn: " + (Object)me, "sh.kick");
                return true;
            }
        }
        return false;
    }
    
    public int getPlayerID(final String player) {
        try {
            Throwable t = null;
            try {
                final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT id FROM players WHERE name='" + player + "'");
                try {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                    return 0;
                }
                finally {
                    if (rs != null) {
                        rs.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable t2;
                    t = t2;
                }
                else {
                    final Throwable t2;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (SQLException ex) {}
        return 0;
    }
}
