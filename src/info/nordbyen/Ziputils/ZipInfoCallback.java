package info.nordbyen.Ziputils;

import java.util.zip.*;
import java.io.*;

public interface ZipInfoCallback
{
    void process(final ZipEntry p0) throws IOException;
}
