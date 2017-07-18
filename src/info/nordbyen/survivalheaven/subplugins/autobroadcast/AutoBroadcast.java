package info.nordbyen.survivalheaven.subplugins.autobroadcast;

import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import java.util.*;

public class AutoBroadcast extends AbstractCommand
{
    private static final String MESSAGE_NO_PERMISSION;
    private static final long TICKS_BETWEEN_BROADCASTS = 12000L;
    private String message;
    private boolean running;
    
    static {
        MESSAGE_NO_PERMISSION = ChatColor.RED + "Du har ikke nok rettigheter til \u00e5 gj\u00f8re dette!";
    }
    
    public AutoBroadcast() {
        super("bc");
        this.message = "";
        this.running = false;
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String arg2, final String[] args) {
        if (!sender.hasPermission("sh.autobroadcast") && !sender.isOp()) {
            sender.sendMessage(AutoBroadcast.MESSAGE_NO_PERMISSION);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "AutoBroadcast: /bc start, /bc stopp, /bc tekst <tekst>");
            return true;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (this.message.equals("")) {
                sender.sendMessage(ChatColor.RED + "Ingen melding er satt!");
                return true;
            }
            this.running = true;
            Bukkit.broadcastMessage(this.message);
            this.scheduleNextMessage();
            sender.sendMessage(ChatColor.GREEN + "Denne meldingen vil n\u00e5 bli sendt hvert 10. minutt til noen skriver /bc stopp");
            return true;
        }
        else {
            if (args[0].equalsIgnoreCase("stopp")) {
                final boolean running = false;
                this.running = running;
                if (running) {
                    sender.sendMessage(ChatColor.RED + "Du kan ikke stoppe den n\u00e5r den ikke kj\u00f8rer");
                    return true;
                }
                this.running = false;
                sender.sendMessage(ChatColor.GREEN + "Stopper broadcasteren");
            }
            if (!args[0].equalsIgnoreCase("tekst")) {
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Du m\u00e5 skrive en melding!");
                return true;
            }
            final StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; ++i) {
                sb.append(String.valueOf(args[i]) + " ");
            }
            this.message = sb.toString();
            sender.sendMessage(ChatColor.GREEN + "Du satte meldingen til: " + this.message);
            return true;
        }
    }
    
    public void scheduleNextMessage() {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                if (!AutoBroadcast.this.running) {
                    return;
                }
                Bukkit.broadcastMessage(AutoBroadcast.this.message);
                AutoBroadcast.this.scheduleNextMessage();
            }
        }, 12000L);
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
}
