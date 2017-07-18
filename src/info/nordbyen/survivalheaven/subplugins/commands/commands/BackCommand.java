package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.*;
import java.util.*;

public class BackCommand extends AbstractCommand
{
    public BackCommand(final String command) {
        super(command);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bare spillere kan ta /back");
            return true;
        }
        final Player p = (Player)sender;
        final IPlayerDataManager pdm = SH.getManager().getPlayerDataManager();
        final IPlayerData pd = pdm.getPlayerData(p.getUniqueId().toString());
        final Location loc = pd.getLastlocation();
        if (loc == null) {
            p.sendMessage(ChatColor.RED + "Fant ikke din forje posisjon");
            return true;
        }
        p.teleport(loc);
        p.sendMessage(ChatColor.GREEN + "Du ble sendt tilbake :D");
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
