package info.nordbyen.Ziputils;

import java.io.*;
import java.util.*;

public final class ZTFileUtil
{
    private static void innerListFiles(final File dir, final Collection<File> rtrn, final FileFilter filter) {
        final String[] filenames = dir.list();
        if (filenames != null) {
            for (int i = 0; i < filenames.length; ++i) {
                final File file = new File(dir, filenames[i]);
                if (file.isDirectory()) {
                    innerListFiles(file, rtrn, filter);
                }
                else if (filter != null && filter.accept(file)) {
                    rtrn.add(file);
                }
            }
        }
    }
    
    public static Collection<File> listFiles(final File dir) {
        return listFiles(dir, null);
    }
    
    public static Collection<File> listFiles(final File dir, FileFilter filter) {
        final Collection<File> rtrn = new ArrayList<File>();
        if (dir.isFile()) {
            return rtrn;
        }
        if (filter == null) {
            filter = new FileFilter() {
                @Override
                public boolean accept(final File pathname) {
                    return true;
                }
            };
        }
        innerListFiles(dir, rtrn, filter);
        return rtrn;
    }
}
