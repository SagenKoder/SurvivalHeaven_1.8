package info.nordbyen.survivalheaven;

import info.nordbyen.survivalheaven.api.config.*;
import java.io.*;

public class SHConfig extends CustomConfiguration
{
    private static SHConfig cfg;
    
    public static void createInstance() {
        if (SHConfig.cfg == null) {
            SHConfig.cfg = new SHConfig();
        }
    }
    
    public static boolean isDebugEnabled() {
        createInstance();
        return SHConfig.cfg.getBoolean("debug");
    }
    
    public SHConfig() {
        super(new File("./plugins/SurvivalHeaven/main-config.yml"));
        (SHConfig.cfg = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        if (!this.contains("debug")) {
            this.set("debug", (Object)false);
        }
        this.save();
    }
}
