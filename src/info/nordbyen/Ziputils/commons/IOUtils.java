package info.nordbyen.Ziputils.commons;

import java.io.*;

public class IOUtils
{
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final char DIR_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String LINE_SEPARATOR;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    
    static {
        DIR_SEPARATOR = File.separatorChar;
        final StringWriter buf = new StringWriter(4);
        final PrintWriter out = new PrintWriter(buf);
        out.println();
        LINE_SEPARATOR = buf.toString();
    }
    
    public static void closeQuietly(final InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        }
        catch (IOException ex) {}
    }
    
    public static void closeQuietly(final OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        }
        catch (IOException ex) {}
    }
    
    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }
        for (int ch = input1.read(); -1 != ch; ch = input1.read()) {
            final int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
        }
        final int ch2 = input2.read();
        return ch2 == -1;
    }
    
    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    
    public static void copy(final InputStream input, final Writer output) throws IOException {
        final InputStreamReader in = new InputStreamReader(input);
        copy(in, output);
    }
    
    public static void copy(final InputStream input, final Writer output, final String encoding) throws IOException {
        if (encoding == null) {
            copy(input, output);
        }
        else {
            final InputStreamReader in = new InputStreamReader(input, encoding);
            copy(in, output);
        }
    }
    
    public static int copy(final Reader input, final Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    
    public static long copyLarge(final InputStream input, final OutputStream output) throws IOException {
        final byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    public static long copyLarge(final Reader input, final Writer output) throws IOException {
        final char[] buffer = new char[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    public static byte[] toByteArray(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    
    public static String toString(final InputStream input, final String encoding) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }
}
