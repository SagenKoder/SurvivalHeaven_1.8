package info.nordbyen.Ziputils.transform;

import java.util.zip.*;
import info.nordbyen.Ziputils.commons.*;
import info.nordbyen.Ziputils.*;
import java.io.*;

public abstract class ByteArrayZipEntryTransformer implements ZipEntryTransformer
{
    protected boolean preserveTimestamps() {
        return false;
    }
    
    @Override
    public void transform(final InputStream in, final ZipEntry zipEntry, final ZipOutputStream out) throws IOException {
        byte[] bytes = IOUtils.toByteArray(in);
        bytes = this.transform(zipEntry, bytes);
        ByteSource source;
        if (this.preserveTimestamps()) {
            source = new ByteSource(zipEntry.getName(), bytes, zipEntry.getTime());
        }
        else {
            source = new ByteSource(zipEntry.getName(), bytes);
        }
        ZipEntrySourceZipEntryTransformer.addEntry(source, out);
    }
    
    protected abstract byte[] transform(final ZipEntry p0, final byte[] p1) throws IOException;
}
