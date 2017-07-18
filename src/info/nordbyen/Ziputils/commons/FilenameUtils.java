package info.nordbyen.Ziputils.commons;

import java.io.*;

public class FilenameUtils
{
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char SYSTEM_SEPARATOR;
    
    static {
        EXTENSION_SEPARATOR_STR = new Character('.').toString();
        SYSTEM_SEPARATOR = File.separatorChar;
    }
    
    public static int getPrefixLength(final String filename) {
        if (filename == null) {
            return -1;
        }
        final int len = filename.length();
        if (len == 0) {
            return 0;
        }
        char ch0 = filename.charAt(0);
        if (ch0 == ':') {
            return -1;
        }
        if (len == 1) {
            if (ch0 == '~') {
                return 2;
            }
            return isSeparator(ch0) ? 1 : 0;
        }
        else if (ch0 == '~') {
            int posUnix = filename.indexOf(47, 1);
            int posWin = filename.indexOf(92, 1);
            if (posUnix == -1 && posWin == -1) {
                return len + 1;
            }
            posUnix = ((posUnix == -1) ? posWin : posUnix);
            posWin = ((posWin == -1) ? posUnix : posWin);
            return Math.min(posUnix, posWin) + 1;
        }
        else {
            final char ch2 = filename.charAt(1);
            if (ch2 == ':') {
                ch0 = Character.toUpperCase(ch0);
                if (ch0 < 'A' || ch0 > 'Z') {
                    return -1;
                }
                if (len == 2 || !isSeparator(filename.charAt(2))) {
                    return 2;
                }
                return 3;
            }
            else {
                if (!isSeparator(ch0) || !isSeparator(ch2)) {
                    return isSeparator(ch0) ? 1 : 0;
                }
                int posUnix2 = filename.indexOf(47, 2);
                int posWin2 = filename.indexOf(92, 2);
                if ((posUnix2 == -1 && posWin2 == -1) || posUnix2 == 2 || posWin2 == 2) {
                    return -1;
                }
                posUnix2 = ((posUnix2 == -1) ? posWin2 : posUnix2);
                posWin2 = ((posWin2 == -1) ? posUnix2 : posWin2);
                return Math.min(posUnix2, posWin2) + 1;
            }
        }
    }
    
    private static boolean isSeparator(final char ch) {
        return ch == '/' || ch == '\\';
    }
    
    static boolean isSystemWindows() {
        return FilenameUtils.SYSTEM_SEPARATOR == '\\';
    }
}
