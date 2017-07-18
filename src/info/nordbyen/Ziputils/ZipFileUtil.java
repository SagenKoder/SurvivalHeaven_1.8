package info.nordbyen.Ziputils;

import java.nio.charset.*;
import java.lang.reflect.*;
import java.util.zip.*;
import java.io.*;

class ZipFileUtil
{
    private static final String MISSING_METHOD_PLEASE_UPGRADE = "Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).";
    private static final String CONSTRUCTOR_MESSAGE_FOR_ZIPFILE = "Using constructor ZipFile(File, Charset) has failed: ";
    private static final String CONSTRUCTOR_MESSAGE_FOR_OUTPUT = "Using constructor ZipOutputStream(OutputStream, Charset) has failed: ";
    private static final String CONSTRUCTOR_MESSAGE_FOR_INPUT = "Using constructor ZipInputStream(InputStream, Charset) has failed: ";
    
    static ZipInputStream createZipInputStream(final InputStream inStream, final Charset charset) {
        if (charset == null) {
            return new ZipInputStream(inStream);
        }
        try {
            final Constructor<ZipInputStream> constructor = ZipInputStream.class.getConstructor(InputStream.class, Charset.class);
            return constructor.newInstance(inStream, charset);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException("Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).", e);
        }
        catch (InstantiationException e2) {
            throw new IllegalStateException("Using constructor ZipInputStream(InputStream, Charset) has failed: " + e2.getMessage(), e2);
        }
        catch (IllegalAccessException e3) {
            throw new IllegalStateException("Using constructor ZipInputStream(InputStream, Charset) has failed: " + e3.getMessage(), e3);
        }
        catch (IllegalArgumentException e4) {
            throw new IllegalStateException("Using constructor ZipInputStream(InputStream, Charset) has failed: " + e4.getMessage(), e4);
        }
        catch (InvocationTargetException e5) {
            throw new IllegalStateException("Using constructor ZipInputStream(InputStream, Charset) has failed: " + e5.getMessage(), e5);
        }
    }
    
    static ZipOutputStream createZipOutputStream(final BufferedOutputStream outStream, final Charset charset) {
        if (charset == null) {
            return new ZipOutputStream(outStream);
        }
        try {
            final Constructor<ZipOutputStream> constructor = ZipOutputStream.class.getConstructor(OutputStream.class, Charset.class);
            return constructor.newInstance(outStream, charset);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException("Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).", e);
        }
        catch (InstantiationException e2) {
            throw new IllegalStateException("Using constructor ZipOutputStream(OutputStream, Charset) has failed: " + e2.getMessage(), e2);
        }
        catch (IllegalAccessException e3) {
            throw new IllegalStateException("Using constructor ZipOutputStream(OutputStream, Charset) has failed: " + e3.getMessage(), e3);
        }
        catch (IllegalArgumentException e4) {
            throw new IllegalStateException("Using constructor ZipOutputStream(OutputStream, Charset) has failed: " + e4.getMessage(), e4);
        }
        catch (InvocationTargetException e5) {
            throw new IllegalStateException("Using constructor ZipOutputStream(OutputStream, Charset) has failed: " + e5.getMessage(), e5);
        }
    }
    
    static ZipFile getZipFile(final File src, final Charset charset) throws IOException {
        if (charset == null) {
            return new ZipFile(src);
        }
        try {
            final Constructor<ZipFile> constructor = ZipFile.class.getConstructor(File.class, Charset.class);
            return constructor.newInstance(src, charset);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException("Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).", e);
        }
        catch (InstantiationException e2) {
            throw new IllegalStateException("Using constructor ZipFile(File, Charset) has failed: " + e2.getMessage(), e2);
        }
        catch (IllegalAccessException e3) {
            throw new IllegalStateException("Using constructor ZipFile(File, Charset) has failed: " + e3.getMessage(), e3);
        }
        catch (IllegalArgumentException e4) {
            throw new IllegalStateException("Using constructor ZipFile(File, Charset) has failed: " + e4.getMessage(), e4);
        }
        catch (InvocationTargetException e5) {
            throw new IllegalStateException("Using constructor ZipFile(File, Charset) has failed: " + e5.getMessage(), e5);
        }
    }
    
    static boolean isCharsetSupported() throws IOException {
        try {
            ZipFile.class.getConstructor(File.class, Charset.class);
            return true;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }
}
