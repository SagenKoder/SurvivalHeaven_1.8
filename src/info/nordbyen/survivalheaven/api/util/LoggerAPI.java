package info.nordbyen.survivalheaven.api.util;

import org.bukkit.plugin.*;
import java.util.logging.*;

public class LoggerAPI
{
    private static LoggerAPI logger;
    
    static {
        LoggerAPI.logger = new LoggerAPI();
    }
    
    public static LoggerAPI getLogger() {
        return LoggerAPI.logger;
    }
    
    public void info(final Plugin plugin, final String message) {
        this.log(plugin, Level.INFO, message);
    }
    
    public void log(final Plugin plugin, final Level level, final String message) {
        plugin.getLogger().log(level, message);
    }
    
    public void severe(final Plugin plugin, final String message) {
        this.log(plugin, Level.SEVERE, message);
    }
    
    public void warn(final Plugin plugin, final String message) {
        this.log(plugin, Level.WARNING, message);
    }
}
