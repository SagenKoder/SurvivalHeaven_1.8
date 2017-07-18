package info.nordbyen.survivalheaven.subplugins.remote;

import java.util.logging.*;
import java.text.*;
import java.util.*;

public class LogHandler extends Handler
{
    private final RemoteBukkitPlugin plugin;
    
    public LogHandler(final RemoteBukkitPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void close() throws SecurityException {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public synchronized void publish(final LogRecord record) {
        this.plugin.broadcast(String.valueOf(new SimpleDateFormat("hh:mm a").format(new Date(record.getMillis()))) + " [" + record.getLevel().toString() + "] " + record.getMessage());
    }
}
