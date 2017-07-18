package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class Sol implements CommandExecutor
{
    int i;
    ArrayList<String> sol;
    
    public Sol() {
        this.i = 0;
        this.sol = new ArrayList<String>();
    }
    
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            if (!this.sol.contains(Sender.getName())) {
                if (command.getName().equalsIgnoreCase("sol") && args.length == 0) {
                    if (((Player)Sender).getWorld().hasStorm()) {
                        ++this.i;
                        this.sol.add(Sender.getName());
                        if (this.i < Bukkit.getOnlinePlayers().size() / 2) {
                            Bukkit.broadcastMessage(ChatColor.BLUE + Sender.getName() + ChatColor.AQUA + " stemte p\u00e5 \u00e5 f\u00e5 sol! " + this.i + "/" + Bukkit.getOnlinePlayers().size() / 2 + ". Skriv /sol for \u00e5 stemme");
                        }
                        else {
                            for (final World w : Bukkit.getWorlds()) {
                                w.setStorm(false);
                                w.setThundering(false);
                            }
                            this.sol.clear();
                            Bukkit.broadcastMessage(ChatColor.BLUE + Sender.getName() + ChatColor.AQUA + " stemte p\u00e5 \u00e5 f\u00e5 sol! " + this.i + "/" + Bukkit.getOnlinePlayers().size() / 2);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "V\u00e6ret ble satt til sol!!");
                            this.i = 0;
                        }
                    }
                    else {
                        Sender.sendMessage(ChatColor.RED + "Det er allerede sol i din verden");
                    }
                }
            }
            else {
                Sender.sendMessage(ChatColor.AQUA + "Du har allerede stemt!");
            }
        }
        return false;
    }
}
