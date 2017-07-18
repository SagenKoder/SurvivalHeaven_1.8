package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

public class ByteSource implements ZipEntrySource
{
    private final String path;
    private final byte[] bytes;
    private final long time;
    
    public ByteSource(final String path, final byte[] bytes) {
        this(path, bytes, System.currentTimeMillis());
    }
    
    public ByteSource(final String path, final byte[] bytes, final long time) {
        this.path = path;
        this.bytes = bytes.clone();
        this.time = time;
    }
    
    @Override
    public ZipEntry getEntry() {
        final ZipEntry entry = new ZipEntry(this.path);
        if (this.bytes != null) {
            entry.setSize(this.bytes.length);
        }
        entry.setTime(this.time);
        return entry;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if (this.bytes == null) {
            return null;
        }
        return new ByteArrayInputStream(this.bytes);
    }
    
    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public String toString() {
        return "ByteSource[" + this.path + "]";
    }
}
