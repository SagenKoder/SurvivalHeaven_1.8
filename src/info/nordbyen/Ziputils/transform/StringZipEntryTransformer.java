package info.nordbyen.Ziputils.transform;

import java.util.zip.*;
import info.nordbyen.Ziputils.commons.*;
import info.nordbyen.Ziputils.*;
import java.io.*;

public abstract class StringZipEntryTransformer implements ZipEntryTransformer
{
    private final String encoding;
    
    public StringZipEntryTransformer() {
        this(null);
    }
    
    public StringZipEntryTransformer(final String encoding) {
        this.encoding = encoding;
    }
    
    @Override
    public void transform(final InputStream in, final ZipEntry zipEntry, final ZipOutputStream out) throws IOException {
        String data = IOUtils.toString(in, this.encoding);
        data = this.transform(zipEntry, data);
        final byte[] bytes = (this.encoding == null) ? data.getBytes() : data.getBytes(this.encoding);
        final ByteSource source = new ByteSource(zipEntry.getName(), bytes);
        ZipEntrySourceZipEntryTransformer.addEntry(source, out);
    }
    
    protected abstract String transform(final ZipEntry p0, final String p1) throws IOException;
}
