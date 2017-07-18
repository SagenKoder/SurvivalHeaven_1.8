package info.nordbyen.Ziputils.commons;

import java.io.*;
import java.util.*;

public class FileUtils
{
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = 1048576L;
    public static final long ONE_GB = 1073741824L;
    public static final File[] EMPTY_FILE_ARRAY;
    
    static {
        EMPTY_FILE_ARRAY = new File[0];
    }
    
    public static void cleanDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        final File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }
        IOException exception = null;
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            try {
                forceDelete(file);
            }
            catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
    
    private static void cleanDirectoryOnExit(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        final File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }
        IOException exception = null;
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            try {
                forceDeleteOnExit(file);
            }
            catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
    
    public static boolean contentEquals(final File file1, final File file2) throws IOException {
        final boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        if (file1.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file1.length() != file2.length()) {
            return false;
        }
        if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        InputStream input1 = null;
        InputStream input2 = null;
        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            return IOUtils.contentEquals(input1, input2);
        }
        finally {
            IOUtils.closeQuietly(input1);
            IOUtils.closeQuietly(input2);
        }
    }
    
    public static void copy(final File file, final OutputStream out) throws IOException {
        final FileInputStream in = new FileInputStream(file);
        try {
            IOUtils.copy(new BufferedInputStream(in), out);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
        IOUtils.closeQuietly(in);
    }
    
    public static void copy(final InputStream in, final File file) throws IOException {
        final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            IOUtils.copy(in, out);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }
    
    public static void copyDirectory(final File srcDir, final File destDir, final boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }
    
    public static void copyDirectory(final File srcDir, final File destDir, final FileFilter filter, final boolean preserveFileDate) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }
        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            final File[] srcFiles = (filter == null) ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<String>(srcFiles.length);
                for (int i = 0; i < srcFiles.length; ++i) {
                    final File copiedFile = new File(destDir, srcFiles[i].getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
    }
    
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }
    
    public static void copyFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        if (destFile.getParentFile() != null && !destFile.getParentFile().exists() && !destFile.getParentFile().mkdirs()) {
            throw new IOException("Destination '" + destFile + "' directory cannot be created");
        }
        if (destFile.exists() && !destFile.canWrite()) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }
    
    public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }
    
    public static void copyFileToDirectory(final File srcFile, final File destDir, final boolean preserveFileDate) throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
        copyFile(srcFile, new File(destDir, srcFile.getName()), preserveFileDate);
    }
    
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectory(directory);
        if (!directory.delete()) {
            final String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }
    
    private static void deleteDirectoryOnExit(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectoryOnExit(directory);
        directory.deleteOnExit();
    }
    
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        }
        catch (Exception ex) {}
        try {
            return file.delete();
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter, final boolean preserveFileDate, final List<String> exclusionList) throws IOException {
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        }
        else {
            if (!destDir.mkdirs()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
            if (preserveFileDate) {
                destDir.setLastModified(srcDir.lastModified());
            }
        }
        if (!destDir.canWrite()) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        final File[] files = (filter == null) ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (files == null) {
            throw new IOException("Failed to list contents of " + srcDir);
        }
        for (int i = 0; i < files.length; ++i) {
            final File copiedFile = new File(destDir, files[i].getName());
            if (exclusionList == null || !exclusionList.contains(files[i].getCanonicalPath())) {
                if (files[i].isDirectory()) {
                    doCopyDirectory(files[i], copiedFile, filter, preserveFileDate, exclusionList);
                }
                else {
                    doCopyFile(files[i], copiedFile, preserveFileDate);
                }
            }
        }
    }
    
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }
        final FileInputStream input = new FileInputStream(srcFile);
        try {
            final FileOutputStream output = new FileOutputStream(destFile);
            try {
                IOUtils.copy(input, output);
            }
            finally {
                IOUtils.closeQuietly(output);
            }
            IOUtils.closeQuietly(output);
        }
        finally {
            IOUtils.closeQuietly(input);
        }
        IOUtils.closeQuietly(input);
        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
    
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        }
        else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
    
    public static void forceDeleteOnExit(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        }
        else {
            file.deleteOnExit();
        }
    }
    
    public static void forceMkdir(final File directory) throws IOException {
        if (directory.exists()) {
            if (directory.isFile()) {
                final String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        }
        else if (!directory.mkdirs()) {
            final String message = "Unable to create directory " + directory;
            throw new IOException(message);
        }
    }
    
    public static File getTempFileFor(final File file) {
        final File parent = file.getParentFile();
        final String name = file.getName();
        int index = 0;
        File result;
        do {
            result = new File(parent, String.valueOf(name) + "_" + index++);
        } while (result.exists());
        return result;
    }
    
    public static void moveDirectory(final File srcDir, final File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' is not a directory");
        }
        if (destDir.exists()) {
            throw new IOException("Destination '" + destDir + "' already exists");
        }
        final boolean rename = srcDir.renameTo(destDir);
        if (!rename) {
            copyDirectory(srcDir, destDir);
            deleteDirectory(srcDir);
            if (srcDir.exists()) {
                throw new IOException("Failed to delete original directory '" + srcDir + "' after copy to '" + destDir + "'");
            }
        }
    }
    
    public static void moveFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists()) {
            throw new IOException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
            }
        }
    }
    
    public static FileInputStream openInputStream(final File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (!file.canRead()) {
            throw new IOException("File '" + file + "' cannot be read");
        }
        return new FileInputStream(file);
    }
    
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        else {
            final File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("File '" + file + "' could not be created");
            }
        }
        return new FileOutputStream(file);
    }
    
    public static String readFileToString(final File file) throws IOException {
        return readFileToString(file, null);
    }
    
    public static String readFileToString(final File file, final String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.toString(in, encoding);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    public static long sizeOfDirectory(final File directory) {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        long size = 0L;
        final File[] files = directory.listFiles();
        if (files == null) {
            return 0L;
        }
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            if (file.isDirectory()) {
                size += sizeOfDirectory(file);
            }
            else {
                size += file.length();
            }
        }
        return size;
    }
}
