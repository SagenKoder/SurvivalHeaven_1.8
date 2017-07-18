package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

public class FileSource implements ZipEntrySource
{
    private final String path;
    private final File file;
    
    public static FileSource[] pair(final File[] files, final String[] names) {
        if (files.length > names.length) {
            throw new IllegalArgumentException("names array must contain at least the same amount of items as files array or more");
        }
        final FileSource[] result = new FileSource[files.length];
        for (int i = 0; i < files.length; ++i) {
            result[i] = new FileSource(names[i], files[i]);
        }
        return result;
    }
    
    public FileSource(final String path, final File file) {
        this.path = path;
        this.file = file;
    }
    
    @Override
    public ZipEntry getEntry() {
        final ZipEntry entry = new ZipEntry(this.path);
        if (!this.file.isDirectory()) {
            entry.setSize(this.file.length());
        }
        entry.setTime(this.file.lastModified());
        return entry;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if (this.file.isDirectory()) {
            return null;
        }
        return new BufferedInputStream(new FileInputStream(this.file));
    }
    
    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public String toString() {
        return "FileSource[" + this.path + ", " + this.file + "]";
    }
}
