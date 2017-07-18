package info.nordbyen.survivalheaven.subplugins.ligg;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;

public class LiggCommand extends AbstractCommand
{
    private static LiggCommand instance;
    
    public static void clearCommand() {
        LiggCommand.instance = null;
    }
    
    public static void initCommand() {
        if (LiggCommand.instance == null) {
            LiggCommand.instance = new LiggCommand("ligg");
        }
    }
    
    public LiggCommand(final String command) {
        super(command);
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length > 0) {
            sender.sendMessage("This command doesn't accept any arguments.");
            return true;
        }
        if (sender instanceof Player) {
            Ligg.getInstance().performAction((Player)sender);
        }
        else {
            Ligg.getInstance().performAction(sender);
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
