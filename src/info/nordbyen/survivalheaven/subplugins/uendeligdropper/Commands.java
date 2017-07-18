package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.*;
import org.bukkit.block.*;

public class Commands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("infdisp")) {
            return true;
        }
        if (!sender.hasPermission("infdispenser.command")) {
            return true;
        }
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "du kan ikke bruke /infdisp kommandoen utenfor spillet!");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "Bruk " + ChatColor.YELLOW + "/infdisp sett" + ChatColor.GRAY + " - for \u00e5 sette en uendelig dispenser");
            sender.sendMessage(ChatColor.GRAY + "Brukt " + ChatColor.YELLOW + "/infdisp fjern" + ChatColor.GRAY + " - for \u00e5 fjerne en uendelig dispenser");
            return true;
        }
        if (args[0].equalsIgnoreCase("sett")) {
            final Player player = (Player)sender;
            if (Util.getBlockTarget((LivingEntity)player).getType() != Material.DISPENSER && Util.getBlockTarget((LivingEntity)player).getType() != Material.DROPPER) {
                player.sendMessage(String.valueOf(SH.PREFIX) + "Du m\u00e5 se p\u00e5 en dispenser eller dropper!");
                return true;
            }
            final Block dispenser = Util.getBlockTarget((LivingEntity)player);
            if (!Dispensers.getInstance().getList("dispensers").contains(dispenser.getLocation().toString())) {
                Dispensers.getInstance();
                Dispensers.setLocation(dispenser.getLocation());
                player.sendMessage(String.valueOf(SH.PREFIX) + "Fyll denne dispenseren/dropperen med uendelig stacks!");
            }
            else {
                player.sendMessage(String.valueOf(SH.PREFIX) + "Denne dispenseren/dropperen er allerede uendelig");
            }
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("fjern")) {
                sender.sendMessage(String.valueOf(SH.PREFIX) + ChatColor.RED + "Feil syntaks!");
                return true;
            }
            final Player player = (Player)sender;
            if (Util.getBlockTarget((LivingEntity)player).getType() != Material.DISPENSER && Util.getBlockTarget((LivingEntity)player).getType() != Material.DROPPER) {
                player.sendMessage(String.valueOf(SH.PREFIX) + "Du m\u00e5 se p\u00e5 en dispenser eller dropper!");
                return true;
            }
            final Block dispenser = Util.getBlockTarget((LivingEntity)player);
            if (Dispensers.getInstance().getList("dispensers").contains(dispenser.getLocation().toString())) {
                Dispensers.getInstance().getList("dispensers").remove(dispenser.getLocation().toString());
                Dispensers.getInstance().save();
            }
            player.sendMessage(String.valueOf(SH.PREFIX) + "Fjernet dispenseren/dropperen fra listen");
            return true;
        }
    }
}
