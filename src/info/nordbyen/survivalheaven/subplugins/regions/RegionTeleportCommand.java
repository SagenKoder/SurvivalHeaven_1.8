package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class RegionTeleportCommand extends AbstractCommand
{
    public RegionTeleportCommand() {
        super("nord", "/<command>", "Kommandoer for \u00e5 teleportere til utpostene", Arrays.asList("s\u00f8r", "nord", "\u00f8st", "vest", "spawn", "kreativ", "creative", "pvp"));
        this.register();
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bare in-game spillere kan teleportere!");
            return true;
        }
        final Player p = (Player)sender;
        if (label.equalsIgnoreCase("s\u00f8r")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til s\u00f8r");
            p.teleport(new Location(Bukkit.getWorld("NyVerden"), 145.0, 77.0, 6234.0));
        }
        else if (label.equalsIgnoreCase("nord")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til nord");
            p.teleport(new Location(Bukkit.getWorld("NyVerden"), -232.0, 64.0, -6071.0));
        }
        else if (label.equalsIgnoreCase("\u00f8st")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til \u00f8st");
            p.teleport(new Location(Bukkit.getWorld("NyVerden"), 6269.5, 65.5, 780.5, 180.0f, 0.0f));
        }
        else if (label.equalsIgnoreCase("vest")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til vest");
            p.teleport(new Location(Bukkit.getWorld("NyVerden"), -5774.0, 73.0, 95.0));
        }
        else if (label.equalsIgnoreCase("spawn") || label.equalsIgnoreCase("normal") || label.equalsIgnoreCase("hub")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til spawn");
            p.teleport(new Location(Bukkit.getWorld("NyVerden"), 104.5, 65.5, 161.5, 270.0f, 0.0f));
        }
        else if (label.equalsIgnoreCase("creative") || label.equalsIgnoreCase("kreativ")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til kreativ");
            p.teleport(new Location(Bukkit.getWorld("creative"), 3.0, 74.0, -112.0));
        }
        else if (label.equalsIgnoreCase("pvp")) {
            p.sendMessage(ChatColor.GREEN + "Teleporterer til PvP");
            p.teleport(new Location(Bukkit.getWorld("pvp2"), 3458.0, 84.0, -876.0));
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
