package info.nordbyen.Ziputils;

import java.util.zip.*;
import info.nordbyen.Ziputils.commons.*;
import java.io.*;

class ZipEntryUtil
{
    static void addEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out) throws IOException {
        out.putNextEntry(zipEntry);
        if (in != null) {
            IOUtils.copy(in, out);
        }
        out.closeEntry();
    }
    
    static ZipEntry copy(final ZipEntry original) {
        return copy(original, null);
    }
    
    static ZipEntry copy(final ZipEntry original, final String newName) {
        final ZipEntry copy = new ZipEntry((newName == null) ? original.getName() : newName);
        if (original.getCrc() != -1L) {
            copy.setCrc(original.getCrc());
        }
        if (original.getMethod() != -1) {
            copy.setMethod(original.getMethod());
        }
        if (original.getSize() >= 0L) {
            copy.setSize(original.getSize());
        }
        if (original.getExtra() != null) {
            copy.setExtra(original.getExtra());
        }
        copy.setComment(original.getComment());
        copy.setTime(original.getTime());
        return copy;
    }
    
    static void copyEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out) throws IOException {
        copyEntry(zipEntry, in, out, true);
    }
    
    static void copyEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out, final boolean preserveTimestamps) throws IOException {
        final ZipEntry copy = copy(zipEntry);
        copy.setTime(preserveTimestamps ? zipEntry.getTime() : System.currentTimeMillis());
        addEntry(copy, new BufferedInputStream(in), out);
    }
}
