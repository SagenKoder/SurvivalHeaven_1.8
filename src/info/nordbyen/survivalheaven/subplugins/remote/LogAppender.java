package info.nordbyen.survivalheaven.subplugins.remote;

import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import java.text.*;
import java.util.*;

public class LogAppender extends AbstractAppender
{
    private final RemoteBukkitPlugin plugin;
    
    public LogAppender(final RemoteBukkitPlugin plugin) {
        super("RemoteController", (Filter)null, (Layout)null);
        this.plugin = plugin;
        this.start();
    }
    
    public void append(final LogEvent event) {
        this.plugin.broadcast(String.valueOf(new SimpleDateFormat("hh:mm a").format(new Date(event.getMillis()))) + " [" + event.getLevel().toString() + "] " + event.getMessage().getFormattedMessage());
    }
}
