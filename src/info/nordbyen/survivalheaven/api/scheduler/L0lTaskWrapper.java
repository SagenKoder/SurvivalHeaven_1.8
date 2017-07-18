package info.nordbyen.survivalheaven.api.scheduler;

public final class L0lTaskWrapper
{
    private L0lTask task;
    private final int delay;
    private int loops_done;
    
    public L0lTaskWrapper(final L0lTask task, final int delay) {
        this.loops_done = 0;
        this.task = task;
        this.delay = delay;
    }
    
    public void executeTask() {
        this.task.taskToDo(this.loops_done);
        ++this.loops_done;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public L0lTask getTask() {
        return this.task;
    }
    
    public boolean isDone() {
        return !this.task.shouldContinue(this.loops_done);
    }
    
    public void nullAll() {
        this.task = null;
    }
}
