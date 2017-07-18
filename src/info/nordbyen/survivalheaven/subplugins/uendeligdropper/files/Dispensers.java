package info.nordbyen.survivalheaven.subplugins.uendeligdropper.files;

import info.nordbyen.survivalheaven.api.config.*;
import java.util.*;
import org.bukkit.*;
import java.io.*;

public class Dispensers extends CustomConfiguration
{
    private static Dispensers dispensers;
    private static List<String> emptyList;
    
    static {
        Dispensers.emptyList = new ArrayList<String>();
    }
    
    public static Dispensers getInstance() {
        if (Dispensers.dispensers == null) {
            Dispensers.dispensers = new Dispensers();
        }
        return Dispensers.dispensers;
    }
    
    public static boolean isDispenser(final Location location) {
        getInstance().reload();
        return getInstance().getList("dispensers").contains(location.toString());
    }
    
    public static void setLocation(final Location location) {
        ((ArrayList)getInstance().getList("dispensers")).add(location.toString());
        getInstance().save();
    }
    
    public Dispensers() {
        super(new File("./plugins/SurvivalHeaven/dispensers.yml"));
        (Dispensers.dispensers = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        if (!this.contains("dispensers")) {
            this.set("dispensers", (Object)Dispensers.emptyList);
            this.save();
        }
    }
}
