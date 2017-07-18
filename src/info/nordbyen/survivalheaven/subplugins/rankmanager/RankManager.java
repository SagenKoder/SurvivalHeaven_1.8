package info.nordbyen.survivalheaven.subplugins.rankmanager;

import info.nordbyen.survivalheaven.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import java.util.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.entity.*;
import info.nordbyen.survivalheaven.api.util.*;
import org.bukkit.*;
import ca.wacos.nametagedit.*;
import info.nordbyen.survivalheaven.api.regions.*;

public class RankManager implements IRankManager
{
    public RankManager() {
        Bukkit.getPluginManager().registerEvents((Listener)new RankManagerListener(), (Plugin)SH.getPlugin());
        SH.getPlugin().getCommand("rank").setExecutor((CommandExecutor)new RankManagerCommand());
    }
    
    @Override
    public BadgeType[] getBadges(final String uuid) {
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(uuid);
        final BadgeType[] badges = new BadgeType[pd.getBadges().size()];
        int i = 0;
        for (final int badge : pd.getBadges()) {
            badges[i] = BadgeType.getBadgeFromId(badge);
            ++i;
        }
        return badges;
    }
    
    @Override
    public String getChatBadgePrefix(final String uuid) {
        final BadgeType[] badges = this.getBadges(uuid);
        String prefix = "";
        BadgeType[] array;
        for (int length = (array = badges).length, i = 0; i < length; ++i) {
            final BadgeType badge = array[i];
            prefix = String.valueOf(prefix) + badge.getPrefix();
        }
        return prefix;
    }
    
    @Override
    public String getChatRankPrefix(final String uuid) {
        final RankType rank = this.getRank(uuid);
        return rank.getPrefix();
    }
    
    @Override
    public RankType getRank(final String uuid) {
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(uuid);
        final RankType rank = RankType.getRankFromId(pd.getRank());
        return rank;
    }
    
    @Override
    public void updateNames() {
        for (final Player o : Bukkit.getOnlinePlayers()) {
            final String custom = this.getChatRankPrefix(o.getUniqueId().toString());
            final String name = String.valueOf(this.getRank(o.getUniqueId().toString()).getColor()) + custom + o.getName();
            o.setPlayerListName(name);
            o.setDisplayName(name);
            final IRegionData rd = SH.getManager().getRegionManager().getRegionAt(o.getLocation());
            if (rd == null) {
                FancyMessages.sendTabTitle(o, SH.NAME, SH.MOTTO);
            }
            else {
                FancyMessages.sendTabTitle(o, SH.NAME, ChatColor.BLUE + "Omr\u00e5de: " + ChatColor.GREEN + rd.getName() + ChatColor.BLUE + "\nPvP: " + (rd.isPvp() ? (ChatColor.RED + "Aktivert") : (ChatColor.GREEN + "Deaktivert")));
            }
        }
        for (final IPlayerData p : SH.getManager().getPlayerDataManager().getAllPlayerData()) {
            if (p.getRank() != RankType.BRUKER.getId()) {
                final String custom = this.getChatRankPrefix(p.getUUID());
                final String prefix = String.valueOf(this.getRank(p.getUUID()).getColor()) + custom;
                if (prefix.equals(NametagAPI.getPrefix(p.getName()))) {
                    continue;
                }
                if (custom != "") {
                    NametagAPI.setPrefix(p.getName(), String.valueOf(this.getRank(p.getUUID()).getColor()) + custom);
                }
                else {
                    NametagAPI.clear(p.getName());
                }
            }
            else {
                NametagAPI.clear(p.getName());
            }
        }
    }
}
