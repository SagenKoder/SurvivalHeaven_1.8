package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.subplugins.quest.*;
import info.nordbyen.survivalheaven.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.api.playerdata.*;
import org.bukkit.entity.*;

public class AcceptHandler implements Acceptable
{
    @Override
    public void executedAccept(final String uuid) {
        if (FirstEncounter.getWaitingPlayers().contains(uuid)) {
            final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(uuid);
            final Player p = Bukkit.getPlayer(pd.getName());
            p.sendMessage(ChatColor.BLUE + "Yay :D");
        }
    }
}
