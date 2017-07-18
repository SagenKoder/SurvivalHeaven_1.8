package info.nordbyen.survivalheaven.api.scheduler;

import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import java.util.*;

public class L0lTaskManager extends BukkitRunnable
{
    private static BukkitTask instance;
    private static HashMap<L0lTaskWrapper, Plugin> taskMap_r;
    private static HashMap<L0lTaskWrapper, Plugin> taskMap_o;
    private final Plugin plugin;
    
    public static void addTask(final Plugin plugin, final L0lTask task, int ticks) {
        if (task == null) {
            throw new IllegalArgumentException("'L0lTask task' cannot be 'null'!");
        }
        if (ticks < 1) {
            ticks = 1;
        }
        L0lTaskManager.taskMap_r.put(new L0lTaskWrapper(task, ticks), plugin);
    }
    
    public static void nullAll() {
        L0lTaskManager.instance = null;
        L0lTaskManager.taskMap_r.clear();
        L0lTaskManager.taskMap_r = null;
    }
    
    public static void removeTask(final L0lTask task) {
        L0lTaskManager.taskMap_r.remove(task);
    }
    
    public static void startTimer(final Plugin plugin) {
        if (L0lTaskManager.taskMap_r == null) {
            L0lTaskManager.taskMap_r = new HashMap<L0lTaskWrapper, Plugin>();
        }
        if (L0lTaskManager.taskMap_o == null) {
            L0lTaskManager.taskMap_o = new HashMap<L0lTaskWrapper, Plugin>();
        }
        if (L0lTaskManager.instance == null) {
            L0lTaskManager.instance = new L0lTaskManager(plugin).runTaskTimer(plugin, 1L, 1L);
        }
    }
    
    private L0lTaskManager(final Plugin plugin) {
        this.plugin = plugin;
    }
    
    public void run() {
        final long ticks = Bukkit.getServer().getWorlds().get(0).getFullTime();
        for (final Map.Entry<L0lTaskWrapper, Plugin> taskWrapperEntry : L0lTaskManager.taskMap_r.entrySet()) {
            if (taskWrapperEntry.getValue() != this.plugin) {
                continue;
            }
            final L0lTaskWrapper taskWrapper = taskWrapperEntry.getKey();
            if (ticks % taskWrapper.getDelay() != 0L) {
                continue;
            }
            if (taskWrapper.isDone()) {
                taskWrapper.nullAll();
                L0lTaskManager.taskMap_o.put(taskWrapper, this.plugin);
            }
            else {
                taskWrapper.executeTask();
            }
        }
        for (final Map.Entry<L0lTaskWrapper, Plugin> taskWrapperEntry : L0lTaskManager.taskMap_o.entrySet()) {
            L0lTaskManager.taskMap_r.remove(taskWrapperEntry.getKey());
        }
        L0lTaskManager.taskMap_o = new HashMap<L0lTaskWrapper, Plugin>();
    }
}
