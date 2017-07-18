package info.nordbyen.survivalheaven.api.util;

import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import java.util.*;

public class EventAPI
{
    private static HashMap<Listener, Plugin> events;
    
    public EventAPI() {
        EventAPI.events = new HashMap<Listener, Plugin>();
    }
    
    public void addEvent(final Listener listener, final Plugin plugin) {
        EventAPI.events.put(listener, plugin);
    }
    
    public void registerEvent(final Listener listener, final Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
    
    public void registerEvents() {
        for (final Listener listener : EventAPI.events.keySet()) {
            Bukkit.getPluginManager().registerEvents(listener, (Plugin)EventAPI.events.get(listener));
        }
    }
}
