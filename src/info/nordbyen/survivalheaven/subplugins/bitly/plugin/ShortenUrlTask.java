package info.nordbyen.survivalheaven.subplugins.bitly.plugin;

import java.util.*;
import java.net.*;
import java.io.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.subplugins.bitly.util.*;

public class ShortenUrlTask extends TimerTask
{
    private final String username;
    private final String key;
    private final String message;
    
    public ShortenUrlTask(final String username, final String key, final String message) {
        this.username = username;
        this.key = key;
        this.message = message;
    }
    
    public String parseStringForUrl() {
        String finalMessage = "";
        final String[] words = this.message.split(" ");
        for (int i = 0; i < words.length; ++i) {
            URL url_;
            try {
                url_ = new URL(words[i]);
            }
            catch (Exception e) {
                continue;
            }
            try {
                url_.openConnection();
            }
            catch (IOException e2) {
                continue;
            }
            try {
                final Url url = Bitly.as(this.username, this.key).call(Bitly.shorten(words[i]));
                words[i] = url.getShortUrl();
            }
            catch (BitlyException e3) {
                final String http = "http://" + words[i];
                try {
                    final Url url2 = Bitly.as(this.username, this.key).call(Bitly.shorten(http));
                    words[i] = url2.getShortUrl();
                }
                catch (BitlyException e4) {
                    Bukkit.getLogger().warning("Malformed url: " + words[i]);
                }
            }
        }
        String[] array;
        for (int length = (array = words).length, j = 0; j < length; ++j) {
            final String string = array[j];
            finalMessage = String.valueOf(finalMessage) + string + " ";
        }
        return finalMessage.trim();
    }
    
    @Override
    public void run() {
        this.parseStringForUrl();
    }
}
