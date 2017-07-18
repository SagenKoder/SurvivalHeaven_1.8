package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;

public class SeenCommand extends AbstractCommand
{
    public SeenCommand(final String command) {
        super(command);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Feil syntaks: /seen <spiller>");
            return true;
        }
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerDataFromName(args[0]);
        if (pd == null) {
            sender.sendMessage(ChatColor.RED + "Spilleren finnes ikke");
            return true;
        }
        long lastLoginDiffInSec = (new Date().getTime() - pd.getLastlogin().getTime()) / 1000L;
        long timePlayedDiffInSec = pd.getTimeplayed() / 1000L;
        long firstLoginDiffInSec = (new Date().getTime() - pd.getFirstlogin().getTime()) / 1000L;
        final long[] lastLogin = { 0L, ((lastLoginDiffInSec /= 60L) >= 24L) ? (lastLoginDiffInSec % 24L) : lastLoginDiffInSec, ((lastLoginDiffInSec /= 60L) >= 60L) ? (lastLoginDiffInSec % 60L) : lastLoginDiffInSec, (lastLoginDiffInSec >= 60L) ? (lastLoginDiffInSec % 60L) : lastLoginDiffInSec };
        lastLoginDiffInSec = (lastLogin[0] = lastLoginDiffInSec / 24L);
        final long[] timePlayed = { 0L, ((timePlayedDiffInSec /= 60L) >= 24L) ? (timePlayedDiffInSec % 24L) : timePlayedDiffInSec, ((timePlayedDiffInSec /= 60L) >= 60L) ? (timePlayedDiffInSec % 60L) : timePlayedDiffInSec, (timePlayedDiffInSec >= 60L) ? (timePlayedDiffInSec % 60L) : timePlayedDiffInSec };
        timePlayedDiffInSec = (timePlayed[0] = timePlayedDiffInSec / 24L);
        final long[] firstLogin = { 0L, ((firstLoginDiffInSec /= 60L) >= 24L) ? (firstLoginDiffInSec % 24L) : firstLoginDiffInSec, ((firstLoginDiffInSec /= 60L) >= 60L) ? (firstLoginDiffInSec % 60L) : firstLoginDiffInSec, (firstLoginDiffInSec >= 60L) ? (firstLoginDiffInSec % 60L) : firstLoginDiffInSec };
        firstLoginDiffInSec = (firstLogin[0] = firstLoginDiffInSec / 24L);
        final float avgTimeInHours = (timePlayed[0] * 24L + timePlayed[1]) / (firstLogin[0] * 24L + firstLogin[1]);
        sender.sendMessage(ChatColor.BLUE + "***************************************************");
        sender.sendMessage(ChatColor.YELLOW + pd.getName() + "s spillerinfo");
        sender.sendMessage(ChatColor.BLUE + "---------------------------------------------------");
        if (Bukkit.getPlayer(pd.getName()) == null) {
            sender.sendMessage(ChatColor.GRAY + "Sist logget p\u00e5: " + ChatColor.YELLOW + lastLogin[0] + "d " + lastLogin[1] + "t " + lastLogin[2] + "m " + lastLogin[3] + "s");
        }
        else {
            sender.sendMessage(ChatColor.GRAY + "Sist logget p\u00e5: " + ChatColor.GREEN + "ER ONLINE");
        }
        sender.sendMessage(ChatColor.GRAY + "Total tid spilt: " + ChatColor.YELLOW + (timePlayed[0] * 24L + timePlayed[1]) + "t " + timePlayed[2] + "m " + timePlayed[3] + "s");
        sender.sendMessage(ChatColor.GRAY + "F\u00f8rste login: " + ChatColor.YELLOW + firstLogin[0] + "d " + firstLogin[1] + "t " + firstLogin[2] + "m " + firstLogin[3] + "s");
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final String lastarg = (args.length >= 1) ? args[args.length - 1] : "";
        final IPlayerDataManager pdm = SH.getManager().getPlayerDataManager();
        final List<IPlayerData> pda = pdm.getAllPlayerData();
        final List<String> allNames = new ArrayList<String>();
        for (final IPlayerData pd : pda) {
            if (pd.getName().startsWith(lastarg)) {
                allNames.add(pd.getName());
            }
        }
        return allNames;
    }
}
