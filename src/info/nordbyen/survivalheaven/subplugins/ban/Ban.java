package info.nordbyen.survivalheaven.subplugins.ban;

import java.util.*;

public class Ban
{
    private final String uuid;
    private final String by_uuid;
    private final String reason;
    private final Date from;
    private final Date to;
    
    public Ban(final String uuid, final String by_uuid, final String reason, final Date from, final Date to) {
        this.uuid = uuid;
        this.by_uuid = by_uuid;
        this.reason = reason;
        this.from = from;
        this.to = to;
    }
    
    public String getUUID() {
        return this.uuid;
    }
    
    public String getGiverUUID() {
        return this.by_uuid;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public Date getFrom() {
        return this.from;
    }
    
    public Date getTo() {
        return this.to;
    }
}
