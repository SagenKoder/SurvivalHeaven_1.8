package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

class ZipEntryOrInfoAdapter implements ZipEntryCallback, ZipInfoCallback
{
    private final ZipEntryCallback entryCallback;
    private final ZipInfoCallback infoCallback;
    
    public ZipEntryOrInfoAdapter(final ZipEntryCallback entryCallback, final ZipInfoCallback infoCallback) {
        if ((entryCallback != null && infoCallback != null) || (entryCallback == null && infoCallback == null)) {
            throw new IllegalArgumentException("Only one of ZipEntryCallback and ZipInfoCallback must be specified together");
        }
        this.entryCallback = entryCallback;
        this.infoCallback = infoCallback;
    }
    
    @Override
    public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
        if (this.entryCallback != null) {
            this.entryCallback.process(in, zipEntry);
        }
        else {
            this.process(zipEntry);
        }
    }
    
    @Override
    public void process(final ZipEntry zipEntry) throws IOException {
        this.infoCallback.process(zipEntry);
    }
}
