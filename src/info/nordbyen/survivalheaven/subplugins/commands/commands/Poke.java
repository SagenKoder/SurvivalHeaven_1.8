package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;

public class Poke implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(Sender.getName()).getRank() > 1) {
            if (SH.pokes.contains(Sender.getName())) {
                SH.pokes.remove(Sender.getName());
                Sender.sendMessage(ChatColor.GREEN + "Du skrudde av pokes (folk kan ikke poke deg)");
            }
            else {
                SH.pokes.add(Sender.getName());
                Sender.sendMessage(ChatColor.GREEN + "Du skrudde p\u00e5 pokes (folk kan poke deg)");
            }
        }
        else {
            Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til \u00e5 utf\u00f8re denne kommandoen");
        }
        return false;
    }
}
