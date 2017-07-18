package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import java.util.*;

public class M extends AbstractCommand
{
    public static HashMap<String, String> resend;
    
    static {
        M.resend = new HashMap<String, String>();
    }
    
    public M(final String command, final List<String> aliases) {
        super(command, "/m <spiller> <melding>", "sende en privat melding til en spiller", aliases);
    }
    
    public String arrayToString(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i < array.length; ++i) {
            sb.append(array[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private Player findPlayerByName(final String playerName) {
        final Player player = Bukkit.getServer().getPlayer(playerName);
        return (player == null || !player.isOnline()) ? null : player;
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Feil: Bruk /m spiller [tekst]");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("Du m\u00e5 huske tekst!");
            return true;
        }
        if (args.length >= 2) {
            final Player msender = (Player)sender;
            final Player reciver = this.findPlayerByName(args[0]);
            if (msender.getName().equals(reciver.getName()) || reciver == null) {
                msender.sendMessage(ChatColor.RED + "Kan ikke sende meldinger til denne spilleren!");
                return true;
            }
            M.resend.put(reciver.getName(), msender.getName());
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
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final ArrayList<String> players = new ArrayList<String>();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o.getName().toUpperCase().startsWith(args[args.length - 1].toUpperCase())) {
                players.add(o.getName());
            }
        }
        return players;
    }
}
