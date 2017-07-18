package info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit;

import com.sk89q.worldedit.event.extent.*;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.extent.*;
import com.sk89q.worldedit.extension.platform.*;
import com.sk89q.worldedit.util.eventbus.*;

public class MyEventHandler
{
    @Subscribe
    public void wrapForLogging(final EditSessionEvent event) {
        final Actor actor = event.getActor();
        if (event.getStage() != EditSession.Stage.BEFORE_CHANGE && actor != null && actor.isPlayer()) {
            event.setExtent((Extent)new MyLogger(actor, event.getExtent(), event.getWorld()));
        }
    }
}
