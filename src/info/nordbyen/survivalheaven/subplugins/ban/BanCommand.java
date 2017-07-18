package info.nordbyen.survivalheaven.subplugins.ban;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import java.util.*;

public class BanCommand extends AbstractCommand
{
    public BanCommand(final String command) {
        super(command);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final String[] largs = label.split(" ");
        if (largs.length <= 0) {
            throw new IllegalStateException("largs cannot be less than one");
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
