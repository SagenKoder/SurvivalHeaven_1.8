package info.nordbyen.Ziputils;

import java.io.*;

class ZipExceptionUtil
{
    static ZipException rethrow(final IOException e) {
        throw new ZipException(e);
    }
}
