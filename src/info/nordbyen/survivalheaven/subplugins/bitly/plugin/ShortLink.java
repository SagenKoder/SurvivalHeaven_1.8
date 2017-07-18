package info.nordbyen.survivalheaven.subplugins.bitly.plugin;

import info.nordbyen.survivalheaven.api.subplugin.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class ShortLink extends SubPlugin implements Listener
{
    static Logger log;
    static ShortLink instance;
    private String key;
    private String username;
    
    static {
        ShortLink.log = Bukkit.getLogger();
    }
    
    public ShortLink(final String name) {
        super(name);
    }
    
    public void disable() {
        Bukkit.getScheduler().cancelTasks((Plugin)this.getPlugin());
    }
    
    public void enable() {
        ShortLink.instance = this;
        this.key = "R_402ff904a22447148961043124fab70c";
        this.username = "l0lkj";
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this.getPlugin());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent event) {
    }
}
