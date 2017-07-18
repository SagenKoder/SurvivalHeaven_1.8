package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.command.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;

public class HelpCommand extends AbstractCommand
{
    private static final int COMMANDS_PER_PAGE = 6;
    private ArrayList<C> spK;
    private ArrayList<C> stK;
    
    public HelpCommand(final String command, final List<String> aliases) {
        super(command, null, null, aliases);
        this.spK = new ArrayList<C>();
        this.stK = new ArrayList<C>();
        this.initMessages();
    }
    
    public void initMessages() {
        this.spK.add(new C("afk", "Setter deg i afk modus"));
        this.spK.add(new C("sethome <navn>", "Setter et hjem der du st\u00e5r"));
        this.spK.add(new C("delhome <navn>", "Sletter et hjem"));
        this.spK.add(new C("home <navn>", "Teleporterer deg til et hjem"));
        this.spK.add(new C("homes", "Lister alle dine hjem"));
        this.spK.add(new C("h <melding>", "Sender en melding til alle spillere om handel"));
        this.spK.add(new C("m <spiller> <melding>", "Sender en privat melding til en spiller"));
        this.spK.add(new C("s", "Teleporterer deg til h\u00f8yeste blokk over deg"));
        this.spK.add(new C("tpa <spiller>", "Teleportere til en annen spiller"));
        this.spK.add(new C("tpaccept <spiller>", "Godta en tpa foresp\u00f8rsel"));
        this.spK.add(new C("who", "Se hvem som er p\u00e5logget"));
        this.spK.add(new C("venn", "Lister alle kommandoer for venner"));
        this.spK.add(new C("venn list", "Lister alle dine venner"));
        this.spK.add(new C("venn sp\u00f8r <spiller>", "Sender en venneforesp\u00f8rsel (sp\u00f8r stab om bugs)"));
        this.spK.add(new C("venn godta <spiller>", "Godtar en venneforsp\u00f8rsel"));
        this.spK.add(new C("venn avsl\u00e5 <spiller>", "Avsl\u00e5r en venneforesp\u00f8rsel"));
        this.spK.add(new C("venn foresp\u00f8rsler", "Lister alle dine venneforesp\u00f8rsler"));
        this.spK.add(new C("venn fjern <spiller>", "Fjerner en spiller fra dine venner"));
        this.spK.add(new C("lw", "Se advarslene dine"));
        this.spK.add(new C("back", "Teleporterer deg til siste d\u00f8dsplass"));
        this.stK.add(new C("FUCK OFF", "Dette kommer snart"));
        this.stK.add(new C("hjelp spiller <nr>", "Se spillerhjelpen"));
    }
    
    @Override
    public boolean onCommand(final CommandSender p, final Command command, final String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("help")) {
            return true;
        }
        boolean isAdmin = false;
        if (!(p instanceof Player)) {
            isAdmin = true;
        }
        else {
            final IRankManager rm = SH.getManager().getRankManager();
            final RankType r = rm.getRank(((Player)p).getUniqueId().toString());
            if (r.getId() >= RankType.MODERATOR.getId()) {
                isAdmin = true;
            }
        }
        if (args.length >= 1 && args[0].equalsIgnoreCase("spiller")) {
            isAdmin = false;
            final String[] newArgs = new String[args.length - 1];
            int i = 0;
            boolean f = true;
            String[] array;
            for (int length = (array = args).length, j = 0; j < length; ++j) {
                final String s = array[j];
                if (f) {
                    f = false;
                }
                else {
                    newArgs[i] = s;
                    ++i;
                }
            }
            args = newArgs;
        }
        if (isAdmin) {
            this.prosessStabHelp(p, args);
            return true;
        }
        this.prosessSpillerHelp(p, args);
        return true;
    }
    
    public void prosessSpillerHelp(final CommandSender p, final String... args) {
        final int maxPage = (int)Math.ceil(this.spK.size() / 6.0);
        int page = 1;
        if (args.length >= 1) {
            try {
                page = Integer.parseInt(args[0]);
            }
            catch (Exception e) {
                p.sendMessage(ChatColor.RED + args[0] + " er et ugyldig tall");
                return;
            }
        }
        if (page > maxPage) {
            page = maxPage;
        }
        p.sendMessage(ChatColor.GREEN + "*********************************************************");
        p.sendMessage(ChatColor.BLUE + "HJELP FOR SPILLERE (Side " + page + " av " + maxPage + ")");
        p.sendMessage(ChatColor.GRAY + "----------------------------------------------");
        for (int i = 6 * (page - 1); i < ((page == maxPage) ? (6 * page + this.spK.size() % 6 - 1) : (6 * page)); ++i) {
            final C c = this.spK.get(i);
            p.sendMessage(ChatColor.YELLOW + "/" + c.c + " " + ChatColor.GRAY + c.d);
        }
        p.sendMessage(ChatColor.GREEN + "*********************************************************");
    }
    
    public void prosessStabHelp(final CommandSender p, final String... args) {
        final int maxPage = (int)Math.ceil(this.stK.size() / 6.0);
        int page = 1;
        if (args.length >= 1) {
            try {
                page = Integer.parseInt(args[0]);
            }
            catch (Exception e) {
                p.sendMessage(ChatColor.RED + args[0] + " er et ugyldig tall");
                return;
            }
        }
        if (page > maxPage) {
            page = maxPage;
        }
        p.sendMessage(ChatColor.GREEN + "*********************************************************");
        p.sendMessage(ChatColor.BLUE + "HJELP FOR STAB (Side " + page + " av " + maxPage + ")");
        p.sendMessage(ChatColor.GRAY + "----------------------------------------------");
        for (int i = 6 * (page - 1); i < ((page == maxPage) ? (6 * page + this.stK.size() % 6 - 1) : (6 * page)); ++i) {
            final C c = this.stK.get(i);
            p.sendMessage(ChatColor.YELLOW + "/" + c.c + " " + ChatColor.GRAY + c.d);
        }
        p.sendMessage(ChatColor.GREEN + "*********************************************************");
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return null;
    }
    
    public static class C
    {
        String c;
        String d;
        
        public C(final String c, final String d) {
            this.c = c;
            this.d = d;
        }
    }
}
