package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import java.util.*;

public class ServerCommand extends AbstractCommand
{
    static ServerCommand instance;
    private final String FEIL_SYNTAX;
    
    static {
        ServerCommand.instance = null;
    }
    
    public static void clearCommand() {
        ServerCommand.instance = null;
    }
    
    public static void initCommand() {
        if (ServerCommand.instance == null) {
            ServerCommand.instance = new ServerCommand();
        }
    }
    
    private ServerCommand() {
        super("sh", "/<command>", "En gruppe med administrative kommandoer", Arrays.asList("s"));
        this.FEIL_SYNTAX = ChatColor.RED + "Feil syntax. Skriv /sh for en liste med kommandoer";
        this.register();
    }
    
    private boolean ban(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 3) {
            sender.sendMessage(this.FEIL_SYNTAX);
            return true;
        }
        final IPlayerData pd = this.findPlayer(sender, args[1]);
        if (pd == null) {
            return true;
        }
        int days = -1;
        try {
            days = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            sender.sendMessage(ChatColor.RED + args[2] + " m\u00e5 v\u00e6re et tall");
            return true;
        }
        if (days < 1) {
            sender.sendMessage(ChatColor.RED + "Feil med tallet " + days);
            return true;
        }
        String reason = "The banhammer has spoken!";
        if (args.length >= 4) {
            final StringBuffer melding = new StringBuffer();
            for (int i = 3; i < args.length; ++i) {
                melding.append(String.valueOf(args[i]) + " ");
            }
            reason = melding.toString();
        }
        Bukkit.broadcastMessage(ChatColor.GRAY + "Spiller " + ChatColor.YELLOW + pd.getName() + ChatColor.GRAY + " ble bannet av en operator");
        Bukkit.broadcastMessage(ChatColor.GRAY + "    -> Grunn: " + ChatColor.YELLOW + reason);
        Bukkit.broadcastMessage(ChatColor.GRAY + "    -> Tid " + ChatColor.YELLOW + days);
        return true;
    }
    
    private IPlayerData findPlayer(final CommandSender sender, final String name) {
        final Player r = Bukkit.getPlayer(name);
        IPlayerData pd;
        if (r != null) {
            pd = SH.getManager().getPlayerDataManager().getPlayerData(r.getUniqueId().toString());
        }
        else {
            sender.sendMessage(ChatColor.GRAY + "Spilleren " + name + " er ikke online, s\u00f8ker internt...");
            pd = SH.getManager().getPlayerDataManager().getPlayerDataFromName(name);
            if (pd == null) {
                sender.sendMessage(ChatColor.RED + "Fant ikke spiller " + name);
                return null;
            }
        }
        if (pd == null) {
            sender.sendMessage(ChatColor.RED + "Fant spilleren online men ikke internt. Kontakt l0lkj om dette!");
            return null;
        }
        return pd;
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!this.isAuthorized(sender, "sh.server")) {
            sender.sendMessage(ChatColor.RED + "Du har ikke tillatelse til dette");
            return true;
        }
        if (!this.isPlayer(sender)) {
            sender.sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re en spiller ( Sry Thomas :P )");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "**********************************");
            sender.sendMessage(ChatColor.BLUE + "Syntax for /sh");
            sender.sendMessage(ChatColor.GOLD + "----------------------------------");
            sender.sendMessage(ChatColor.YELLOW + "/sh ban <spiller> <dager> <grunn>" + " " + ChatColor.GRAY + "Banner en spiller");
            sender.sendMessage(ChatColor.YELLOW + "/sh permban <spiller> < grunn >" + " " + ChatColor.GRAY + "Banner en spiller");
            sender.sendMessage(ChatColor.YELLOW + "/sh warn add <spiller> <1-3> <grunn>" + " " + ChatColor.GRAY + "Gir en advarsel");
            sender.sendMessage(ChatColor.YELLOW + "/sh warn see <spiller>" + " " + ChatColor.GRAY + "Ser advarsler");
            sender.sendMessage(ChatColor.YELLOW + "/sh warn del <id>" + " " + ChatColor.GRAY + "Fjerner en advarsel");
            sender.sendMessage(ChatColor.YELLOW + "/sh note add <spiller> <grunn>" + ChatColor.GRAY + " " + "Skriver et notat");
            sender.sendMessage(ChatColor.YELLOW + "/sh note see <spiller>" + " " + ChatColor.GRAY + "Ser notater");
            sender.sendMessage(ChatColor.YELLOW + "/sh note del <id>" + " " + ChatColor.GRAY + "Fjerner et notat");
            sender.sendMessage(ChatColor.GOLD + "**********************************");
            return true;
        }
        if (args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("tempban")) {
            return this.ban(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("permban")) {
            return this.permban(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("warn")) {
            return this.warn(sender, command, label, args);
        }
        sender.sendMessage(String.valueOf(this.FEIL_SYNTAX) + "0");
        return true;
    }
    
    private boolean permban(final CommandSender sender, final Command command, final String label, final String[] args) {
        sender.sendMessage(ChatColor.RED + "NOT YET IMPLEMENTED");
        return true;
    }
    
    private boolean warn(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 3) {
            sender.sendMessage(this.FEIL_SYNTAX);
            return true;
        }
        final IPlayerData pd = this.findPlayer(sender, args[3]);
        if (pd == null) {
            return true;
        }
        if (args[1].equalsIgnoreCase("add")) {
            if (args.length <= 5) {
                sender.sendMessage(this.FEIL_SYNTAX);
                return true;
            }
            int level = -1;
            try {
                level = Integer.parseInt(args[4]);
            }
            catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Syntax feil: " + args[3] + " er ikke et tall!");
            }
            return true;
        }
        else {
            if (args[1].equalsIgnoreCase("see")) {
                final int length = args.length;
                return true;
            }
            return !args[1].equalsIgnoreCase("del") || true;
        }
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
