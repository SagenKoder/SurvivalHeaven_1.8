package info.nordbyen.survivalheaven.api.scheduler;

public interface L0lTask
{
    boolean shouldContinue(final int p0);
    
    void taskToDo(final int p0);
}
