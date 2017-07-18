package info.nordbyen.survivalheaven.api.config;

import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;
import java.util.logging.*;
import org.bukkit.configuration.*;
import java.io.*;

public class CustomConfiguration extends YamlConfiguration
{
    private final File configFile;
    
    public CustomConfiguration(final File file) {
        this.configFile = file;
        this.load();
    }
    
    public void load() {
        try {
            super.load(this.configFile);
        }
        catch (FileNotFoundException e3) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not find " + this.configFile.getName() + ", creating new one...");
            this.reload();
        }
        catch (IOException e) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not load " + this.configFile.getName(), e);
        }
        catch (InvalidConfigurationException e2) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, String.valueOf(this.configFile.getName()) + " is no valid configuration file", (Throwable)e2);
        }
    }
    
    public boolean loadRessource(final File file) {
        boolean out = true;
        if (!file.exists()) {
            final InputStream fis = this.getClass().getResourceAsStream("/" + file.getName());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                final byte[] buf = new byte[1024];
                int i = 0;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            }
            catch (Exception e) {
                Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Failed to load config from JAR");
                out = false;
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
                catch (Exception ex) {}
            }
            finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
                catch (Exception ex2) {}
            }
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch (Exception ex3) {}
        }
        return out;
    }
    
    public boolean reload() {
        boolean out = true;
        if (!this.configFile.exists()) {
            out = this.loadRessource(this.configFile);
        }
        if (out) {
            this.load();
        }
        return out;
    }
    
    public void save() {
        try {
            super.save(this.configFile);
        }
        catch (IOException ex) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + this.configFile.getName(), ex);
        }
    }
}
