package info.nordbyen.survivalheaven.api.command;

import org.bukkit.*;
import java.lang.reflect.*;
import org.bukkit.permissions.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import java.util.*;
import info.nordbyen.survivalheaven.*;

public abstract class AbstractCommand implements TabExecutor
{
    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permMessage;
    protected static CommandMap cmap;
    
    public AbstractCommand(final String command) {
        this(command, null, null, null, null);
    }
    
    public AbstractCommand(final String command, final String usage) {
        this(command, usage, null, null, null);
    }
    
    public AbstractCommand(final String command, final String usage, final String description) {
        this(command, usage, description, null, null);
    }
    
    public AbstractCommand(final String command, final String usage, final String description, final List<String> aliases) {
        this(command, usage, description, null, aliases);
    }
    
    public AbstractCommand(final String command, final String usage, final String description, final String permissionMessage) {
        this(command, usage, description, permissionMessage, null);
    }
    
    public AbstractCommand(final String command, final String usage, final String description, final String permissionMessage, final List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
        this.register();
    }
    
    final CommandMap getCommandMap() {
        if (AbstractCommand.cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                AbstractCommand.cmap = (CommandMap)f.get(Bukkit.getServer());
                return this.getCommandMap();
            }
            catch (Exception e) {
                e.printStackTrace();
                return this.getCommandMap();
            }
        }
        if (AbstractCommand.cmap != null) {
            return AbstractCommand.cmap;
        }
        return this.getCommandMap();
    }
    
    public boolean isAuthorized(final CommandSender sender, final Permission perm) {
        return sender.hasPermission(perm);
    }
    
    public boolean isAuthorized(final CommandSender sender, final String permission) {
        return sender.hasPermission(permission);
    }
    
    public boolean isAuthorized(final Player player, final Permission perm) {
        return player.hasPermission(perm);
    }
    
    public boolean isAuthorized(final Player player, final String permission) {
        return player.hasPermission(permission);
    }
    
    public boolean isPlayer(final CommandSender sender) {
        return sender instanceof Player;
    }
    
    public abstract boolean onCommand(final CommandSender p0, final Command p1, final String p2, final String[] p3);
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final ArrayList<String> players = new ArrayList<String>();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o.getName().toUpperCase().startsWith(args[args.length - 1].toUpperCase())) {
                players.add(o.getName());
            }
        }
        return players;
    }
    
    public void register() {
        ((SH)SH.getManager()).commands.put(this.command, this);
        final ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) {
            cmd.setAliases((List)this.alias);
        }
        if (this.description != null) {
            cmd.setDescription(this.description);
        }
        if (this.usage != null) {
            cmd.setUsage(this.usage);
        }
        if (this.permMessage != null) {
            cmd.setPermissionMessage(this.permMessage);
        }
        this.getCommandMap().register("", (Command)cmd);
        cmd.setExecutor((TabExecutor)this);
    }
    
    private final class ReflectCommand extends Command
    {
        private TabExecutor exe;
        
        protected ReflectCommand(final String command) {
            super(command);
            this.exe = null;
        }
        
        public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
            if (this.exe != null) {
                this.exe.onCommand(sender, (Command)this, commandLabel, args);
            }
            return false;
        }
        
        public void setExecutor(final TabExecutor exe) {
            this.exe = exe;
        }
        
        public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
            if (this.exe != null) {
                return (List<String>)this.exe.onTabComplete(sender, (Command)this, alias, args);
            }
            return null;
        }
    }
}
