package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.api.config.*;
import org.bukkit.*;
import java.io.*;

public class FirstEncounterConfig extends CustomConfiguration
{
    private static FirstEncounterConfig cfg;
    
    public static Location getDoor1_1() {
        return new Location(Bukkit.getWorld(getInstance().getString("quest1.door1.world")), getInstance().getDouble("quest1.door1.1.x"), getInstance().getDouble("quest1.door1.1.y"), getInstance().getDouble("quest1.door1.1.z"));
    }
    
    public static Location getDoor1_2() {
        return new Location(Bukkit.getWorld(getInstance().getString("quest1.door1.world")), getInstance().getDouble("quest1.door1.2.x"), getInstance().getDouble("quest1.door1.2.y"), getInstance().getDouble("quest1.door1.2.z"));
    }
    
    public static FirstEncounterConfig getInstance() {
        if (FirstEncounterConfig.cfg == null) {
            FirstEncounterConfig.cfg = new FirstEncounterConfig();
        }
        return FirstEncounterConfig.cfg;
    }
    
    private FirstEncounterConfig() {
        super(new File("./plugins/SurvivalHeaven/questconfig/quest1.yml"));
        (FirstEncounterConfig.cfg = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        if (!this.contains("quest1.door1.world")) {
            this.set("quest1.door1.world", (Object)"NyVerden");
        }
        if (!this.contains("quest1.door1.1.x")) {
            this.set("quest1.door1.1.x", (Object)87);
        }
        if (!this.contains("quest1.door1.1.y")) {
            this.set("quest1.door1.1.y", (Object)62);
        }
        if (!this.contains("quest1.door1.1.z")) {
            this.set("quest1.door1.1.z", (Object)209);
        }
        if (!this.contains("quest1.door1.2.x")) {
            this.set("quest1.door1.2.x", (Object)80);
        }
        if (!this.contains("quest1.door1.2.y")) {
            this.set("quest1.door1.2.y", (Object)65);
        }
        if (!this.contains("quest1.door1.2.z")) {
            this.set("quest1.door1.2.z", (Object)209);
        }
        this.save();
    }
}
