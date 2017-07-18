package info.nordbyen.survivalheaven.subplugins.remote;

import java.net.*;
import java.io.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;

public class ConnectionHandler extends Thread
{
    private final RemoteBukkitPlugin plugin;
    private final int number;
    private final Socket socket;
    private final PrintStream out;
    private Directive directive;
    private volatile boolean killed;
    
    public ConnectionHandler(final RemoteBukkitPlugin plugin, final int number, final Socket socket) throws IOException {
        super("RemoteBukkit-ConnectionHandler");
        this.killed = false;
        this.setDaemon(true);
        this.plugin = plugin;
        this.number = number;
        this.socket = socket;
        this.out = new PrintStream(socket.getOutputStream());
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public void kill() {
        if (this.killed) {
            return;
        }
        this.killed = true;
        this.plugin.didCloseConnection(this);
        try {
            this.socket.close();
        }
        catch (IOException ex) {}
    }
    
    public void kill(final String reason) {
        this.directive = Directive.INTERACTIVE;
        this.send("\nRemoteBukkit closing connection because:");
        this.send(reason);
        this.kill();
    }
    
    @Override
    public void run() {
        RemoteBukkitPlugin.log("Connection #" + this.number + " from " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + " was accepted.");
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            final String user = in.readLine();
            final String pass = in.readLine();
            if (user == null || pass == null) {
                throw new IOException("Connection terminated before all credentials could be sent!");
            }
            if (this.plugin.areValidCredentials(user, pass)) {
                final String raw = in.readLine();
                if (raw == null) {
                    throw new IOException("Connection terminated before connection directive could be recieved!");
                }
                this.directive = Directive.toDirective(raw);
                if (this.directive == null) {
                    RemoteBukkitPlugin.log("Connection #" + this.number + " from " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + " requested the use of an unsupported directive (\"" + raw + "\").");
                    this.kill("Unsported directive \"" + raw + "\".");
                }
                else {
                    this.plugin.didEstablishConnection(this, this.directive);
                    while (true) {
                        final String input = in.readLine();
                        if (input == null) {
                            break;
                        }
                        if (this.plugin.doVerboseLogging()) {
                            RemoteBukkitPlugin.log("Connection #" + this.number + " from " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + " dispatched command: " + input);
                        }
                        this.plugin.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin.getPlugin(), (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                ConnectionHandler.this.plugin.getPlugin().getServer().dispatchCommand((CommandSender)ConnectionHandler.this.plugin.getPlugin().getServer().getConsoleSender(), input);
                            }
                        });
                    }
                }
            }
            else {
                RemoteBukkitPlugin.log("Connection #" + this.number + " from " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + " attempted to authenticate using incorrect credentials.");
                this.kill("Incorrect credentials.");
            }
        }
        catch (IOException ex) {
            RemoteBukkitPlugin.log("Connection #" + this.number + " from " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + " abruptly closed the connection during authentication.");
        }
        this.kill();
    }
    
    public void send(final String msg) {
        if (this.directive != Directive.NOLOG) {
            this.out.println(msg);
        }
    }
}
