package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class ShortenedUrl extends Url
{
    private final boolean newHash;
    
    ShortenedUrl(final String shortBase, final String globalHash, final String userHash, final String shortUrl, final String longUrl, final boolean newHash) {
        super(shortBase, globalHash, userHash, shortUrl, longUrl);
        this.newHash = newHash;
    }
    
    public boolean isNewHash() {
        return this.newHash;
    }
    
    @Override
    public String toString() {
        return "ShortenedUrl [newHash=" + this.newHash + ", getGlobalHash()=" + this.getGlobalHash() + ", getLongUrl()=" + this.getLongUrl() + ", getShortUrl()=" + this.getShortUrl() + ", getUserHash()=" + this.getUserHash() + "]";
    }
}
