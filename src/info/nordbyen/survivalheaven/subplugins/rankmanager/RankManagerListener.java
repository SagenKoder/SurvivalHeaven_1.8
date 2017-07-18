package info.nordbyen.survivalheaven.subplugins.rankmanager;

import java.util.*;
import info.nordbyen.survivalheaven.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

public class RankManagerListener implements Listener
{
    ArrayList<String> warns;
    ArrayList<String> mutes;
    
    public RankManagerListener() {
        this.warns = new ArrayList<String>();
        this.mutes = new ArrayList<String>();
    }
    
    public String filterMessage(String msg) {
        msg = msg.replaceAll("(?i)sofaen", "sof\u200ba\u200be\u200bn");
        msg = msg.replaceAll("(?i)kanal", "ka\u200bn\u200ba\u200bl\u200b");
        final List<String> l = new ArrayList<String>();
        l.add("faen");
        l.add("fuck");
        l.add("fuuck");
        l.add("fuuuck");
        l.add("pule");
        l.add("hore");
        l.add("bitch");
        l.add("pikk");
        l.add("penis");
        l.add("homse");
        l.add("rapist");
        l.add("helevette");
        l.add("h\u00e6levette");
        l.add("helevete");
        l.add("h\u00e6levete");
        l.add("helvete");
        l.add("j\u00e6vla");
        l.add("j\u00e6vel");
        l.add("fitte");
        l.add("drittunge");
        l.add("kuksuger");
        l.add("kuk");
        l.add("kukk");
        l.add("faggot");
        l.add("Abbortekstikkel");
        l.add("Abbortryne");
        l.add("Abortfaen");
        l.add("Badetiss");
        l.add("albiner");
        l.add("neger");
        l.add("nekrofil");
        l.add("helvete");
        l.add("helvetes");
        l.add("h\u00e6lv\u00e6tt\u00e6");
        l.add("faggot");
        l.add("innoverpikk");
        l.add("pikk");
        l.add("knuller");
        l.add("puler");
        l.add("fucker");
        l.add("b\u00e6sj");
        l.add("tiss");
        l.add("sex");
        l.add("anal");
        l.add("hurpe");
        l.add("prostituert");
        l.add("prosdata");
        l.add("penis");
        l.add("satan");
        l.add("drittsekk");
        l.add("faen");
        l.add("faan");
        l.add("j\u00e6vlig");
        l.add("j\u00e6vla");
        l.add("f\u00f8kka");
        l.add("f\u00f8kk");
        l.add("fokk");
        l.add("morrapuler");
        l.add("morraknuller");
        l.add("satan");
        l.add("nigger");
        l.add("nigga");
        l.add("neger");
        l.add("lespe");
        l.add("bifil");
        l.add("gay");
        l.add("porn");
        l.add("porno");
        l.add("rumpe");
        l.add("pornhub");
        l.add("kristian.com");
        l.add("redtube");
        l.add("gaytube");
        l.add("xxxtube");
        l.add("virgin");
        l.add("xxx");
        l.add("fittetryne");
        l.add("r\u00e6vslikker");
        l.add("alkis");
        l.add("pungsvette");
        l.add("testikkel");
        l.add("kukost");
        l.add("fette");
        l.add("ebolafaen");
        l.add("ekling");
        l.add("cock");
        l.add("helvete");
        l.add("hijo de puta");
        l.add("puta");
        l.add("fucker");
        l.add("drittsekk");
        for (final String s : l) {
            msg = msg.replaceAll("(?i)" + s, ChatColor.RED + "*\u51f8(^_^)\u51f8*" + ChatColor.GRAY);
        }
        msg = msg.replace("\u200b", "");
        return msg;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCmd(final PlayerCommandPreprocessEvent e) {
        final String s = e.getMessage();
        if (s.startsWith("/reload")) {
            if (SH.getManager().getRankManager().getRank(e.getPlayer().getUniqueId().toString()).getId() != RankType.ADMINISTRATOR.getId()) {
                e.getPlayer().sendMessage(ChatColor.RED + "/reload er disablet for \u00e5 effektivisere serveren");
                e.setCancelled(true);
            }
            return;
        }
        if ((s.startsWith("/msg") || s.startsWith("/m") || s.startsWith("/r") || s.startsWith("/tell") || s.startsWith("/whisper") || s.startsWith("/say") || s.startsWith("/me") || s.startsWith("/tpa") || s.startsWith("/tpaccept")) && this.mutes.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "DU ER MUTET!");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(final AsyncPlayerChatEvent e) {
        SH.getManager().getRankManager().updateNames();
        if (e.isCancelled()) {
            return;
        }
        if (e.getMessage() == null) {
            return;
        }
        final Player p = e.getPlayer();
        e.setCancelled(true);
        if (this.mutes.contains(p.getName())) {
            p.sendMessage(ChatColor.RED + "DU ER MUTET!");
            return;
        }
        final String msg = e.getMessage();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            String message = String.valueOf(p.getDisplayName()) + ChatColor.GRAY + ": " + msg.replace(o.getName(), new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append(o.getName()).append(ChatColor.GRAY).toString());
            if (SH.getManager().getPlayerDataManager().getPlayerData(p.getUniqueId().toString()).getRank() >= RankType.SMARAGD.getId()) {
                message = ChatColor.translateAlternateColorCodes('&', message);
            }
            o.sendMessage(message);
        }
        Bukkit.getConsoleSender().sendMessage(String.valueOf(p.getDisplayName()) + ChatColor.RESET + ": " + ChatColor.GRAY + msg);
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        SH.getManager().getRankManager().updateNames();
    }
}
