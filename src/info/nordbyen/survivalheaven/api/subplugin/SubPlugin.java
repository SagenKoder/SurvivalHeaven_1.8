package info.nordbyen.survivalheaven.api.subplugin;

import org.bukkit.plugin.java.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;

public abstract class SubPlugin
{
    private final String name;
    private final JavaPlugin plugin;
    private final ISH manager;
    private boolean enabled;
    
    public SubPlugin(final String name) {
        this.name = name;
        this.plugin = SH.getPlugin();
        this.manager = SH.getManager();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Enabling subplugin: " + ChatColor.YELLOW + name);
    }
    
    protected abstract void disable();
    
    public final void disablePlugin() {
        if (!this.enabled) {
            return;
        }
        this.enabled = false;
        this.disable();
    }
    
    protected abstract void enable();
    
    public final void enablePlugin() {
        if (this.enabled) {
            return;
        }
        this.enabled = true;
        this.enable();
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final JavaPlugin getPlugin() {
        return this.plugin;
    }
    
    public final boolean isEnabled() {
        return this.enabled;
    }
}
