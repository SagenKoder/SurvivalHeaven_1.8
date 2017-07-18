package info.nordbyen.survivalheaven.subplugins.bitly.util;

import java.util.*;

public final class Bitly
{
    public static Provider as(final String user, final String apiKey) {
        return new SimpleProvider("http://bit.ly/", user, apiKey, "http://api.bit.ly/v3/");
    }
    
    public static BitlyMethod<UrlClicks> clicks(final String string) {
        return Methods.clicks(string);
    }
    
    public static BitlyMethod<Set<UrlClicks>> clicks(final String[] string) {
        return Methods.clicks(string);
    }
    
    public static BitlyMethod<Url> expand(final String value) {
        return Methods.expand(value);
    }
    
    public static BitlyMethod<Set<Url>> expand(final String[] value) {
        return Methods.expand(value);
    }
    
    public static BitlyMethod<UrlInfo> info(final String value) {
        return Methods.info(value);
    }
    
    public static BitlyMethod<Set<UrlInfo>> info(final String[] value) {
        return Methods.info(value);
    }
    
    public static BitlyMethod<ShortenedUrl> shorten(final String longUrl) {
        return Methods.shorten(longUrl);
    }
    
    public interface Provider
    {
         <A> A call(final BitlyMethod<A> p0);
        
        String getUrl();
    }
}
