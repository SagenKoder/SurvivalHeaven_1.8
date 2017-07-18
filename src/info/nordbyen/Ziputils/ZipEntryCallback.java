package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

public interface ZipEntryCallback
{
    void process(final InputStream p0, final ZipEntry p1) throws IOException;
}
