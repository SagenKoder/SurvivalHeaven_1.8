package info.nordbyen.survivalheaven.subplugins.bitly.util;

import info.nordbyen.survivalheaven.subplugins.bitly.util.data.*;
import java.util.*;
import org.w3c.dom.*;
import info.nordbyen.survivalheaven.subplugins.bitly.util.utils.*;

final class Methods
{
    public static BitlyMethod<UrlClicks> clicks(final String string) {
        return (BitlyMethod<UrlClicks>)new MethodBase("clicks", new Pair[] { Pair.p(hashOrUrl(string), string) }) {
            @Override
            public UrlClicks apply(final Bitly.Provider provider, final Document document) {
                return Methods.parseClicks(provider, document.getElementsByTagName("clicks").item(0));
            }
        };
    }
    
    public static BitlyMethod<Set<UrlClicks>> clicks(final String[] string) {
        return (BitlyMethod<Set<UrlClicks>>)new MethodBase("clicks", getUrlMethodArgs(string)) {
            @Override
            public Set<UrlClicks> apply(final Bitly.Provider provider, final Document document) {
                final HashSet clicks = new HashSet();
                final NodeList nl = document.getElementsByTagName("clicks");
                for (int i = 0; i < nl.getLength(); ++i) {
                    clicks.add(Methods.parseClicks(provider, nl.item(i)));
                }
                return (Set<UrlClicks>)clicks;
            }
        };
    }
    
    public static BitlyMethod<Url> expand(final String values) {
        return (BitlyMethod<Url>)new MethodBase("expand", getUrlMethodArgs(new String[] { values })) {
            @Override
            public Url apply(final Bitly.Provider provider, final Document document) {
                return Methods.parseUrl(provider, document.getElementsByTagName("entry").item(0));
            }
        };
    }
    
    public static BitlyMethod<Set<Url>> expand(final String[] values) {
        return (BitlyMethod<Set<Url>>)new MethodBase("expand", getUrlMethodArgs(values)) {
            @Override
            public Set<Url> apply(final Bitly.Provider provider, final Document document) {
                final HashSet inf = new HashSet();
                final NodeList infos = document.getElementsByTagName("entry");
                for (int i = 0; i < infos.getLength(); ++i) {
                    inf.add(Methods.parseUrl(provider, infos.item(i)));
                }
                return (Set<Url>)inf;
            }
        };
    }
    
    static Collection<Pair<String, String>> getUrlMethodArgs(final String[] value) {
        final List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
        for (final String p : value) {
            pairs.add(Pair.p(hashOrUrl(p), p));
        }
        return pairs;
    }
    
    static String hashOrUrl(final String p) {
        return p.startsWith("http://") ? "shortUrl" : "hash";
    }
    
    public static BitlyMethod<UrlInfo> info(final String value) {
        return (BitlyMethod<UrlInfo>)new MethodBase("info", getUrlMethodArgs(new String[] { value })) {
            @Override
            public UrlInfo apply(final Bitly.Provider provider, final Document document) {
                return Methods.parseInfo(provider, document.getElementsByTagName("info").item(0));
            }
        };
    }
    
    public static BitlyMethod<Set<UrlInfo>> info(final String[] values) {
        return (BitlyMethod<Set<UrlInfo>>)new MethodBase("info", getUrlMethodArgs(values)) {
            @Override
            public Set<UrlInfo> apply(final Bitly.Provider provider, final Document document) {
                final HashSet inf = new HashSet();
                final NodeList infos = document.getElementsByTagName("info");
                for (int i = 0; i < infos.getLength(); ++i) {
                    inf.add(Methods.parseInfo(provider, infos.item(i)));
                }
                return (Set<UrlInfo>)inf;
            }
        };
    }
    
    static UrlClicks parseClicks(final Bitly.Provider provider, final Node item) {
        final NodeList nl = item.getChildNodes();
        long user = 0L;
        long global = 0L;
        for (int i = 0; i < nl.getLength(); ++i) {
            final String name = nl.item(i).getNodeName();
            final String value = Dom.getTextContent(nl.item(i));
            if ("user_clicks".equals(name)) {
                user = Long.parseLong(value);
            }
            else if ("global_clicks".equals(name)) {
                global = Long.parseLong(value);
            }
        }
        return new UrlClicks(parseUrl(provider, item), user, global);
    }
    
    static UrlInfo parseInfo(final Bitly.Provider provider, final Node nl) {
        final NodeList il = nl.getChildNodes();
        String title = "";
        String createdBy = "";
        for (int i = 0; i < il.getLength(); ++i) {
            final Node n = il.item(i);
            final String name = n.getNodeName();
            final String value = Dom.getTextContent(n);
            if ("created_by".equals(name)) {
                createdBy = value;
            }
            else if ("title".equals(name)) {
                title = value;
            }
        }
        return new UrlInfo(parseUrl(provider, nl), createdBy, title);
    }
    
    static ShortenedUrl parseShortenedUrl(final Bitly.Provider provider, final Node nl) {
        String gHash = "";
        String uHash = "";
        String sUrl = "";
        String lUrl = "";
        String isNew = "";
        final NodeList il = nl.getChildNodes();
        for (int i = 0; i < il.getLength(); ++i) {
            final Node n = il.item(i);
            final String name = n.getNodeName();
            final String value = Dom.getTextContent(n).trim();
            if ("new_hash".equals(name)) {
                isNew = value;
            }
            else if ("url".equals(name)) {
                sUrl = value;
            }
            else if ("long_url".equals(name)) {
                lUrl = value;
            }
            else if ("global_hash".equals(name)) {
                gHash = value;
            }
            else if ("hash".equals(name)) {
                uHash = value;
            }
        }
        return new ShortenedUrl(provider.getUrl(), gHash, uHash, sUrl, lUrl, isNew.equals("1"));
    }
    
    static Url parseUrl(final Bitly.Provider provider, final Node nl) {
        String gHash = "";
        String uHash = "";
        String sUrl = "";
        String lUrl = "";
        final NodeList il = nl.getChildNodes();
        for (int i = 0; i < il.getLength(); ++i) {
            final Node n = il.item(i);
            final String name = n.getNodeName();
            final String value = Dom.getTextContent(n);
            if ("short_url".equals(name)) {
                sUrl = value;
            }
            else if ("long_url".equals(name)) {
                lUrl = value;
            }
            else if ("global_hash".equals(name)) {
                gHash = value;
            }
            else if ("user_hash".equals(name)) {
                uHash = value;
            }
            else if ("hash".equals(name)) {
                uHash = value;
            }
        }
        return new Url(provider.getUrl(), gHash, uHash, sUrl, lUrl);
    }
    
    public static BitlyMethod<ShortenedUrl> shorten(final String longUrl) {
        return (BitlyMethod<ShortenedUrl>)new MethodBase("shorten", new Pair[] { Pair.p("longUrl", longUrl) }) {
            @Override
            public ShortenedUrl apply(final Bitly.Provider provider, final Document document) {
                final NodeList infos = document.getElementsByTagName("data");
                return Methods.parseShortenedUrl(provider, infos.item(0));
            }
        };
    }
}
