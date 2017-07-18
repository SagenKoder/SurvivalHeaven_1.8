package info.nordbyen.survivalheaven.subplugins.commands;

import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.subplugins.particles.*;
import java.util.*;
import info.nordbyen.survivalheaven.subplugins.commands.commands.*;
import org.bukkit.*;

public class Commands extends SubPlugin
{
    static ArrayList<String> brs;
    static Random rand;
    static int num;
    
    static {
        (Commands.brs = new ArrayList<String>()).add("Bes\u00f8k noen av byene v\u00e5re med /nord, /s\u00f8r, /\u00f8st eller /vest.");
        Commands.brs.add("Teste en ide eller bygge en by i kreativ? /kreativ teleporerer deg til kreativ verdenen!");
        Commands.brs.add("\u00d8nsker du \u00e5 la noen andre se i kistene dine eller fjerne blokkene dine? /venn");
        Commands.brs.add("Trenger du hjelp kan du sp\u00f8rre online stab eller opprette en post p\u00e5 forumet survivalheaven.org");
        Commands.brs.add("Har du bygget noe fint i kreativ som du vil beskytte? Sp\u00f8r en admin eller mod om \u00e5 beskytte omr\u00e5det ditt!");
        Commands.brs.add("Stem p\u00e5 serveren og motta gaver: http://minecraft-mp.com/server/29019/vote/");
        Commands.brs.add("Ny valutta og nytt shopsystem: http://survivalheaven.org/threads/nye-butikker-og-ny-valuta.417");
        Commands.rand = new Random();
        Commands.num = Commands.rand.nextInt(Commands.brs.size() - 1);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                Commands.sendBroadcast();
            }
        }, 400L, 12000L);
    }
    
    public Commands(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
    }
    
    @Override
    protected void enable() {
        this.getPlugin().getCommand("jobb").setExecutor((CommandExecutor)new Jobb());
        this.getPlugin().getCommand("fix").setExecutor((CommandExecutor)new Fix());
        this.getPlugin().getCommand("wb").setExecutor((CommandExecutor)new WB());
        this.getPlugin().getCommand("blokker").setExecutor((CommandExecutor)new Blokker());
        this.getPlugin().getCommand("inv").setExecutor((CommandExecutor)new Inv());
        this.getPlugin().getCommand("kick").setExecutor((CommandExecutor)new Kick());
        this.getPlugin().getCommand("afk").setExecutor((CommandExecutor)new AFK());
        this.getPlugin().getCommand("ss").setExecutor((CommandExecutor)new SS());
        this.getPlugin().getCommand("tp").setExecutor((CommandExecutor)new TP());
        this.getPlugin().getCommand("tph").setExecutor((CommandExecutor)new TPH());
        this.getPlugin().getCommand("k").setExecutor((CommandExecutor)new K());
        this.getPlugin().getCommand("h").setExecutor((CommandExecutor)new H());
        this.getPlugin().getCommand("s").setExecutor((CommandExecutor)new S());
        this.getPlugin().getCommand("smelt").setExecutor((CommandExecutor)new Smelt());
        this.getPlugin().getCommand("hatt").setExecutor((CommandExecutor)new Hatt());
        this.getPlugin().getCommand("ec").setExecutor((CommandExecutor)new EC());
        this.getPlugin().getCommand("who").setExecutor((CommandExecutor)new Who());
        this.getPlugin().getCommand("fspeed").setExecutor((CommandExecutor)new FSpeed());
        this.getPlugin().getCommand("killall").setExecutor((CommandExecutor)new Killall());
        this.getPlugin().getCommand("bug").setExecutor((CommandExecutor)new BR());
        this.getPlugin().getCommand("fly").setExecutor((CommandExecutor)new Fly());
        this.getPlugin().getCommand("sitt").setExecutor((CommandExecutor)new Sitt());
        this.getPlugin().getCommand("sudo").setExecutor((CommandExecutor)new Sudo());
        this.getPlugin().getCommand("adminstick").setExecutor((CommandExecutor)new AdminStick());
        this.getPlugin().getCommand("tpa").setExecutor((CommandExecutor)new TPA());
        this.getPlugin().getCommand("tpaccept").setExecutor((CommandExecutor)new TPAccept());
        this.getPlugin().getCommand("mute").setExecutor((CommandExecutor)new Mute());
        this.getPlugin().getCommand("sh").setExecutor((CommandExecutor)new info.nordbyen.survivalheaven.subplugins.commands.commands.SH());
        this.getPlugin().getCommand("mutes").setExecutor((CommandExecutor)new Mutes());
        this.getPlugin().getCommand("partikler").setExecutor((CommandExecutor)new Partikler());
        this.getPlugin().getCommand("cc").setExecutor((CommandExecutor)new Clear());
        this.getPlugin().getCommand("poke").setExecutor((CommandExecutor)new Poke());
        this.getPlugin().getCommand("r").setExecutor((CommandExecutor)new R());
        this.getPlugin().getCommand("viktig").setExecutor((CommandExecutor)new Viktig());
        this.getPlugin().getCommand("tips").setExecutor((CommandExecutor)new Tips());
        new SeenCommand("seen");
        new HeadCommand("head");
        new BackCommand("back");
        new FuckCommand("fuck");
        new ListCommand();
        new M("m", Arrays.asList("msg", "tell", "t", "whisper"));
        new HelpCommand("help", Arrays.asList("hjelp", "?"));
    }
    
    static void sendBroadcast() {
        if (Commands.brs.size() <= Commands.num) {
            Commands.num = 0;
        }
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Survival" + ChatColor.GRAY + "Heaven" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + Commands.brs.get(Commands.num));
        ++Commands.num;
    }
}
