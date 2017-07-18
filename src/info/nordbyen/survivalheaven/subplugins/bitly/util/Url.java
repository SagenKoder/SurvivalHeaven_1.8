package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class Url
{
    private String shortBase;
    private String globalHash;
    private String userHash;
    private String shortUrl;
    private String longUrl;
    
    Url() {
    }
    
    Url(final String shortBase, final String globalHash, final String userHash, final String shortUrl, final String longUrl) {
        this.shortBase = shortBase;
        this.globalHash = globalHash;
        this.userHash = userHash;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        if (this.shortUrl.length() == 0) {
            this.shortUrl = String.valueOf(shortBase) + userHash;
        }
    }
    
    public String getGlobalHash() {
        return this.globalHash;
    }
    
    public String getLongUrl() {
        return this.longUrl;
    }
    
    public String getShortUrl() {
        return this.shortUrl;
    }
    
    public String getUserHash() {
        return this.userHash;
    }
    
    @Override
    public String toString() {
        return "Url [shortBase=" + this.shortBase + ", globalHash=" + this.globalHash + ", longUrl=" + this.longUrl + ", shortUrl=" + this.shortUrl + ", userHash=" + this.userHash + "]";
    }
}
