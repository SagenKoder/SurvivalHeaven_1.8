package info.nordbyen.survivalheaven.subplugins.votifyer;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import info.nordbyen.survivalheaven.api.command.*;
import java.util.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;

public class Votifyer extends SubPlugin
{
    static InventoryMenu menu;
    
    static {
        (Votifyer.menu = new InventoryMenu("Stemmer", 9, new InventoryMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(final InventoryMenu.OptionClickEvent e) {
                final int pos = e.getPosition();
                final Player p = e.getPlayer();
                if (pos == 3) {
                    p.sendMessage(ChatColor.RED + "Du pr\u00f8vde \u00e5 stemme for dag, men denne er ikke kodet enda");
                    return;
                }
                if (pos == 4) {
                    p.sendMessage(ChatColor.RED + "Du pr\u00f8vde \u00e5 stemme for natt, men denne er ikke kodet enda");
                    return;
                }
                if (pos == 5) {
                    p.sendMessage(ChatColor.RED + "Du pr\u00f8vde \u00e5 stemme for sol, men denne er ikke kodet enda");
                    return;
                }
                if (pos == 6) {
                    p.sendMessage(ChatColor.RED + "Du pr\u00f8vde \u00e5 stemme for regn, men denne er ikke kodet enda");
                }
            }
        })).setOption(2, Skull.PIG.getItem(), "Stem dag", "Kast din stemme for dag");
        Votifyer.menu.setOption(3, new ItemStack(Material.REDSTONE_LAMP_OFF), "Stem natt", "Kast din stemme for natt");
        Votifyer.menu.setOption(5, new ItemStack(Material.BUCKET), "Stem sol", "Kast din stemme for sol");
        Votifyer.menu.setOption(6, new ItemStack(Material.WATER_BUCKET), "Stem regn", "Kast din stemme for regn");
    }
    
    public Votifyer(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
        Votifyer.menu.destroy();
    }
    
    @Override
    protected void enable() {
        new VoteCommand("stem", "/<command>", "\u00c5pner et display for \u00e5 stemme", Arrays.asList("stem", "dag", "natt", "sol", "regn", "stemm", "vote"));
    }
    
    public class VoteCommand extends AbstractCommand
    {
        public VoteCommand(final String command, final String usage, final String description, final List<String> aliases) {
            super(command, usage, description, aliases);
        }
        
        @Override
        public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re en spiller for \u00e5 gj\u00f8re dette");
                return true;
            }
            final Player p = (Player)sender;
            if (SH.getManager().getRankManager().getRank(p.getUniqueId().toString()).getId() < RankType.BRUKER.getId()) {
                p.sendMessage(ChatColor.RED + "Du har ikke h\u00f8y nok rank til \u00e5 gj\u00f8re dette");
                return true;
            }
            Votifyer.menu.open(p);
            return true;
        }
        
        @Override
        public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
            return null;
        }
    }
}
