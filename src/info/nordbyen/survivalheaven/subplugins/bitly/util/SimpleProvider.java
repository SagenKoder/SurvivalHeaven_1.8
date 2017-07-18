package info.nordbyen.survivalheaven.subplugins.bitly.util;

import javax.xml.parsers.*;
import info.nordbyen.survivalheaven.subplugins.bitly.util.utils.*;
import org.w3c.dom.*;
import info.nordbyen.survivalheaven.subplugins.bitly.util.data.*;
import java.net.*;
import java.io.*;
import java.util.*;

class SimpleProvider implements Bitly.Provider
{
    private final String url;
    private final String user;
    private final String apiKey;
    private final String endPoint;
    
    SimpleProvider(final String url, final String user, final String apiKey, final String endPoint) {
        this.url = url;
        this.user = user;
        this.apiKey = apiKey;
        this.endPoint = endPoint;
    }
    
    @Override
    public <A> A call(final BitlyMethod<A> m) {
        final String url = this.getUrlForCall(m);
        final Document response = this.filterErrorResponse(this.fetchUrl(url));
        return m.apply(this, response);
    }
    
    private Document fetchUrl(final String url) {
        try {
            final HttpURLConnection openConnection = (HttpURLConnection)new URL(url).openConnection();
            if (openConnection.getResponseCode() == 200) {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(openConnection.getInputStream());
            }
            throw new BitlyException("Transport error! " + openConnection.getResponseCode() + " " + openConnection.getResponseMessage());
        }
        catch (IOException e) {
            throw new BitlyException("Transport I/O error!", e);
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
    
    private Document filterErrorResponse(final Document doc) {
        final Node statusCode = doc.getElementsByTagName("status_code").item(0);
        final Node statusText = doc.getElementsByTagName("status_txt").item(0);
        if (statusCode == null || statusText == null) {
            throw new BitlyException("Unexpected response (no status and/or message)!");
        }
        final int code = Integer.parseInt(Dom.getTextContent(statusCode));
        if (code == 200) {
            return doc;
        }
        throw new BitlyException(Dom.getTextContent(statusText));
    }
    
    @Override
    public String getUrl() {
        return this.url;
    }
    
    protected String getUrlForCall(final BitlyMethod<?> m) {
        final StringBuilder sb = new StringBuilder(this.endPoint).append(String.valueOf(m.getName()) + "?").append("&login=").append(this.user).append("&apiKey=").append(this.apiKey).append("&format=xml");
        try {
            for (final Pair p : m.getParameters()) {
                sb.append("&" + p.getOne() + "=" + URLEncoder.encode(p.getTwo(), "UTF-8"));
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "SimpleProvider [apiKey=" + this.apiKey + ", endPoint=" + this.endPoint + ", url=" + this.url + ", user=" + this.user + "]";
    }
}
