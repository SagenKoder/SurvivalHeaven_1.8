package info.nordbyen.survivalheaven.subplugins.remote;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.apache.logging.log4j.*;
import java.util.logging.*;
import java.io.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

public class RemoteBukkitPlugin extends SubPlugin
{
    private static final Logger log;
    private static final org.apache.logging.log4j.core.Logger logger;
    private static final ArrayList<String> oldMsgs;
    private boolean verbose;
    private final ArrayList<ConnectionHandler> connections;
    private final ArrayList<User> users;
    private LogAppender appender;
    private ConnectionListener listener;
    private int logsize;
    
    static {
        log = Logger.getLogger("Minecraft-Server");
        logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
        oldMsgs = new ArrayList<String>();
    }
    
    public static void log(final String msg) {
        RemoteBukkitPlugin.log.log(Level.INFO, "[REMOTE] " + msg);
    }
    
    public static void log(final String msg, final IOException ex) {
        RemoteBukkitPlugin.log.log(Level.INFO, "[REMOTE] " + msg, ex);
    }
    
    public RemoteBukkitPlugin(final String name) {
        super(name);
        this.connections = new ArrayList<ConnectionHandler>();
        this.users = new ArrayList<User>();
    }
    
    public boolean areValidCredentials(final String username, final String password) {
        for (final User user : this.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    public void broadcast(final String msg) {
        synchronized (RemoteBukkitPlugin.oldMsgs) {
            RemoteBukkitPlugin.oldMsgs.add(msg);
            if (RemoteBukkitPlugin.oldMsgs.size() > this.logsize) {
                RemoteBukkitPlugin.oldMsgs.remove((this.logsize != 0) ? 1 : 0);
            }
        }
        // monitorexit(RemoteBukkitPlugin.oldMsgs)
        for (final ConnectionHandler con : new ArrayList<ConnectionHandler>(this.connections)) {
            con.send(msg);
        }
    }
    
    public void didCloseConnection(final ConnectionHandler con) {
        log("Connection #" + con.getNumber() + " from " + con.getSocket().getInetAddress().getHostAddress() + ":" + con.getSocket().getPort() + " was closed.");
        this.connections.remove(con);
    }
    
    public void didEstablishConnection(final ConnectionHandler con, final Directive directive) {
        log("Connection #" + con.getNumber() + " from " + con.getSocket().getInetAddress().getHostAddress() + ":" + con.getSocket().getPort() + " was successfully established.");
        this.connections.add(con);
        if (directive == Directive.NOLOG) {
            con.send("Connection was successfully established.");
        }
        else {
            synchronized (RemoteBukkitPlugin.oldMsgs) {
                for (final String msg : RemoteBukkitPlugin.oldMsgs) {
                    con.send(msg);
                }
            }
            // monitorexit(RemoteBukkitPlugin.oldMsgs)
        }
    }
    
    public void disable() {
        RemoteBukkitPlugin.logger.removeAppender((Appender)this.appender);
        this.listener.kill();
        for (final ConnectionHandler con : new ArrayList<ConnectionHandler>(this.connections)) {
            con.kill("Plugin is being disabled!");
        }
    }
    
    public boolean doVerboseLogging() {
        return this.verbose;
    }
    
    public void enable() {
        this.appender = new LogAppender(this);
        RemoteBukkitPlugin.logger.addAppender((Appender)this.appender);
        int port = 25564;
        try {
            int num = 0;
            ArrayList<Map<String, String>> usersSection = null;
            try {
                usersSection = (ArrayList<Map<String, String>>)RemoteBukkitConfig.getInstance().getList("users");
            }
            catch (Exception ex2) {}
            if (usersSection != null) {
                for (final Map<String, String> entry : usersSection) {
                    ++num;
                    try {
                        final Object rawUsername = entry.get("user");
                        String username;
                        if (rawUsername instanceof String) {
                            username = (String)rawUsername;
                        }
                        else {
                            if (!(rawUsername instanceof Integer)) {
                                RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] Illegal or no username specified for entry #" + num + ", defaulting to \"username\"");
                                continue;
                            }
                            username = ((Integer)rawUsername).toString();
                        }
                        final Object rawPassword = entry.get("pass");
                        String password;
                        if (rawPassword instanceof String) {
                            password = (String)rawPassword;
                        }
                        else {
                            if (!(rawPassword instanceof Integer)) {
                                RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] Illegal or no password specified for entry #" + num + ", defaulting to \"password\"");
                                continue;
                            }
                            password = ((Integer)rawPassword).toString();
                        }
                        this.users.add(new User(username, password));
                    }
                    catch (Exception e) {
                        RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] Could not parse user entry #" + num + ", ignoring it (this entry will be deleted).");
                    }
                }
            }
            else {
                System.out.println("NULLL! OMGPOY!");
            }
            if (this.users.isEmpty()) {
                RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] No user entries could be successfully parsed or no entries were provided. A default entry has been added (username = \"username\", password = \"password\").");
                this.users.add(new User("username", "password"));
            }
            port = RemoteBukkitConfig.getInstance().getInt("port");
            if (port <= 1024) {
                RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] Illegal or no port specified (must be greater than 1024), using default port 25564");
                port = 25564;
            }
            this.verbose = RemoteBukkitConfig.getInstance().getBoolean("verbose");
            final Object logsizeRaw = RemoteBukkitConfig.getInstance().get("logsize");
            if (logsizeRaw instanceof Integer) {
                this.logsize = (int)logsizeRaw;
            }
            else {
                RemoteBukkitPlugin.log.log(Level.WARNING, "[REMOTE] Illegal or no maximum logsize specified (must be greater than or equal to 0), defaulting to \"500\"");
                this.logsize = 500;
            }
        }
        catch (Exception ex) {
            RemoteBukkitPlugin.log.log(Level.SEVERE, "[REMOTE] Fatal error while parsing configuration file. The defaults have been assumed. PLEASE REPORT THIS ERROR AS AN ISSUE AT: http://github.com/escortkeel/RemoteBukkit/issues", ex);
            this.users.clear();
            this.users.add(new User("username", "password"));
            this.logsize = 500;
        }
        (this.listener = new ConnectionListener(this, port)).start();
        this.getPlugin().saveConfig();
    }
}
