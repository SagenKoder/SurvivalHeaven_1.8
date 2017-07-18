package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class Fix implements CommandExecutor
{
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String[] args) {
        if (Sender instanceof Player && Sender.hasPermission("sh.fix")) {
            if (args.length == 0) {
                ((Player)Sender).getItemInHand().setDurability((short)0);
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("alt")) {
                ItemStack[] contents;
                for (int length = (contents = ((Player)Sender).getInventory().getContents()).length, j = 0; j < length; ++j) {
                    final ItemStack i = contents[j];
                    i.setDurability((short)0);
                }
            }
        }
        return false;
    }
}
