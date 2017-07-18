package info.nordbyen.Ziputils.transform;

import java.util.zip.*;
import java.io.*;

public abstract class StreamZipEntryTransformer implements ZipEntryTransformer
{
    @Override
    public void transform(final InputStream in, final ZipEntry zipEntry, final ZipOutputStream out) throws IOException {
        final ZipEntry entry = new ZipEntry(zipEntry.getName());
        entry.setTime(System.currentTimeMillis());
        out.putNextEntry(entry);
        this.transform(zipEntry, in, out);
        out.closeEntry();
    }
    
    protected abstract void transform(final ZipEntry p0, final InputStream p1, final OutputStream p2) throws IOException;
}
