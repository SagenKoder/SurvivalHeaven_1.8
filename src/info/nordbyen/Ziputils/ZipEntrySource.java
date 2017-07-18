package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

public interface ZipEntrySource
{
    ZipEntry getEntry();
    
    InputStream getInputStream() throws IOException;
    
    String getPath();
}
