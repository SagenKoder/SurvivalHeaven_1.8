package info.nordbyen.survivalheaven.api.scheduler;

import java.util.*;

public final class SHTimedTaskManager
{
    private final TreeMap<Long, SHTimedTask> tasks;
    private long ticks_looped;
    private boolean locked;
    
    public SHTimedTaskManager() {
        this.tasks = new TreeMap<Long, SHTimedTask>();
        this.ticks_looped = 0L;
        this.locked = false;
    }
    
    private final void execute() {
        ++this.ticks_looped;
        for (final Map.Entry<Long, SHTimedTask> entry : this.tasks.entrySet()) {
            if (entry.getKey() - this.ticks_looped == 0L) {
                entry.getValue().execute();
            }
        }
    }
    
    private final void registerListener() {
    }
    
    public final void addTimedTask(final long ticks, final SHTimedTask task) {
        if (task == null) {
            throw new NullPointerException("The task cannot be null!");
        }
        if (this.locked) {
            throw new IllegalStateException("Can not add more tasks after a call to build()");
        }
        this.tasks.put(ticks, task);
    }
    
    public final void build() {
        if (this.locked) {
            throw new IllegalStateException("This has already been buildt");
        }
        this.locked = true;
    }
    
    public final boolean isBuildt() {
        return this.locked;
    }
}
