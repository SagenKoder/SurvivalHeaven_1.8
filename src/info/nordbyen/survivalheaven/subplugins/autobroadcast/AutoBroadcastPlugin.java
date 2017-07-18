package info.nordbyen.survivalheaven.subplugins.autobroadcast;

import info.nordbyen.survivalheaven.api.subplugin.*;

public class AutoBroadcastPlugin extends SubPlugin
{
    public AutoBroadcastPlugin(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        new AutoBroadcast();
    }
}
