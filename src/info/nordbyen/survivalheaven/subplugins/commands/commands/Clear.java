package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import java.util.*;

public class Clear implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(Sender.getName()).getRank() > 3) {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(p.getName()).getRank() < 4) {
                        for (int i = 0; i < 100; ++i) {
                            p.sendMessage("");
                        }
                    }
                }
                Bukkit.broadcastMessage(ChatColor.GOLD + "Chatten ble renset av " + Sender.getName());
            }
        }
        else {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(p.getName()).getRank() < 4) {
                    for (int i = 0; i < 100; ++i) {
                        p.sendMessage("");
                    }
                }
            }
            Bukkit.broadcastMessage(ChatColor.GOLD + "Chatten ble renset av konsollen");
        }
        return false;
    }
}
