package info.nordbyen.Ziputils.transform;

import java.io.*;
import java.util.zip.*;
import info.nordbyen.Ziputils.*;
import info.nordbyen.Ziputils.commons.*;

public abstract class FileZipEntryTransformer implements ZipEntryTransformer
{
    private static void copy(final InputStream in, final File file) throws IOException {
        final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            IOUtils.copy(in, out);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    @Override
    public void transform(final InputStream in, final ZipEntry zipEntry, final ZipOutputStream out) throws IOException {
        File inFile = null;
        File outFile = null;
        try {
            inFile = File.createTempFile("zip", null);
            outFile = File.createTempFile("zip", null);
            copy(in, inFile);
            this.transform(zipEntry, inFile, outFile);
            final FileSource source = new FileSource(zipEntry.getName(), outFile);
            ZipEntrySourceZipEntryTransformer.addEntry(source, out);
        }
        finally {
            FileUtils.deleteQuietly(inFile);
            FileUtils.deleteQuietly(outFile);
        }
        FileUtils.deleteQuietly(inFile);
        FileUtils.deleteQuietly(outFile);
    }
    
    protected abstract void transform(final ZipEntry p0, final File p1, final File p2) throws IOException;
}
