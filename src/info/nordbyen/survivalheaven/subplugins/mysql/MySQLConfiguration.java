package info.nordbyen.survivalheaven.subplugins.mysql;

import info.nordbyen.survivalheaven.api.config.*;
import java.io.*;

public class MySQLConfiguration extends CustomConfiguration
{
    private static MySQLConfiguration cfg;
    
    public static String getDatabaseName() {
        getInstance().reload();
        return getInstance().getString("database");
    }
    
    public static String getHostName() {
        getInstance().reload();
        return getInstance().getString("host");
    }
    
    public static int getHostPort() {
        getInstance().reload();
        return getInstance().getInt("port");
    }
    
    public static MySQLConfiguration getInstance() {
        if (MySQLConfiguration.cfg == null) {
            MySQLConfiguration.cfg = new MySQLConfiguration();
        }
        return MySQLConfiguration.cfg;
    }
    
    public static String getUserName() {
        getInstance().reload();
        return getInstance().getString("user");
    }
    
    public static String getUserPassword() {
        getInstance().reload();
        return getInstance().getString("pass");
    }
    
    public MySQLConfiguration() {
        super(new File("./plugins/SurvivalHeaven/mysql.yml"));
        (MySQLConfiguration.cfg = this).load();
        this.save();
        this.saveDefault();
    }
    
    private void saveDefault() {
        if (!this.contains("host")) {
            this.set("host", (Object)"localhost");
        }
        if (!this.contains("port")) {
            this.set("port", (Object)3306);
        }
        if (!this.contains("database")) {
            this.set("database", (Object)"SurvivalHeaven");
        }
        if (!this.contains("user")) {
            this.set("user", (Object)"root");
        }
        if (!this.contains("pass")) {
            this.set("pass", (Object)"");
        }
        this.save();
    }
}
