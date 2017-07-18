package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.plugin.java.*;
import java.util.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;

public class TPA implements CommandExecutor
{
    JavaPlugin pl;
    public static HashMap<String, String> tpa;
    
    static {
        TPA.tpa = new HashMap<String, String>();
    }
    
    public TPA() {
        this.pl = SH.getPlugin();
    }
    
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                final Player p = Bukkit.getPlayer(args[0]);
                if (p != null) {
                    TPA.tpa.put(p.getName(), Sender.getName());
                    Sender.sendMessage(ChatColor.GREEN + "Foresp\u00f8rsel sendt!");
                    p.sendMessage(ChatColor.DARK_GREEN + Sender.getName() + " vil teleportere til deg.");
                    p.sendMessage(ChatColor.GREEN + "Skriv /tpaccept for \u00e5 godta");
                    this.pl.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.pl, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            TPA.tpa.remove(Sender.getName());
                        }
                    }, 600L);
                }
            }
            else {
                Sender.sendMessage(ChatColor.RED + "Bruk /tpa <spiller>");
            }
        }
        return false;
    }
}
