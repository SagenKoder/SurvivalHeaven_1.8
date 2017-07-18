package info.nordbyen.survivalheaven.subplugins.remote;

import info.nordbyen.survivalheaven.api.config.*;
import java.io.*;
import java.util.*;

public class RemoteBukkitConfig extends CustomConfiguration
{
    private static RemoteBukkitConfig cfg;
    
    public static RemoteBukkitConfig getInstance() {
        if (RemoteBukkitConfig.cfg == null) {
            RemoteBukkitConfig.cfg = new RemoteBukkitConfig();
        }
        return RemoteBukkitConfig.cfg;
    }
    
    public RemoteBukkitConfig() {
        super(new File("./plugins/SurvivalHeaven/remoteconsole.yml"));
        (RemoteBukkitConfig.cfg = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        if (!this.contains("port")) {
            this.set("port", (Object)"25564");
        }
        if (!this.contains("verbose")) {
            this.set("verbose", (Object)true);
        }
        if (!this.contains("logsize")) {
            this.set("logsize", (Object)500);
        }
        final ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
        if (!this.contains("users")) {
            this.set("user", (Object)users);
        }
        this.save();
    }
}
