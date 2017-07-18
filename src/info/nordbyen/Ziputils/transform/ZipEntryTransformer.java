package info.nordbyen.Ziputils.transform;

import java.util.zip.*;
import java.io.*;

public interface ZipEntryTransformer
{
    void transform(final InputStream p0, final ZipEntry p1, final ZipOutputStream p2) throws IOException;
}
