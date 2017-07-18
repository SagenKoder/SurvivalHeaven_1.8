package info.nordbyen.survivalheaven.subplugins.quest;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class Godta_Command extends AbstractCommand
{
    public static final HashMap<String, Acceptable> players;
    private static Godta_Command instance;
    
    static {
        players = new HashMap<String, Acceptable>();
        Godta_Command.instance = null;
    }
    
    public static void clearCommand() {
        Godta_Command.instance = null;
    }
    
    public static void initCommand() {
        if (Godta_Command.instance == null) {
            Godta_Command.instance = new Godta_Command();
        }
    }
    
    private Godta_Command() {
        super("godta", "/<command>", "En kommando for bruk i quester");
        this.register();
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        sender.sendMessage(ChatColor.RED + "HAHA");
        if (!this.isAuthorized(sender, "sh.godta")) {
            sender.sendMessage(ChatColor.RED + "Du har ikke tillatelse til dette");
        }
        if (!this.isPlayer(sender)) {
            sender.sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re en spiller ( Sry Thomas :P )");
        }
        final Player p = (Player)sender;
        if (Godta_Command.players.containsKey(p.getUniqueId().toString())) {
            Godta_Command.players.get(p.getUniqueId().toString()).executedAccept(p.getUniqueId().toString());
            Godta_Command.players.remove(p.getUniqueId().toString());
        }
        else {
            p.sendMessage(ChatColor.RED + "Du har ingenting \u00e5 godta");
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
