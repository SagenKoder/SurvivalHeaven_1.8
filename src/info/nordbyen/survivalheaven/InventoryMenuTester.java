package info.nordbyen.survivalheaven;

import info.nordbyen.survivalheaven.api.subplugin.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.inventory.*;
import info.nordbyen.survivalheaven.api.command.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class InventoryMenuTester extends SubPlugin
{
    InventoryMenu testMenu;
    
    public InventoryMenuTester(final String name) {
        super(name);
    }
    
    @Override
    protected void disable() {
        this.testMenu.destroy();
    }
    
    @Override
    protected void enable() {
        this.testMenu = new InventoryMenu("TestMenu", 9, new InventoryMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(final InventoryMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("Du trykket p\u00e5 " + event.getName());
                event.setWillClose(true);
            }
        }).setOption(3, new ItemStack(Material.APPLE, 1), "Valg1", "Dette valget er fancy :3").setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Valg2", "Dette valget er fancy :3").setOption(5, new ItemStack(Material.EMERALD, 1), "Valg3", "Dette valget er fancy :3");
        new OpenMenuCommand((OpenMenuCommand)null);
    }
    
    public class OpenMenuCommand extends AbstractCommand
    {
        private OpenMenuCommand() {
            super("openmenu", "/<command>", "\u00c5pner en testmeny");
            this.register();
        }
        
        @Override
        public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Du m\u00e5 v\u00e6re en spiller");
                return false;
            }
            final Player p = (Player)sender;
            InventoryMenuTester.this.testMenu.open(p);
            p.sendMessage(ChatColor.GREEN + "\u00c5pnet testmenyen");
            return true;
        }
        
        @Override
        public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
            return null;
        }
    }
}
