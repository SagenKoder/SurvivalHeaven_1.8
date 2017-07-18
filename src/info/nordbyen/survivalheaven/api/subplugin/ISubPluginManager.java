package info.nordbyen.survivalheaven.api.subplugin;

import java.util.*;

public interface ISubPluginManager
{
    void addSubPlugin(final SubPlugin p0);
    
    void disableAll();
    
    void enableAll();
    
    ArrayList<SubPlugin> getSubplugins();
    
    void removeSubPlugin(final SubPlugin p0);
    
    void unregisterAll();
}
