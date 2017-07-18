package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class VerdenKommandoer implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player)Sender;
            if (command.getName().equalsIgnoreCase("normal")) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
            if (command.getName().equalsIgnoreCase("pvp")) {
                p.teleport(Bukkit.getWorld("pvp").getSpawnLocation());
            }
            if (command.getName().equalsIgnoreCase("theend")) {
                if (p.getWorld().getName().equalsIgnoreCase("PvP")) {
                    p.teleport(Bukkit.getWorld("theend_pvp").getSpawnLocation());
                }
                else {
                    p.teleport(Bukkit.getWorld("world_the_end").getSpawnLocation());
                }
            }
            if (command.getName().equalsIgnoreCase("nether")) {
                if (p.getWorld().getName() != "PvP") {
                    p.teleport(Bukkit.getWorld("world_nether").getSpawnLocation());
                }
                else {
                    p.teleport(Bukkit.getWorld("pvp_nether").getSpawnLocation());
                }
            }
            if (command.getName().equalsIgnoreCase("kreativ")) {
                p.teleport(Bukkit.getWorld("Kreativ").getSpawnLocation());
            }
            if (command.getName().equalsIgnoreCase("pvpt")) {
                p.teleport(Bukkit.getWorld("PvPTeams").getSpawnLocation());
            }
            if (command.getName().equalsIgnoreCase("old")) {
                p.teleport(Bukkit.getWorld("GodeGamleNormal").getSpawnLocation());
            }
            if (command.getName().equalsIgnoreCase("pve")) {
                p.teleport(Bukkit.getWorld("PvE").getSpawnLocation());
            }
        }
        return true;
    }
}
