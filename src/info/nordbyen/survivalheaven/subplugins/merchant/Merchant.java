package info.nordbyen.survivalheaven.subplugins.merchant;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;

public class Merchant extends SubPlugin
{
    public Merchant(final String name) {
        super(name);
    }
    
    public void disable() {
    }
    
    public void enable() {
        Bukkit.getPluginManager().registerEvents((Listener)new MerchantListener(), (Plugin)this.getPlugin());
    }
    
    public class MerchantListener implements Listener
    {
    }
}
