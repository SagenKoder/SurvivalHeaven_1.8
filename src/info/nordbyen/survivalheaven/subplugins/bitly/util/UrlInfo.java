package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class UrlInfo
{
    private final Url url;
    private final String createdBy;
    private final String title;
    
    UrlInfo(final Url url, final String createdBy, final String title) {
        this.url = url;
        this.createdBy = createdBy;
        this.title = title;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public Url getUrl() {
        return this.url;
    }
    
    @Override
    public String toString() {
        return "Info [createdBy=" + this.createdBy + ", title=" + this.title + ", url=" + this.url + "]";
    }
}
