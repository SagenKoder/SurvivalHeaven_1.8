package info.nordbyen.survivalheaven.subplugins.particles;

import info.nordbyen.survivalheaven.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.inventory.*;
import info.nordbyen.survivalheaven.api.rankmanager.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class ParticleListener implements Listener
{
    public ParticleListener() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)SH.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    for (final String s : SH.particles.keySet()) {
                        if (p.getUniqueId().toString().equals(s)) {
                            if (SH.particles.get(s) == ParticleEffect.HEART) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(0.3f, 0.3f, 0.3f, 1.0f, 3, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.VILLAGER_HAPPY) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(0.3f, 0.3f, 0.3f, 5.0f, 3, p.getLocation().add(0.0, 2.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.CLOUD) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(0.2f, 0.0f, 0.2f, 0.0f, 10, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.REDSTONE) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 2.0f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.EXPLOSION_LARGE) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 0.01f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.SUSPENDED_DEPTH) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 0.01f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.CRIT) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 0.01f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.CRIT_MAGIC) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 0.02f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) == ParticleEffect.SPELL_MOB) {
                                ParticleEffect.fromName(SH.particles.get(s).getName()).display(1.0f, 1.0f, 1.0f, 0.02f, 100, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                            }
                            if (SH.particles.get(s) != ParticleEffect.VILLAGER_HAPPY) {
                                continue;
                            }
                            ParticleEffect.fromName(SH.particles.get(s).getName()).display(0.3f, 0.3f, 0.3f, 0.01f, 60, p.getLocation().add(0.0, 3.0, 0.0), 30.0);
                        }
                    }
                }
            }
        }, 0L, 10L);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player && e.getInventory().getName().equalsIgnoreCase("Tilgjengelige partikler")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.INK_SACK && e.getCurrentItem().getDurability() == 1) {
                SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.HEART);
            }
            else if (e.getCurrentItem().getType() == Material.FIREWORK_CHARGE) {
                SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.VILLAGER_HAPPY);
            }
            else if (e.getCurrentItem().getType() == Material.INK_SACK && e.getCurrentItem().getDurability() == 15) {
                SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.CLOUD);
            }
            else if (e.getCurrentItem().getType() == Material.REDSTONE) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.REDSTONE);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.TNT) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.EXPLOSION_LARGE);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.WATER_BUCKET) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.SUSPENDED_DEPTH);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.CRIT);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.EXP_BOTTLE) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.CRIT_MAGIC);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.POISONOUS_POTATO) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.SPELL_MOB);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.EMERALD) {
                if (SH.getManager().getPlayerDataManager().getPlayerDataFromName(e.getWhoClicked().getName()).getRank() >= RankType.SMARAGD.getId()) {
                    SH.particles.put(e.getWhoClicked().getUniqueId().toString(), ParticleEffect.VILLAGER_HAPPY);
                }
                else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne partiklen!");
                }
            }
            else if (e.getCurrentItem().getType() == Material.BARRIER) {
                SH.particles.remove(e.getWhoClicked().getUniqueId().toString());
            }
        }
    }
}
