package info.nordbyen.survivalheaven.subplugins.subplugin;

import info.nordbyen.survivalheaven.api.subplugin.*;
import java.util.*;

public final class SubPluginManager implements ISubPluginManager
{
    private final ArrayList<SubPlugin> subplugins;
    
    public SubPluginManager() {
        this.subplugins = new ArrayList<SubPlugin>();
    }
    
    @Override
    public void addSubPlugin(final SubPlugin plugin) {
        try {
            if (this.subplugins.contains(plugin)) {
                return;
            }
            this.subplugins.add(plugin);
            plugin.enablePlugin();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void disableAll() {
        for (final SubPlugin spl : this.subplugins) {
            try {
                System.out.println("DISABLER: " + spl.getName() + " => " + spl.getClass().getName());
                final long time = System.currentTimeMillis();
                spl.disablePlugin();
                System.out.println("DONE: time=" + (System.currentTimeMillis() - time) + "ms\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void enableAll() {
        for (final SubPlugin spl : this.subplugins) {
            try {
                spl.enablePlugin();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public ArrayList<SubPlugin> getSubplugins() {
        return (ArrayList<SubPlugin>)this.subplugins.clone();
    }
    
    @Override
    public void removeSubPlugin(final SubPlugin plugin) {
        try {
            if (!this.subplugins.contains(plugin)) {
                return;
            }
            this.subplugins.remove(plugin);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void unregisterAll() {
        this.subplugins.clear();
    }
}
