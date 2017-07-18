package info.nordbyen.survivalheaven.api.util;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class MessagingAPI
{
    private static MessagingAPI messageAPI;
    
    static {
        MessagingAPI.messageAPI = new MessagingAPI();
    }
    
    public static MessagingAPI getMessenger() {
        return MessagingAPI.messageAPI;
    }
    
    public void broadcast(final int start, final String[] args) {
        final String message = this.buildString(start, args);
        Bukkit.broadcastMessage(message);
    }
    
    public void broadcast(final String message) {
        Bukkit.broadcastMessage(message);
    }
    
    public void broadcast(final String[] args) {
        this.broadcast(0, args);
    }
    
    public String buildString(final int start, final String[] args) {
        final StringBuilder str = new StringBuilder();
        for (int i = start; i < args.length; ++i) {
            str.append(String.valueOf(args[i]) + " ");
        }
        final String message = str.toString();
        return message;
    }
    
    public String buildString(final String[] args) {
        return this.buildString(0, args);
    }
    
    public void deny(final CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You are not allowed to do this!");
    }
    
    public void deny(final CommandSender sender, final String message) {
        sender.sendMessage(ChatColor.RED + message);
    }
    
    public void deny(final Player player) {
        player.sendMessage(ChatColor.RED + "You are not allowed to do this!");
    }
    
    public void deny(final Player player, final String message) {
        player.sendMessage(ChatColor.RED + message);
    }
    
    public String info(final String message) {
        return new StringBuilder().append(ChatColor.GRAY).append(ChatColor.ITALIC).append(message).toString();
    }
    
    public void massSend(final Player[] players, final String message) {
        for (final Player player : players) {
            if (player.isOnline()) {
                player.sendMessage(message);
            }
        }
    }
    
    public void massSend(final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
    
    public void massSend(final String[] players, final String message) {
        for (final String target : players) {
            final Player player = Bukkit.getPlayer(target);
            if (player.isOnline()) {
                player.sendMessage(message);
            }
        }
    }
    
    public void noTarget(final CommandSender sender, final String name) {
        sender.sendMessage(ChatColor.GOLD + name + ChatColor.RED + " was not found!");
    }
    
    public void noTarget(final Player player, final String name) {
        player.sendMessage(ChatColor.GOLD + name + ChatColor.RED + " was not found!");
    }
    
    public void send(final CommandSender sender, final String message) {
        sender.sendMessage(message);
    }
    
    public void send(final Player player, final String message) {
        player.sendMessage(message);
    }
    
    public void send(final String name, final String message) {
        final Player player = Bukkit.getPlayer(name);
        if (player != null) {
            player.sendMessage(message);
        }
    }
    
    public String severe(final String message) {
        return ChatColor.RED + message;
    }
    
    public String warn(final String message) {
        return ChatColor.YELLOW + message;
    }
}
