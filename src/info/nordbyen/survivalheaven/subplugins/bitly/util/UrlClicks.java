package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class UrlClicks
{
    private final long userClicks;
    private final long globalClicks;
    private final Url url;
    
    UrlClicks(final Url url, final long userClicks, final long globalClicks) {
        this.url = url;
        this.userClicks = userClicks;
        this.globalClicks = globalClicks;
    }
    
    public long getGlobalClicks() {
        return this.globalClicks;
    }
    
    public Url getUrl() {
        return this.url;
    }
    
    public long getUserClicks() {
        return this.userClicks;
    }
    
    @Override
    public String toString() {
        return "UrlClicks [globalClicks=" + this.globalClicks + ", userClicks=" + this.userClicks + ", url=" + this.url + "]";
    }
}
