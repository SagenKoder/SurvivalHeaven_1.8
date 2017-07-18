package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class Jmp
{
    public static Bitly.Provider as(final String user, final String apiKey) {
        return new SimpleProvider("http://j.mp/", user, apiKey, "http://api.j.mp/v3/");
    }
}
