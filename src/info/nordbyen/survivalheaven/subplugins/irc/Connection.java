package info.nordbyen.survivalheaven.subplugins.irc;

import java.util.concurrent.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Connection implements Runnable
{
    public static final Object mutex;
    private final Socket socket;
    private String username;
    private String hostname;
    private String nick;
    private String description;
    public static Map<String, Connection> connectionMap;
    public static Map<String, Channel> channelMap;
    private static String globalServerName;
    private LinkedBlockingQueue<String> outQueue;
    private final Thread outThread;
    
    static {
        mutex = new Object();
        Connection.connectionMap = new HashMap<String, Connection>();
        Connection.channelMap = new HashMap<String, Channel>();
    }
    
    public static String delimited(final String[] items, final String delimiter) {
        final StringBuffer response = new StringBuffer();
        boolean first = true;
        for (final String s : items) {
            if (first) {
                first = false;
            }
            else {
                response.append(delimiter);
            }
            response.append(s);
        }
        return response.toString();
    }
    
    public static String filterAllowedNick(final String theNick) {
        return theNick.replace(":", "").replace(" ", "").replace("!", "").replace("@", "").replace("#", "");
    }
    
    public static void main(final String[] args) throws Throwable {
        if (args.length == 0) {
            System.out.println("Usage: java jw.jircs.Connection <servername>");
            return;
        }
        Connection.globalServerName = args[0];
        try {
            Throwable t = null;
            try {
                final ServerSocket ss = new ServerSocket(6667);
                try {
                    while (true) {
                        final Socket s = ss.accept();
                        final Connection jircs = new Connection(s);
                        final Thread thread = new Thread(jircs);
                        thread.start();
                    }
                }
                finally {
                    if (ss != null) {
                        ss.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable t2;
                    t = t2;
                }
                else {
                    final Throwable t2;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void startServer(final String name) throws Throwable {
        Connection.globalServerName = name;
        try {
            Throwable t = null;
            try {
                final ServerSocket ss = new ServerSocket(6667);
                try {
                    while (true) {
                        final Socket s = ss.accept();
                        final Connection jircs = new Connection(s);
                        final Thread thread = new Thread(jircs);
                        thread.start();
                    }
                }
                finally {
                    if (ss != null) {
                        ss.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable t2;
                    t = t2;
                }
                else {
                    final Throwable t2;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public Connection(final Socket socket) {
        this.outQueue = new LinkedBlockingQueue<String>(1000);
        this.outThread = new Thread() {
            @Override
            public void run() {
                try {
                    final OutputStream out = Connection.this.socket.getOutputStream();
                    while (true) {
                        String s = Connection.this.outQueue.take();
                        s = s.replace("\n", "").replace("\r", "");
                        s = String.valueOf(s) + "\r\n";
                        out.write(s.getBytes());
                        out.flush();
                    }
                }
                catch (Exception e) {
                    System.out.println("Outqueue died");
                    Connection.this.outQueue.clear();
                    Connection.access$2(Connection.this, null);
                    e.printStackTrace();
                    try {
                        Connection.this.socket.close();
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        this.socket = socket;
    }
    
    private void doServer() throws Exception {
        final InetSocketAddress address = (InetSocketAddress)this.socket.getRemoteSocketAddress();
        this.hostname = address.getAddress().getHostAddress();
        System.out.println("Connection from host " + this.hostname);
        this.outThread.start();
        final InputStream socketIn = this.socket.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socketIn));
        String line;
        while ((line = reader.readLine()) != null) {
            this.processLine(line);
        }
    }
    
    public String getRepresentation() {
        return String.valueOf(this.nick) + "!" + this.username + "@" + this.hostname;
    }
    
    private String[] padSplit(final String line, final String regex, final int max) {
        final String[] split = line.split(regex);
        final String[] output = new String[max];
        for (int i = 0; i < output.length; ++i) {
            output[i] = "";
        }
        for (int i = 0; i < split.length; ++i) {
            output[i] = split[i];
        }
        return output;
    }
    
    private void processLine(String line) throws Exception {
        System.out.println("Processing line from " + this.nick + ": " + line);
        String prefix = "";
        if (line.startsWith(":")) {
            final String[] tokens = line.split(" ", 2);
            prefix = tokens[0];
            line = ((tokens.length > 1) ? tokens[1] : "");
        }
        final String[] tokens2 = line.split(" ", 2);
        String command = tokens2[0];
        line = ((tokens2.length > 1) ? tokens2[1] : "");
        final String[] tokens3 = line.split("(^| )\\:", 2);
        String trailing = null;
        line = tokens3[0];
        if (tokens3.length > 1) {
            trailing = tokens3[1];
        }
        final ArrayList<String> argumentList = new ArrayList<String>();
        if (!line.equals("")) {
            argumentList.addAll(Arrays.asList(line.split(" ")));
        }
        if (trailing != null) {
            argumentList.add(trailing);
        }
        final String[] arguments = argumentList.toArray(new String[0]);
        if (command.matches("[0-9][0-9][0-9]")) {
            command = "n" + command;
        }
        Command commandObject = null;
        try {
            Command.valueOf(command.toLowerCase());
        }
        catch (Exception ex) {}
        if (commandObject == null) {
            try {
                commandObject = Command.valueOf(command.toUpperCase());
            }
            catch (Exception ex2) {}
        }
        if (commandObject == null) {
            this.sendSelfNotice("That command (" + command + ") isnt a supported command at this server.");
            return;
        }
        if (arguments.length < commandObject.getMin() || arguments.length > commandObject.getMax()) {
            this.sendSelfNotice("Invalid number of arguments for this command, expected not more than " + commandObject.getMax() + " and not less than " + commandObject.getMin() + " but got " + arguments.length + " arguments");
            return;
        }
        commandObject.run(this, prefix, arguments);
    }
    
    @Override
    public void run() {
        try {
            this.doServer();
        }
        catch (Exception e) {
            try {
                this.socket.close();
            }
            catch (Exception ex) {}
            e.printStackTrace();
            return;
        }
        finally {
            if (this.nick != null && Connection.connectionMap.get(this.nick) == this) {
                this.sendQuit("Client disconnected");
            }
        }
        if (this.nick != null && Connection.connectionMap.get(this.nick) == this) {
            this.sendQuit("Client disconnected");
        }
    }
    
    public void send(final String s) {
        final Queue<String> testQueue = this.outQueue;
        if (testQueue != null) {
            System.out.println("Sending line to " + this.nick + ": " + s);
            testQueue.add(s);
        }
    }
    
    protected void sendGlobal(final String string) {
        this.send(":" + Connection.globalServerName + " " + string);
    }
    
    protected void sendQuit(final String quitMessage) {
        synchronized (Connection.mutex) {
            for (final String channelName : new ArrayList<String>(Connection.channelMap.keySet())) {
                final Channel channel = Connection.channelMap.get(channelName);
                channel.channelMembers.remove(this);
                channel.send(":" + this.getRepresentation() + " QUIT :" + quitMessage);
                if (channel.channelMembers.size() == 0) {
                    Connection.channelMap.remove(channel.name);
                }
            }
        }
        // monitorexit(Connection.mutex)
    }
    
    private void sendSelfNotice(final String string) {
        this.send(":" + Connection.globalServerName + " NOTICE " + this.nick + " :" + string);
    }
    
    static /* synthetic */ void access$2(final Connection connection, final LinkedBlockingQueue outQueue) {
        connection.outQueue = (LinkedBlockingQueue<String>)outQueue;
    }
    
    static /* synthetic */ void access$3(final Connection connection, final String nick) {
        connection.nick = nick;
    }
    
    static /* synthetic */ void access$7(final Connection connection, final String username) {
        connection.username = username;
    }
    
    static /* synthetic */ void access$8(final Connection connection, final String description) {
        connection.description = description;
    }
    
    public enum Command
    {
        NICK(0, 1, 1) {
            private void doFirstTimeNick(final Connection con, final String nick) throws InterruptedException {
                Connection.access$3(con, Connection.filterAllowedNick(nick));
                synchronized (Connection.mutex) {
                    Connection.connectionMap.put(con.nick, con);
                }
                // monitorexit(Connection.mutex)
            }
            
            private void doSelfSwitchNick(final Connection con, final String nick) {
                synchronized (Connection.mutex) {
                    final String oldNick = con.nick;
                    Connection.access$3(con, Connection.filterAllowedNick(nick));
                    Connection.connectionMap.remove(oldNick);
                    Connection.connectionMap.put(con.nick, con);
                    con.send(":" + oldNick + "!" + con.username + "@" + con.hostname + " NICK :" + con.nick);
                    for (final Channel c : Connection.channelMap.values()) {
                        if (c.channelMembers.contains(con)) {
                            c.sendNot(con, ":" + oldNick + "!" + con.username + "@" + con.hostname + " NICK :" + con.nick);
                        }
                    }
                }
                // monitorexit(Connection.mutex)
            }
            
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                if (con.nick == null) {
                    this.doFirstTimeNick(con, arguments[0]);
                }
                else {
                    this.doSelfSwitchNick(con, arguments[0]);
                }
            }
        }, 
        USER(1, 1, 4) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                if (con.username != null) {
                    con.send("NOTICE AUTH :You can't change your user information after you've logged in right now.");
                    return;
                }
                Connection.access$7(con, arguments[0]);
                final String forDescription = (arguments.length > 3) ? arguments[3] : "(no description)";
                Connection.access$8(con, forDescription);
                con.sendGlobal("001 " + con.nick + " :Welcome to " + Connection.globalServerName + ", a Jircs-powered IRC network.");
                con.sendGlobal("004 " + con.nick + " " + Connection.globalServerName + " Jircs");
                con.sendGlobal("375 " + con.nick + " :- " + Connection.globalServerName + " Message of the Day -");
                con.sendGlobal("372 " + con.nick + " :- Hello. Welcome to " + Connection.globalServerName + ", a Jircs-powered IRC network.");
                con.sendGlobal("372 " + con.nick + " :- See http://code.google.com/p/jwutils/wiki/Jircs " + "for more info on Jircs.");
                con.sendGlobal("376 " + con.nick + " :End of /MOTD command.");
            }
        }, 
        PING(2, 1, 1) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                con.send(":" + Connection.globalServerName + " PONG " + Connection.globalServerName + " :" + arguments[0]);
            }
        }, 
        JOIN(3, 1, 2) {
            public void doJoin(final Connection con, final String channelName) {
                if (!channelName.startsWith("#")) {
                    con.sendSelfNotice("This server only allows channel names that start with a # sign.");
                    return;
                }
                if (channelName.contains(" ")) {
                    con.sendSelfNotice("This server does not allow spaces in channel names.");
                }
                synchronized (Connection.mutex) {
                    Channel channel = Connection.channelMap.get(channelName);
                    boolean added = false;
                    if (channel == null) {
                        added = true;
                        channel = new Channel();
                        channel.name = channelName;
                        Connection.channelMap.put(channelName, channel);
                    }
                    if (channel.channelMembers.contains(con)) {
                        con.sendSelfNotice("You're already a member of " + channelName);
                        // monitorexit(Connection.mutex)
                        return;
                    }
                    channel.channelMembers.add(con);
                    channel.send(":" + con.getRepresentation() + " JOIN " + channelName);
                    if (added) {
                        con.sendGlobal("MODE " + channelName + " +nt");
                    }
                    if (channel.topic != null) {
                        con.sendGlobal("332 " + con.nick + " " + channel.name + " :" + channel.topic);
                    }
                    else {
                        con.sendGlobal("331 " + con.nick + " " + channel.name + " :No topic is set");
                    }
                    for (final Connection channelMember : channel.channelMembers) {
                        con.sendGlobal("353 " + con.nick + " = " + channelName + " :" + channelMember.nick);
                    }
                    con.sendGlobal("366 " + con.nick + " " + channelName + " :End of /NAMES list");
                }
                // monitorexit(Connection.mutex)
            }
            
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                if (arguments.length == 2) {
                    con.sendSelfNotice("This server does not support channel keys at this time. JOIN will act as if you hadn't specified any keys.");
                }
                final String[] channelNames = arguments[0].split(",");
                String[] array;
                for (int length = (array = channelNames).length, i = 0; i < length; ++i) {
                    final String channelName = array[i];
                    if (!channelName.startsWith("#")) {
                        con.sendSelfNotice("This server only allows channel names that start with a # sign.");
                        return;
                    }
                    if (channelName.contains(" ")) {
                        con.sendSelfNotice("This server does not allow spaces in channel names.");
                        return;
                    }
                }
                String[] array2;
                for (int length2 = (array2 = channelNames).length, j = 0; j < length2; ++j) {
                    final String channelName = array2[j];
                    this.doJoin(con, channelName);
                }
            }
        }, 
        WHO(4, 0, 2) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                if (arguments.length > 1) {
                    con.sendSelfNotice("Filtering by operator only using the WHO command isn't yet supported. WHO will act as if \"o\" has not been specified.");
                }
                String person = "";
                if (arguments.length > 0) {
                    person = arguments[0];
                }
                synchronized (Connection.mutex) {
                    final Channel channel = Connection.channelMap.get(person);
                    if (channel != null) {
                        for (final Connection channelMember : channel.channelMembers) {
                            con.sendGlobal("352 " + con.nick + " " + person + " " + channelMember.username + " " + channelMember.hostname + " " + Connection.globalServerName + " " + channelMember.nick + " H :0 " + channelMember.description);
                        }
                    }
                    else {
                        con.sendSelfNotice("WHO with something other than a channel as arguments is not supported right now. WHO will display an empty list of people.");
                    }
                }
                // monitorexit(Connection.mutex)
                con.send("315 " + con.nick + " " + person + " :End of /WHO list.");
            }
        }, 
        USERHOST(5, 1, 5) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                final ArrayList<String> replies = new ArrayList<String>();
                for (final String s : arguments) {
                    final Connection user = Connection.connectionMap.get(s);
                    if (user != null) {
                        replies.add(String.valueOf(user.nick) + "=+" + user.username + "@" + user.hostname);
                    }
                }
                con.sendGlobal("302 " + con.nick + " :" + Connection.delimited(replies.toArray(new String[0]), " "));
            }
        }, 
        MODE(6, 0, 2) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                if (arguments.length == 1) {
                    if (arguments[0].startsWith("#")) {
                        con.sendGlobal("324 " + con.nick + " " + arguments[0] + " +nt");
                    }
                    else {
                        con.sendSelfNotice("User mode querying not supported yet.");
                    }
                }
                else if (arguments.length == 2 && (arguments[1].equals("+b") || arguments[1].equals("+e"))) {
                    if (arguments[0].startsWith("#")) {
                        if (arguments[1].equals("+b")) {
                            con.sendGlobal("368 " + con.nick + " " + arguments[0] + " :End of channel ban list");
                        }
                        else {
                            con.sendGlobal("349 " + con.nick + " " + arguments[0] + " :End of channel exception list");
                        }
                    }
                    else {
                        con.sendSelfNotice("User mode setting not supported yet for +b or +e.");
                    }
                }
                else {
                    con.sendSelfNotice("Specific modes not supported yet.");
                }
            }
        }, 
        PART(7, 1, 2) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                final String[] channels = arguments[0].split(",");
                String[] array;
                for (int length = (array = channels).length, i = 0; i < length; ++i) {
                    final String channelName = array[i];
                    synchronized (Connection.mutex) {
                        final Channel channel = Connection.channelMap.get(channelName);
                        if (channelName == null) {
                            con.sendSelfNotice("You're not a member of the channel " + channelName + ", so you can't part it.");
                        }
                        else {
                            channel.send(":" + con.getRepresentation() + " PART " + channelName);
                            channel.channelMembers.remove(con);
                            if (channel.channelMembers.size() == 0) {
                                Connection.channelMap.remove(channelName);
                            }
                        }
                    }
                    // monitorexit(Connection.mutex)
                }
            }
        }, 
        QUIT(8, 1, 1) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                con.sendQuit("Quit: " + arguments[0]);
            }
        }, 
        PRIVMSG(9, 2, 2) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                final String[] recipients = arguments[0].split(",");
                final String message = arguments[1];
                String[] array;
                for (int length = (array = recipients).length, i = 0; i < length; ++i) {
                    final String recipient = array[i];
                    if (recipient.startsWith("#")) {
                        final Channel channel = Connection.channelMap.get(recipient);
                        if (channel == null) {
                            con.sendSelfNotice("No such channel, so can't send a message to it: " + recipient);
                        }
                        else if (!channel.channelMembers.contains(con)) {
                            con.sendSelfNotice("You can't send messages to channels you're not at.");
                        }
                        else {
                            channel.sendNot(con, ":" + con.getRepresentation() + " PRIVMSG " + recipient + " :" + message);
                        }
                    }
                    else {
                        final Connection recipientConnection = Connection.connectionMap.get(recipient);
                        if (recipientConnection == null) {
                            con.sendSelfNotice("The user " + recipient + " is not online.");
                        }
                        else {
                            recipientConnection.send(":" + con.getRepresentation() + " PRIVMSG " + recipient + " :" + message);
                        }
                    }
                }
            }
        }, 
        TOPIC(10, 1, 2) {
            @Override
            public void run(final Connection con, final String prefix, final String[] arguments) throws Exception {
                final Channel channel = Connection.channelMap.get(arguments[0]);
                if (channel == null) {
                    con.sendSelfNotice("No such channel for topic viewing: " + arguments[0]);
                    return;
                }
                if (arguments.length == 1) {
                    if (channel.topic != null) {
                        con.sendGlobal("332 " + con.nick + " " + channel.name + " :" + channel.topic);
                    }
                    else {
                        con.sendGlobal("331 " + con.nick + " " + channel.name + " :No topic is set");
                    }
                }
                else {
                    Channel.access$2(channel, arguments[1]);
                    channel.sendNot(con, ":" + con.getRepresentation() + " TOPIC " + channel.name + " :" + channel.topic);
                }
            }
        };
        
        private int minArgumentCount;
        private int maxArgumentCount;
        
        private Command(final String s, final int n, final int min, final int max) {
            this.minArgumentCount = min;
            this.maxArgumentCount = max;
        }
        
        public int getMax() {
            return this.maxArgumentCount;
        }
        
        public int getMin() {
            return this.minArgumentCount;
        }
        
        public abstract void run(final Connection p0, final String p1, final String[] p2) throws Exception;
    }
    
    public static class Channel
    {
        private final ArrayList<Connection> channelMembers;
        private String topic;
        protected String name;
        
        public Channel() {
            this.channelMembers = new ArrayList<Connection>();
        }
        
        public void memberQuit(final String nick) {
        }
        
        public void send(final String toSend) {
            this.sendNot(null, toSend);
        }
        
        public void sendNot(final Connection not, final String toSend) {
            synchronized (Connection.mutex) {
                for (final Connection con : this.channelMembers) {
                    if (con != not) {
                        con.send(toSend);
                    }
                }
            }
            // monitorexit(Connection.mutex)
        }
        
        static /* synthetic */ void access$2(final Channel channel, final String topic) {
            channel.topic = topic;
        }
    }
}
