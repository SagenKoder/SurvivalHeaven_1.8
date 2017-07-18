package info.nordbyen.survivalheaven.subplugins.remote;

import java.io.*;
import java.net.*;

public class ConnectionListener extends Thread
{
    private final RemoteBukkitPlugin plugin;
    private final ServerSocket s;
    private int number;
    
    public ConnectionListener(final RemoteBukkitPlugin plugin, final int port) {
        super("RemoteBukkit-ConnectionListener");
        this.number = 0;
        this.setDaemon(true);
        this.plugin = plugin;
        try {
            this.s = new ServerSocket(port);
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to listen on port:" + port, ex);
        }
    }
    
    public void kill() {
        try {
            this.s.close();
        }
        catch (IOException ex) {}
    }
    
    @Override
    public void run() {
        while (!this.s.isClosed()) {
            Socket socket = null;
            try {
                socket = this.s.accept();
                final ConnectionHandler con = new ConnectionHandler(this.plugin, this.number++, socket);
                con.start();
            }
            catch (IOException ex) {
                if (socket == null) {
                    continue;
                }
                RemoteBukkitPlugin.log("Exception while attempting to accept connection #" + (this.number - 1) + " from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), ex);
            }
        }
    }
}
