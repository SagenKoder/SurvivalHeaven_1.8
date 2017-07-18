package info.nordbyen.Ziputils;

import info.nordbyen.Ziputils.transform.*;
import java.nio.charset.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;
import info.nordbyen.Ziputils.commons.*;

public final class ZipUtil
{
    private static final String PATH_SEPARATOR = "/";
    public static final int DEFAULT_COMPRESSION_LEVEL = -1;
    
    public static void addEntries(final File zip, final ZipEntrySource[] entries) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.addEntries(zip, entries, tmpFile);
                return true;
            }
        });
    }
    
    public static void addEntries(final File zip, final ZipEntrySource[] entries, final File destZip) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)));
            copyEntries(zip, out);
            for (int i = 0; i < entries.length; ++i) {
                addEntry(entries[i], out);
            }
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
            return;
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    public static void addEntry(final File zip, final String path, final byte[] bytes) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.addEntry(zip, path, bytes, tmpFile);
                return true;
            }
        });
    }
    
    public static void addEntry(final File zip, final String path, final byte[] bytes, final File destZip) {
        addEntry(zip, new ByteSource(path, bytes), destZip);
    }
    
    public static void addEntry(final File zip, final String path, final File file) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.addEntry(zip, path, file, tmpFile);
                return true;
            }
        });
    }
    
    public static void addEntry(final File zip, final String path, final File file, final File destZip) {
        addEntry(zip, new FileSource(path, file), destZip);
    }
    
    public static void addEntry(final File zip, final ZipEntrySource entry) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.addEntry(zip, entry, tmpFile);
                return true;
            }
        });
    }
    
    public static void addEntry(final File zip, final ZipEntrySource entry, final File destZip) {
        addEntries(zip, new ZipEntrySource[] { entry }, destZip);
    }
    
    private static void addEntry(final ZipEntrySource entry, final ZipOutputStream out) throws IOException {
        out.putNextEntry(entry.getEntry());
        final InputStream in = entry.getInputStream();
        if (in != null) {
            try {
                IOUtils.copy(in, out);
            }
            finally {
                IOUtils.closeQuietly(in);
            }
            IOUtils.closeQuietly(in);
        }
        out.closeEntry();
    }
    
    public static void addOrReplaceEntries(final File zip, final ZipEntrySource[] entries) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.addOrReplaceEntries(zip, entries, tmpFile);
                return true;
            }
        });
    }
    
    public static void addOrReplaceEntries(final File zip, final ZipEntrySource[] entries, final File destZip) {
        final Map<String, ZipEntrySource> entryByPath = byPath(entries);
        try {
            final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)));
            try {
                final Set<String> names = new HashSet<String>();
                iterate(zip, new ZipEntryCallback() {
                    @Override
                    public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
                        if (names.add(zipEntry.getName())) {
                            final ZipEntrySource entry = entryByPath.remove(zipEntry.getName());
                            if (entry != null) {
                                addEntry(entry, out);
                            }
                            else {
                                ZipEntryUtil.copyEntry(zipEntry, in, out);
                            }
                        }
                    }
                });
                final Iterator<ZipEntrySource> it = entryByPath.values().iterator();
                while (it.hasNext()) {
                    addEntry(it.next(), out);
                }
            }
            finally {
                IOUtils.closeQuietly(out);
            }
            IOUtils.closeQuietly(out);
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static boolean archiveEquals(final File f1, final File f2) {
        try {
            if (FileUtils.contentEquals(f1, f2)) {
                return true;
            }
            final long start = System.currentTimeMillis();
            final boolean result = archiveEqualsInternal(f1, f2);
            final long time = System.currentTimeMillis() - start;
            return result;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private static boolean archiveEqualsInternal(final File f1, final File f2) throws IOException {
        ZipFile zf1 = null;
        ZipFile zf2 = null;
        try {
            zf1 = new ZipFile(f1);
            zf2 = new ZipFile(f2);
            if (zf1.size() != zf2.size()) {
                return false;
            }
            final Enumeration<?> en = zf1.entries();
            while (en.hasMoreElements()) {
                final ZipEntry e1 = (ZipEntry)en.nextElement();
                final String path = e1.getName();
                final ZipEntry e2 = zf2.getEntry(path);
                if (!metaDataEquals(path, e1, e2)) {
                    return false;
                }
                InputStream is1 = null;
                InputStream is2 = null;
                try {
                    is1 = zf1.getInputStream(e1);
                    is2 = zf2.getInputStream(e2);
                    if (!IOUtils.contentEquals(is1, is2)) {
                        return false;
                    }
                }
                finally {
                    IOUtils.closeQuietly(is1);
                    IOUtils.closeQuietly(is2);
                }
                IOUtils.closeQuietly(is1);
                IOUtils.closeQuietly(is2);
            }
        }
        finally {
            closeQuietly(zf1);
            closeQuietly(zf2);
        }
        closeQuietly(zf1);
        closeQuietly(zf2);
        return true;
    }
    
    static Map<String, ZipEntrySource> byPath(final Collection<?> entries) {
        final Map<String, ZipEntrySource> result = new HashMap<String, ZipEntrySource>();
        for (final ZipEntrySource source : entries) {
            result.put(source.getPath(), source);
        }
        return result;
    }
    
    static Map<String, ZipEntrySource> byPath(final ZipEntrySource[] entries) {
        final Map<String, ZipEntrySource> result = new HashMap<String, ZipEntrySource>();
        for (int i = 0; i < entries.length; ++i) {
            final ZipEntrySource source = entries[i];
            result.put(source.getPath(), source);
        }
        return result;
    }
    
    static Map<String, ZipEntryTransformer> byPath(final ZipEntryTransformerEntry[] entries) {
        final Map<String, ZipEntryTransformer> result = new HashMap<String, ZipEntryTransformer>();
        for (int i = 0; i < entries.length; ++i) {
            final ZipEntryTransformerEntry entry = entries[i];
            result.put(entry.getPath(), entry.getTransformer());
        }
        return result;
    }
    
    public static void closeQuietly(final ZipFile zf) {
        try {
            if (zf != null) {
                zf.close();
            }
        }
        catch (IOException ex) {}
    }
    
    public static boolean containsAnyEntry(final File zip, final String[] names) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            for (int i = 0; i < names.length; ++i) {
                if (zf.getEntry(names[i]) != null) {
                    return true;
                }
            }
            return false;
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf);
        }
    }
    
    public static boolean containsEntry(final File zip, final String name) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            return zf.getEntry(name) != null;
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf);
        }
    }
    
    private static void copyEntries(final File zip, final ZipOutputStream out) {
        final Set<String> names = new HashSet<String>();
        iterate(zip, new ZipEntryCallback() {
            @Override
            public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
                final String entryName = zipEntry.getName();
                if (names.add(entryName)) {
                    ZipEntryUtil.copyEntry(zipEntry, in, out);
                }
            }
        });
    }
    
    private static void copyEntries(final File zip, final ZipOutputStream out, final Set<String> ignoredEntries) {
        final Set<String> names = new HashSet<String>();
        final Set<String> dirNames = filterDirEntries(zip, ignoredEntries);
        iterate(zip, new ZipEntryCallback() {
            @Override
            public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
                final String entryName = zipEntry.getName();
                if (ignoredEntries.contains(entryName)) {
                    return;
                }
                for (final String dirName : dirNames) {
                    if (entryName.startsWith(dirName)) {
                        return;
                    }
                }
                if (names.add(entryName)) {
                    ZipEntryUtil.copyEntry(zipEntry, in, out);
                }
            }
        });
    }
    
    private static boolean doEntryEquals(final ZipFile zf1, final ZipFile zf2, final String path1, final String path2) throws IOException {
        InputStream is1 = null;
        InputStream is2 = null;
        try {
            final ZipEntry e1 = zf1.getEntry(path1);
            final ZipEntry e2 = zf2.getEntry(path2);
            if (e1 == null && e2 == null) {
                return true;
            }
            if (e1 == null || e2 == null) {
                return false;
            }
            is1 = zf1.getInputStream(e1);
            is2 = zf2.getInputStream(e2);
            return (is1 == null && is2 == null) || (is1 != null && is2 != null && IOUtils.contentEquals(is1, is2));
        }
        finally {
            IOUtils.closeQuietly(is1);
            IOUtils.closeQuietly(is2);
        }
    }
    
    private static byte[] doUnpackEntry(final ZipFile zf, final String name) throws IOException {
        final ZipEntry ze = zf.getEntry(name);
        if (ze == null) {
            return null;
        }
        final InputStream is = zf.getInputStream(ze);
        try {
            return IOUtils.toByteArray(is);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    private static boolean doUnpackEntry(final ZipFile zf, final String name, final File file) throws IOException {
        final ZipEntry ze = zf.getEntry(name);
        if (ze == null) {
            return false;
        }
        final InputStream in = new BufferedInputStream(zf.getInputStream(ze));
        try {
            FileUtils.copy(in, file);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
        IOUtils.closeQuietly(in);
        return true;
    }
    
    public static boolean entryEquals(final File f1, final File f2, final String path) {
        return entryEquals(f1, f2, path, path);
    }
    
    public static boolean entryEquals(final File f1, final File f2, final String path1, final String path2) {
        ZipFile zf1 = null;
        ZipFile zf2 = null;
        try {
            zf1 = new ZipFile(f1);
            zf2 = new ZipFile(f2);
            return doEntryEquals(zf1, zf2, path1, path2);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf1);
            closeQuietly(zf2);
        }
    }
    
    public static boolean entryEquals(final ZipFile zf1, final ZipFile zf2, final String path1, final String path2) {
        try {
            return doEntryEquals(zf1, zf2, path1, path2);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static void explode(final File zip) {
        try {
            final File tempFile = FileUtils.getTempFileFor(zip);
            FileUtils.moveFile(zip, tempFile);
            unpack(tempFile, zip);
            if (!tempFile.delete()) {
                throw new IOException("Unable to delete file: " + tempFile);
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    static Set<String> filterDirEntries(final File zip, final Collection<String> names) {
        final Set<String> dirs = new HashSet<String>();
        if (zip == null) {
            return dirs;
        }
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            for (final String entryName : names) {
                final ZipEntry entry = zf.getEntry(entryName);
                if (entry.isDirectory()) {
                    dirs.add(entry.getName());
                }
                else {
                    if (zf.getInputStream(entry) != null) {
                        continue;
                    }
                    dirs.add(String.valueOf(entry.getName()) + "/");
                }
            }
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
            return dirs;
        }
        finally {
            closeQuietly(zf);
        }
        closeQuietly(zf);
        return dirs;
    }
    
    public static boolean handle(final File zip, final String name, final ZipEntryCallback action) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            final ZipEntry ze = zf.getEntry(name);
            if (ze == null) {
                return false;
            }
            final InputStream in = new BufferedInputStream(zf.getInputStream(ze));
            try {
                action.process(in, ze);
            }
            finally {
                IOUtils.closeQuietly(in);
            }
            IOUtils.closeQuietly(in);
            return true;
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf);
        }
    }
    
    public static boolean handle(final InputStream is, final String name, final ZipEntryCallback action) {
        final SingleZipEntryCallback helper = new SingleZipEntryCallback(name, action);
        iterate(is, helper);
        return helper.found();
    }
    
    public static void iterate(final File zip, final String[] entryNames, final ZipEntryCallback action) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            for (int i = 0; i < entryNames.length; ++i) {
                final ZipEntry e = zf.getEntry(entryNames[i]);
                if (e != null) {
                    final InputStream is = zf.getInputStream(e);
                    try {
                        action.process(is, e);
                    }
                    catch (IOException ze) {
                        throw new ZipException("Failed to process zip entry '" + e.getName() + " with action " + action, ze);
                    }
                    catch (ZipBreakException ex) {}
                    finally {
                        IOUtils.closeQuietly(is);
                    }
                    IOUtils.closeQuietly(is);
                }
            }
        }
        catch (IOException e2) {
            throw ZipExceptionUtil.rethrow(e2);
        }
        finally {
            closeQuietly(zf);
        }
        closeQuietly(zf);
    }
    
    public static void iterate(final File zip, final String[] entryNames, final ZipInfoCallback action) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            for (int i = 0; i < entryNames.length; ++i) {
                final ZipEntry e = zf.getEntry(entryNames[i]);
                if (e != null) {
                    try {
                        action.process(e);
                    }
                    catch (IOException ze) {
                        throw new ZipException("Failed to process zip entry '" + e.getName() + " with action " + action, ze);
                    }
                    catch (ZipBreakException ex) {
                        break;
                    }
                }
            }
        }
        catch (IOException e2) {
            throw ZipExceptionUtil.rethrow(e2);
        }
        finally {
            closeQuietly(zf);
        }
        closeQuietly(zf);
    }
    
    public static void iterate(final File zip, final ZipEntryCallback action) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            final Enumeration<?> en = zf.entries();
            while (en.hasMoreElements()) {
                final ZipEntry e = (ZipEntry)en.nextElement();
                final InputStream is = zf.getInputStream(e);
                try {
                    action.process(is, e);
                }
                catch (IOException ze) {
                    throw new ZipException("Failed to process zip entry '" + e.getName() + "' with action " + action, ze);
                }
                catch (ZipBreakException ex) {}
                finally {
                    IOUtils.closeQuietly(is);
                }
                IOUtils.closeQuietly(is);
            }
        }
        catch (IOException e2) {
            throw ZipExceptionUtil.rethrow(e2);
        }
        finally {
            closeQuietly(zf);
        }
        closeQuietly(zf);
    }
    
    public static void iterate(final File zip, final ZipInfoCallback action) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            final Enumeration<?> en = zf.entries();
            while (en.hasMoreElements()) {
                final ZipEntry e = (ZipEntry)en.nextElement();
                try {
                    action.process(e);
                }
                catch (IOException ze) {
                    throw new ZipException("Failed to process zip entry '" + e.getName() + " with action " + action, ze);
                }
                catch (ZipBreakException ex) {
                    break;
                }
            }
        }
        catch (IOException e2) {
            throw ZipExceptionUtil.rethrow(e2);
        }
        finally {
            closeQuietly(zf);
        }
        closeQuietly(zf);
    }
    
    public static void iterate(final InputStream is, final String[] entryNames, final ZipEntryCallback action) {
        iterate(is, entryNames, action, null);
    }
    
    public static void iterate(final InputStream is, final String[] entryNames, final ZipEntryCallback action, final Charset charset) {
        final Set<String> namesSet = new HashSet<String>();
        for (int i = 0; i < entryNames.length; ++i) {
            namesSet.add(entryNames[i]);
        }
        try {
            ZipInputStream in = null;
            if (charset == null) {
                in = new ZipInputStream(new BufferedInputStream(is));
            }
            else {
                in = ZipFileUtil.createZipInputStream(is, charset);
            }
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                if (!namesSet.contains(entry.getName())) {
                    continue;
                }
                try {
                    action.process(in, entry);
                }
                catch (IOException ze) {
                    throw new ZipException("Failed to process zip entry '" + entry.getName() + " with action " + action, ze);
                }
                catch (ZipBreakException ex) {
                    break;
                }
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static void iterate(final InputStream is, final ZipEntryCallback action) {
        iterate(is, action, null);
    }
    
    public static void iterate(final InputStream is, final ZipEntryCallback action, final Charset charset) {
        try {
            ZipInputStream in = null;
            if (charset == null) {
                in = new ZipInputStream(new BufferedInputStream(is));
            }
            else {
                in = ZipFileUtil.createZipInputStream(is, charset);
            }
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                try {
                    action.process(in, entry);
                }
                catch (IOException ze) {
                    throw new ZipException("Failed to process zip entry '" + entry.getName() + " with action " + action, ze);
                }
                catch (ZipBreakException ex) {
                    break;
                }
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    private static boolean metaDataEquals(final String path, final ZipEntry e1, final ZipEntry e2) throws IOException {
        if (e2 == null) {
            return false;
        }
        if (e1.isDirectory()) {
            return e2.isDirectory();
        }
        if (e2.isDirectory()) {
            return false;
        }
        final long size1 = e1.getSize();
        final long size2 = e2.getSize();
        if (size1 != -1L && size2 != -1L && size1 != size2) {
            return false;
        }
        final long crc1 = e1.getCrc();
        final long crc2 = e2.getCrc();
        return crc1 == -1L || crc2 == -1L || crc1 == crc2;
    }
    
    private static boolean operateInPlace(final File src, final InPlaceAction action) {
        File tmp = null;
        try {
            tmp = File.createTempFile("zt-zip-tmp", ".zip");
            final boolean result = action.act(tmp);
            if (result) {
                FileUtils.forceDelete(src);
                FileUtils.moveFile(tmp, src);
            }
            return result;
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            FileUtils.deleteQuietly(tmp);
        }
    }
    
    public static void pack(final File rootDir, final File zip) {
        pack(rootDir, zip, -1);
    }
    
    public static void pack(final File sourceDir, final File targetZipFile, final boolean preserveRoot) {
        if (preserveRoot) {
            final String parentName = sourceDir.getName();
            pack(sourceDir, targetZipFile, new NameMapper() {
                @Override
                public String map(final String name) {
                    return String.valueOf(parentName) + "/" + name;
                }
            });
        }
        else {
            pack(sourceDir, targetZipFile);
        }
    }
    
    public static void pack(final File rootDir, final File zip, final int compressionLevel) {
        pack(rootDir, zip, IdentityNameMapper.INSTANCE, compressionLevel);
    }
    
    public static void pack(final File sourceDir, final File targetZip, final NameMapper mapper) {
        pack(sourceDir, targetZip, mapper, -1);
    }
    
    public static void pack(final File sourceDir, final File targetZip, final NameMapper mapper, final int compressionLevel) {
        if (!sourceDir.exists()) {
            throw new ZipException("Given file '" + sourceDir + "' doesn't exist!");
        }
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(targetZip)));
            out.setLevel(compressionLevel);
            pack(sourceDir, out, mapper, "", true);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    private static void pack(final File dir, final ZipOutputStream out, final NameMapper mapper, final String pathPrefix, final boolean mustHaveChildren) throws IOException {
        final String[] filenames = dir.list();
        if (filenames == null) {
            if (!dir.exists()) {
                throw new ZipException("Given file '" + dir + "' doesn't exist!");
            }
            throw new IOException("Given file is not a directory '" + dir + "'");
        }
        else {
            if (mustHaveChildren && filenames.length == 0) {
                throw new ZipException("Given directory '" + dir + "' doesn't contain any files!");
            }
            for (int i = 0; i < filenames.length; ++i) {
                final String filename = filenames[i];
                final File file = new File(dir, filename);
                final boolean isDir = file.isDirectory();
                String path = String.valueOf(pathPrefix) + file.getName();
                if (isDir) {
                    path = String.valueOf(path) + "/";
                }
                final String name = mapper.map(path);
                if (name != null) {
                    final ZipEntry zipEntry = new ZipEntry(name);
                    if (!isDir) {
                        zipEntry.setSize(file.length());
                        zipEntry.setTime(file.lastModified());
                    }
                    out.putNextEntry(zipEntry);
                    if (!isDir) {
                        FileUtils.copy(file, out);
                    }
                    out.closeEntry();
                }
                if (isDir) {
                    pack(file, out, mapper, path, false);
                }
            }
        }
    }
    
    public static void pack(final ZipEntrySource[] entries, final File zip) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip)));
            for (int i = 0; i < entries.length; ++i) {
                addEntry(entries[i], out);
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    public static void packEntries(final File[] filesToPack, final File destZipFile) {
        packEntries(filesToPack, destZipFile, IdentityNameMapper.INSTANCE);
    }
    
    public static void packEntries(final File[] filesToPack, final File destZipFile, final NameMapper mapper) {
        ZipOutputStream out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destZipFile);
            out = new ZipOutputStream(new BufferedOutputStream(fos));
            for (int i = 0; i < filesToPack.length; ++i) {
                final File fileToPack = filesToPack[i];
                final ZipEntry zipEntry = new ZipEntry(mapper.map(fileToPack.getName()));
                zipEntry.setSize(fileToPack.length());
                zipEntry.setTime(fileToPack.lastModified());
                out.putNextEntry(zipEntry);
                FileUtils.copy(fileToPack, out);
                out.closeEntry();
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(fos);
        }
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(fos);
    }
    
    public static byte[] packEntry(final File file) {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            final ZipOutputStream out = new ZipOutputStream(result);
            final ZipEntry entry = new ZipEntry(file.getName());
            entry.setTime(file.lastModified());
            final InputStream in = new BufferedInputStream(new FileInputStream(file));
            try {
                ZipEntryUtil.addEntry(entry, in, out);
            }
            finally {
                IOUtils.closeQuietly(in);
            }
            IOUtils.closeQuietly(in);
            out.close();
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        return result.toByteArray();
    }
    
    public static void packEntry(final File fileToPack, final File destZipFile) {
        packEntry(fileToPack, destZipFile, IdentityNameMapper.INSTANCE);
    }
    
    public static void packEntry(final File fileToPack, final File destZipFile, final NameMapper mapper) {
        packEntries(new File[] { fileToPack }, destZipFile, mapper);
    }
    
    public static void packEntry(final File fileToPack, final File destZipFile, final String fileName) {
        packEntry(fileToPack, destZipFile, new NameMapper() {
            @Override
            public String map(final String name) {
                return fileName;
            }
        });
    }
    
    public static void removeEntries(final File zip, final String[] paths) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.removeEntries(zip, paths, tmpFile);
                return true;
            }
        });
    }
    
    public static void removeEntries(final File zip, final String[] paths, final File destZip) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)));
            copyEntries(zip, out, new HashSet<String>(Arrays.asList(paths)));
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
        IOUtils.closeQuietly(out);
    }
    
    public static void removeEntry(final File zip, final String path) {
        operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                ZipUtil.removeEntry(zip, path, tmpFile);
                return true;
            }
        });
    }
    
    public static void removeEntry(final File zip, final String path, final File destZip) {
        removeEntries(zip, new String[] { path }, destZip);
    }
    
    public static void repack(final File srcZip, final File dstZip, final int compressionLevel) {
        final RepackZipEntryCallback callback = new RepackZipEntryCallback(dstZip, compressionLevel, null);
        try {
            iterate(srcZip, callback);
        }
        finally {
            callback.closeStream();
        }
        callback.closeStream();
    }
    
    public static void repack(final File zip, final int compressionLevel) {
        try {
            final File tmpZip = FileUtils.getTempFileFor(zip);
            repack(zip, tmpZip, compressionLevel);
            if (!zip.delete()) {
                throw new IOException("Unable to delete the file: " + zip);
            }
            FileUtils.moveFile(tmpZip, zip);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static void repack(final InputStream is, final File dstZip, final int compressionLevel) {
        final RepackZipEntryCallback callback = new RepackZipEntryCallback(dstZip, compressionLevel, null);
        try {
            iterate(is, callback);
        }
        finally {
            callback.closeStream();
        }
        callback.closeStream();
    }
    
    public static boolean replaceEntries(final File zip, final ZipEntrySource[] entries) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.replaceEntries(zip, entries, tmpFile);
            }
        });
    }
    
    public static boolean replaceEntries(final File zip, final ZipEntrySource[] entries, final File destZip) {
        final Map<String, ZipEntrySource> entryByPath = byPath(entries);
        final int entryCount = entryByPath.size();
        try {
            final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)));
            try {
                final Set<String> names = new HashSet<String>();
                iterate(zip, new ZipEntryCallback() {
                    @Override
                    public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
                        if (names.add(zipEntry.getName())) {
                            final ZipEntrySource entry = entryByPath.remove(zipEntry.getName());
                            if (entry != null) {
                                addEntry(entry, out);
                            }
                            else {
                                ZipEntryUtil.copyEntry(zipEntry, in, out);
                            }
                        }
                    }
                });
            }
            finally {
                IOUtils.closeQuietly(out);
            }
            IOUtils.closeQuietly(out);
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
        }
        return entryByPath.size() < entryCount;
    }
    
    public static boolean replaceEntry(final File zip, final String path, final byte[] bytes) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.replaceEntry(zip, new ByteSource(path, bytes), tmpFile);
            }
        });
    }
    
    public static boolean replaceEntry(final File zip, final String path, final byte[] bytes, final File destZip) {
        return replaceEntry(zip, new ByteSource(path, bytes), destZip);
    }
    
    public static boolean replaceEntry(final File zip, final String path, final File file) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.replaceEntry(zip, new FileSource(path, file), tmpFile);
            }
        });
    }
    
    public static boolean replaceEntry(final File zip, final String path, final File file, final File destZip) {
        return replaceEntry(zip, new FileSource(path, file), destZip);
    }
    
    public static boolean replaceEntry(final File zip, final ZipEntrySource entry) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.replaceEntry(zip, entry, tmpFile);
            }
        });
    }
    
    public static boolean replaceEntry(final File zip, final ZipEntrySource entry, final File destZip) {
        return replaceEntries(zip, new ZipEntrySource[] { entry }, destZip);
    }
    
    public static boolean transformEntries(final File zip, final ZipEntryTransformerEntry[] entries) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.transformEntries(zip, entries, tmpFile);
            }
        });
    }
    
    public static boolean transformEntries(final File zip, final ZipEntryTransformerEntry[] entries, final File destZip) {
        try {
            final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)));
            try {
                final TransformerZipEntryCallback action = new TransformerZipEntryCallback(entries, out);
                iterate(zip, action);
                return action.found();
            }
            finally {
                IOUtils.closeQuietly(out);
            }
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static boolean transformEntries(final InputStream is, final ZipEntryTransformerEntry[] entries, final OutputStream os) {
        try {
            final ZipOutputStream out = new ZipOutputStream(os);
            final TransformerZipEntryCallback action = new TransformerZipEntryCallback(entries, out);
            iterate(is, action);
            out.finish();
            return action.found();
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static boolean transformEntry(final File zip, final String path, final ZipEntryTransformer transformer) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.transformEntry(zip, path, transformer, tmpFile);
            }
        });
    }
    
    public static boolean transformEntry(final File zip, final String path, final ZipEntryTransformer transformer, final File destZip) {
        return transformEntry(zip, new ZipEntryTransformerEntry(path, transformer), destZip);
    }
    
    public static boolean transformEntry(final File zip, final ZipEntryTransformerEntry entry) {
        return operateInPlace(zip, new InPlaceAction() {
            public boolean act(final File tmpFile) {
                return ZipUtil.transformEntry(zip, entry, tmpFile);
            }
        });
    }
    
    public static boolean transformEntry(final File zip, final ZipEntryTransformerEntry entry, final File destZip) {
        return transformEntries(zip, new ZipEntryTransformerEntry[] { entry }, destZip);
    }
    
    public static boolean transformEntry(final InputStream is, final String path, final ZipEntryTransformer transformer, final OutputStream os) {
        return transformEntry(is, new ZipEntryTransformerEntry(path, transformer), os);
    }
    
    public static boolean transformEntry(final InputStream is, final ZipEntryTransformerEntry entry, final OutputStream os) {
        return transformEntries(is, new ZipEntryTransformerEntry[] { entry }, os);
    }
    
    public static void unexplode(final File dir) {
        unexplode(dir, -1);
    }
    
    public static void unexplode(final File dir, final int compressionLevel) {
        try {
            final File zip = FileUtils.getTempFileFor(dir);
            pack(dir, zip, compressionLevel);
            FileUtils.deleteDirectory(dir);
            FileUtils.moveFile(zip, dir);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static void unpack(final File zip, final File outputDir) {
        unpack(zip, outputDir, IdentityNameMapper.INSTANCE);
    }
    
    public static void unpack(final File zip, final File outputDir, final NameMapper mapper) {
        iterate(zip, new Unpacker(outputDir, mapper));
    }
    
    public static void unpack(final InputStream is, final File outputDir) {
        unpack(is, outputDir, IdentityNameMapper.INSTANCE);
    }
    
    public static void unpack(final InputStream is, final File outputDir, final NameMapper mapper) {
        iterate(is, new Unpacker(outputDir, mapper));
    }
    
    public static byte[] unpackEntry(final File zip, final String name) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            return doUnpackEntry(zf, name);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf);
        }
    }
    
    public static boolean unpackEntry(final File zip, final String name, final File file) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            return doUnpackEntry(zf, name, file);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
        finally {
            closeQuietly(zf);
        }
    }
    
    public static byte[] unpackEntry(final InputStream is, final String name) {
        final ByteArrayUnpacker action = new ByteArrayUnpacker(null);
        if (!handle(is, name, action)) {
            return null;
        }
        return action.getBytes();
    }
    
    public static boolean unpackEntry(final InputStream is, final String name, final File file) throws IOException {
        return handle(is, name, new FileUnpacker(file));
    }
    
    public static byte[] unpackEntry(final ZipFile zf, final String name) {
        try {
            return doUnpackEntry(zf, name);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static boolean unpackEntry(final ZipFile zf, final String name, final File file) {
        try {
            return doUnpackEntry(zf, name, file);
        }
        catch (IOException e) {
            throw ZipExceptionUtil.rethrow(e);
        }
    }
    
    public static void unwrap(final File zip, final File outputDir) {
        unwrap(zip, outputDir, IdentityNameMapper.INSTANCE);
    }
    
    public static void unwrap(final File zip, final File outputDir, final NameMapper mapper) {
        iterate(zip, new Unwraper(outputDir, mapper));
    }
    
    public static void unwrap(final InputStream is, final File outputDir) {
        unwrap(is, outputDir, IdentityNameMapper.INSTANCE);
    }
    
    public static void unwrap(final InputStream is, final File outputDir, final NameMapper mapper) {
        iterate(is, new Unwraper(outputDir, mapper));
    }
    
    private static class ByteArrayUnpacker implements ZipEntryCallback
    {
        private byte[] bytes;
        
        public byte[] getBytes() {
            return this.bytes;
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            this.bytes = IOUtils.toByteArray(in);
        }
    }
    
    private static class FileUnpacker implements ZipEntryCallback
    {
        private final File file;
        
        public FileUnpacker(final File file) {
            this.file = file;
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            FileUtils.copy(in, this.file);
        }
    }
    
    private abstract static class InPlaceAction
    {
        abstract boolean act(final File p0);
    }
    
    private static final class RepackZipEntryCallback implements ZipEntryCallback
    {
        private ZipOutputStream out;
        
        private RepackZipEntryCallback(final File dstZip, final int compressionLevel) {
            try {
                (this.out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dstZip)))).setLevel(compressionLevel);
            }
            catch (IOException e) {
                ZipExceptionUtil.rethrow(e);
            }
        }
        
        private void closeStream() {
            IOUtils.closeQuietly(this.out);
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            ZipEntryUtil.copyEntry(zipEntry, in, this.out);
        }
    }
    
    private static class SingleZipEntryCallback implements ZipEntryCallback
    {
        private final String name;
        private final ZipEntryCallback action;
        private boolean found;
        
        public SingleZipEntryCallback(final String name, final ZipEntryCallback action) {
            this.name = name;
            this.action = action;
        }
        
        public boolean found() {
            return this.found;
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            if (this.name.equals(zipEntry.getName())) {
                this.found = true;
                this.action.process(in, zipEntry);
            }
        }
    }
    
    private static class TransformerZipEntryCallback implements ZipEntryCallback
    {
        private final Map<String, ZipEntryTransformer> entryByPath;
        private final int entryCount;
        private final ZipOutputStream out;
        private final Set<String> names;
        
        public TransformerZipEntryCallback(final ZipEntryTransformerEntry[] entries, final ZipOutputStream out) {
            this.names = new HashSet<String>();
            this.entryByPath = ZipUtil.byPath(entries);
            this.entryCount = this.entryByPath.size();
            this.out = out;
        }
        
        public boolean found() {
            return this.entryByPath.size() < this.entryCount;
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            if (this.names.add(zipEntry.getName())) {
                final ZipEntryTransformer entry = this.entryByPath.remove(zipEntry.getName());
                if (entry != null) {
                    entry.transform(in, zipEntry, this.out);
                }
                else {
                    ZipEntryUtil.copyEntry(zipEntry, in, this.out);
                }
            }
        }
    }
    
    private static class Unpacker implements ZipEntryCallback
    {
        private final File outputDir;
        private final NameMapper mapper;
        
        public Unpacker(final File outputDir, final NameMapper mapper) {
            this.outputDir = outputDir;
            this.mapper = mapper;
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            final String name = this.mapper.map(zipEntry.getName());
            if (name != null) {
                final File file = new File(this.outputDir, name);
                if (zipEntry.isDirectory()) {
                    FileUtils.forceMkdir(file);
                }
                else {
                    FileUtils.forceMkdir(file.getParentFile());
                    FileUtils.copy(in, file);
                }
            }
        }
    }
    
    private static class Unwraper implements ZipEntryCallback
    {
        private final File outputDir;
        private final NameMapper mapper;
        private String rootDir;
        
        public Unwraper(final File outputDir, final NameMapper mapper) {
            this.outputDir = outputDir;
            this.mapper = mapper;
        }
        
        private String getRootName(final String name) {
            final String newName = name.substring(FilenameUtils.getPrefixLength(name));
            final int idx = newName.indexOf("/");
            if (idx < 0) {
                throw new ZipException("Entry " + newName + " from the root of the zip is not supported");
            }
            return newName.substring(0, newName.indexOf("/"));
        }
        
        private String getUnrootedName(final String root, final String name) {
            return name.substring(root.length());
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            final String root = this.getRootName(zipEntry.getName());
            if (this.rootDir == null) {
                this.rootDir = root;
            }
            else if (!this.rootDir.equals(root)) {
                throw new ZipException("Unwrapping with multiple roots is not supported, roots: " + this.rootDir + ", " + root);
            }
            final String name = this.mapper.map(this.getUnrootedName(root, zipEntry.getName()));
            if (name != null) {
                final File file = new File(this.outputDir, name);
                if (zipEntry.isDirectory()) {
                    FileUtils.forceMkdir(file);
                }
                else {
                    FileUtils.forceMkdir(file.getParentFile());
                    FileUtils.copy(in, file);
                }
            }
        }
    }
}
