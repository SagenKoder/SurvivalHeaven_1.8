package info.nordbyen.Ziputils.transform;

import info.nordbyen.Ziputils.*;
import info.nordbyen.Ziputils.commons.*;
import java.io.*;
import java.util.zip.*;

public class ZipEntrySourceZipEntryTransformer implements ZipEntryTransformer
{
    private final ZipEntrySource source;
    
    static void addEntry(final ZipEntrySource entry, final ZipOutputStream out) throws IOException {
        out.putNextEntry(entry.getEntry());
        final InputStream in = entry.getInputStream();
        if (in != null) {
            try {
                IOUtils.copy(in, out);
            }
            finally {
                IOUtils.closeQuietly(in);
            }
            IOUtils.closeQuietly(in);
        }
        out.closeEntry();
    }
    
    public ZipEntrySourceZipEntryTransformer(final ZipEntrySource source) {
        this.source = source;
    }
    
    @Override
    public void transform(final InputStream in, final ZipEntry zipEntry, final ZipOutputStream out) throws IOException {
        addEntry(this.source, out);
    }
}
