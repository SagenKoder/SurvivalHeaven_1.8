package info.nordbyen.Ziputils;

import java.nio.charset.*;
import info.nordbyen.Ziputils.transform.*;
import info.nordbyen.Ziputils.commons.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;
import java.util.concurrent.*;

public class Zips
{
    private final File src;
    private File dest;
    private Charset charset;
    private boolean preserveTimestamps;
    private List<ZipEntrySource> changedEntries;
    private Set<String> removedEntries;
    private List<ZipEntryTransformerEntry> transformers;
    private NameMapper nameMapper;
    private boolean unpackedResult;
    
    public static Zips create() {
        return new Zips(null);
    }
    
    public static Zips get(final File src) {
        return new Zips(src);
    }
    
    private Zips(final File src) {
        this.changedEntries = new ArrayList<ZipEntrySource>();
        this.removedEntries = new HashSet<String>();
        this.transformers = new ArrayList<ZipEntryTransformerEntry>();
        this.src = src;
    }
    
    public Zips addEntries(final ZipEntrySource[] entries) {
        this.changedEntries.addAll(Arrays.asList(entries));
        return this;
    }
    
    public Zips addEntry(final ZipEntrySource entry) {
        this.changedEntries.add(entry);
        return this;
    }
    
    public Zips addFile(final File file) {
        return this.addFile(file, false, null);
    }
    
    public Zips addFile(final File file, final boolean preserveRoot) {
        return this.addFile(file, preserveRoot, null);
    }
    
    public Zips addFile(final File file, final boolean preserveRoot, final FileFilter filter) {
        if (!file.isDirectory()) {
            this.changedEntries.add(new FileSource(file.getName(), file));
            return this;
        }
        final Collection<?> files = ZTFileUtil.listFiles(file);
        for (final File entryFile : files) {
            if (filter != null && !filter.accept(entryFile)) {
                continue;
            }
            String entryPath = this.getRelativePath(file, entryFile);
            if (File.separator.equals("\\")) {
                entryPath = entryPath.replace('\\', '/');
            }
            if (preserveRoot) {
                entryPath = String.valueOf(file.getName()) + entryPath;
            }
            if (entryPath.startsWith("/")) {
                entryPath = entryPath.substring(1);
            }
            this.changedEntries.add(new FileSource(entryPath, entryFile));
        }
        return this;
    }
    
    public Zips addFile(final File file, final FileFilter filter) {
        return this.addFile(file, false, filter);
    }
    
    public Zips addTransformer(final String path, final ZipEntryTransformer transformer) {
        this.transformers.add(new ZipEntryTransformerEntry(path, transformer));
        return this;
    }
    
    public Zips charset(final Charset charset) {
        this.charset = charset;
        return this;
    }
    
    public boolean containsEntry(final String name) {
        if (this.src == null) {
            throw new IllegalStateException("Source is not given");
        }
        return ZipUtil.containsEntry(this.src, name);
    }
    
    public Zips destination(final File destination) {
        this.dest = destination;
        return this;
    }
    
    private File getDestinationFile() throws IOException {
        if (this.isUnpack()) {
            if (this.isInPlace()) {
                final File tempFile = File.createTempFile("zips", null);
                FileUtils.deleteQuietly(tempFile);
                tempFile.mkdirs();
                return tempFile;
            }
            if (!this.dest.isDirectory()) {
                FileUtils.deleteQuietly(this.dest);
                final File result = new File(this.dest.getAbsolutePath());
                result.mkdirs();
                return result;
            }
            return this.dest;
        }
        else {
            if (this.isInPlace()) {
                return File.createTempFile("zips", ".zip");
            }
            if (this.dest.isDirectory()) {
                FileUtils.deleteQuietly(this.dest);
                return new File(this.dest.getAbsolutePath());
            }
            return this.dest;
        }
    }
    
    public byte[] getEntry(final String name) {
        if (this.src == null) {
            throw new IllegalStateException("Source is not given");
        }
        return ZipUtil.unpackEntry(this.src, name);
    }
    
    private String getRelativePath(final File parent, final File file) {
        final String parentPath = parent.getPath();
        final String filePath = file.getPath();
        if (!filePath.startsWith(parentPath)) {
            throw new IllegalArgumentException("File " + file + " is not a child of " + parent);
        }
        return filePath.substring(parentPath.length());
    }
    
    private ZipEntryTransformerEntry[] getTransformersArray() {
        final ZipEntryTransformerEntry[] result = new ZipEntryTransformerEntry[this.transformers.size()];
        int idx = 0;
        final Iterator<ZipEntryTransformerEntry> iter = this.transformers.iterator();
        while (iter.hasNext()) {
            result[idx++] = iter.next();
        }
        return result;
    }
    
    private ZipFile getZipFile() throws IOException {
        return ZipFileUtil.getZipFile(this.src, this.charset);
    }
    
    private void handleInPlaceActions(final File result) throws IOException {
        if (this.isInPlace()) {
            FileUtils.forceDelete(this.src);
            if (result.isFile()) {
                FileUtils.moveFile(result, this.src);
            }
            else {
                FileUtils.moveDirectory(result, this.src);
            }
        }
    }
    
    private boolean isEntryInDir(final Set<?> dirNames, final String entryName) {
        for (final String dirName : dirNames) {
            if (entryName.startsWith(dirName)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isInPlace() {
        return this.dest == null;
    }
    
    private boolean isUnpack() {
        return this.unpackedResult || (this.dest != null && this.dest.isDirectory());
    }
    
    public void iterate(final ZipEntryCallback zipEntryCallback) {
        final ZipEntryOrInfoAdapter zipEntryAdapter = new ZipEntryOrInfoAdapter(zipEntryCallback, null);
        this.processAllEntries(zipEntryAdapter);
    }
    
    public void iterate(final ZipInfoCallback callback) {
        final ZipEntryOrInfoAdapter zipEntryAdapter = new ZipEntryOrInfoAdapter(null, callback);
        this.processAllEntries(zipEntryAdapter);
    }
    
    private void iterateChangedAndAdded(final ZipEntryOrInfoAdapter zipEntryCallback) {
        for (final ZipEntrySource entrySource : this.changedEntries) {
            try {
                ZipEntry entry = entrySource.getEntry();
                if (this.nameMapper != null) {
                    final String mappedName = this.nameMapper.map(entry.getName());
                    if (mappedName == null) {
                        continue;
                    }
                    if (!mappedName.equals(entry.getName())) {
                        entry = ZipEntryUtil.copy(entry, mappedName);
                    }
                }
                zipEntryCallback.process(entrySource.getInputStream(), entry);
            }
            catch (ZipBreakException ex) {
                break;
            }
            catch (IOException e) {
                ZipExceptionUtil.rethrow(e);
            }
        }
    }
    
    private void iterateExistingExceptRemoved(final ZipEntryOrInfoAdapter zipEntryCallback) {
        if (this.src == null) {
            return;
        }
        final Set<?> removedDirs = ZipUtil.filterDirEntries(this.src, this.removedEntries);
        ZipFile zf = null;
        try {
            zf = this.getZipFile();
            final Enumeration<?> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)en.nextElement();
                final String entryName = entry.getName();
                if (!this.removedEntries.contains(entryName)) {
                    if (this.isEntryInDir(removedDirs, entryName)) {
                        continue;
                    }
                    if (this.nameMapper != null) {
                        final String mappedName = this.nameMapper.map(entry.getName());
                        if (mappedName == null) {
                            continue;
                        }
                        if (!mappedName.equals(entry.getName())) {
                            entry = ZipEntryUtil.copy(entry, mappedName);
                        }
                    }
                    final InputStream is = zf.getInputStream(entry);
                    try {
                        zipEntryCallback.process(is, entry);
                    }
                    catch (ZipBreakException ex) {
                        break;
                    }
                    finally {
                        IOUtils.closeQuietly(is);
                    }
                    IOUtils.closeQuietly(is);
                }
            }
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
            return;
        }
        finally {
            ZipUtil.closeQuietly(zf);
        }
        ZipUtil.closeQuietly(zf);
    }
    
    public Zips nameMapper(final NameMapper nameMapper) {
        this.nameMapper = nameMapper;
        return this;
    }
    
    public Zips preserveTimestamps() {
        this.preserveTimestamps = true;
        return this;
    }
    
    public void process() {
        if (this.src == null && this.dest == null) {
            throw new IllegalArgumentException("Source and destination shouldn't be null together");
        }
        final ZipEntryTransformerEntry[] transformersArray = this.getTransformersArray();
        File destinationFile = null;
        try {
            destinationFile = this.getDestinationFile();
            ZipOutputStream out = null;
            ZipEntryOrInfoAdapter zipEntryAdapter = null;
            if (destinationFile.isFile()) {
                out = ZipFileUtil.createZipOutputStream(new BufferedOutputStream(new FileOutputStream(destinationFile)), this.charset);
                zipEntryAdapter = new ZipEntryOrInfoAdapter(new CopyingCallback(transformersArray, out, this.preserveTimestamps, null), null);
            }
            else {
                zipEntryAdapter = new ZipEntryOrInfoAdapter(new UnpackingCallback(transformersArray, destinationFile, null), null);
            }
            try {
                this.processAllEntries(zipEntryAdapter);
            }
            finally {
                IOUtils.closeQuietly(out);
            }
            IOUtils.closeQuietly(out);
            this.handleInPlaceActions(destinationFile);
        }
        catch (IOException e) {
            ZipExceptionUtil.rethrow(e);
            return;
        }
        finally {
            if (this.isInPlace()) {
                FileUtils.deleteQuietly(destinationFile);
            }
        }
        if (this.isInPlace()) {
            FileUtils.deleteQuietly(destinationFile);
        }
    }
    
    private void processAllEntries(final ZipEntryOrInfoAdapter zipEntryAdapter) {
        this.iterateChangedAndAdded(zipEntryAdapter);
        this.iterateExistingExceptRemoved(zipEntryAdapter);
    }
    
    public Zips removeEntries(final String[] entries) {
        this.removedEntries.addAll(Arrays.asList(entries));
        return this;
    }
    
    public Zips removeEntry(final String entry) {
        this.removedEntries.add(entry);
        return this;
    }
    
    public Zips setPreserveTimestamps(final boolean preserve) {
        this.preserveTimestamps = preserve;
        return this;
    }
    
    public Zips unpack() {
        this.unpackedResult = true;
        return this;
    }
    
    private static class CopyingCallback implements ZipEntryCallback
    {
        private final Map<?, ?> entryByPath;
        private final ZipOutputStream out;
        private final Set<String> visitedNames;
        private final boolean preserveTimestapms;
        
        private CopyingCallback(final ZipEntryTransformerEntry[] entries, final ZipOutputStream out, final boolean preserveTimestapms) {
            this.out = out;
            this.preserveTimestapms = preserveTimestapms;
            this.entryByPath = ZipUtil.byPath(entries);
            this.visitedNames = new HashSet<String>();
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            final String entryName = zipEntry.getName();
            if (this.visitedNames.contains(entryName)) {
                return;
            }
            this.visitedNames.add(entryName);
            final ZipEntryTransformer transformer = (ZipEntryTransformer)this.entryByPath.remove(entryName);
            if (transformer == null) {
                ZipEntryUtil.copyEntry(zipEntry, in, this.out, this.preserveTimestapms);
            }
            else {
                transformer.transform(in, zipEntry, this.out);
            }
        }
    }
    
    private static class UnpackingCallback implements ZipEntryCallback
    {
        private final Map<?, ?> entryByPath;
        private final Set<String> visitedNames;
        private final File destination;
        
        private UnpackingCallback(final ZipEntryTransformerEntry[] entries, final File destination) {
            this.destination = destination;
            this.entryByPath = ZipUtil.byPath(entries);
            this.visitedNames = new HashSet<String>();
        }
        
        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            final String entryName = zipEntry.getName();
            if (this.visitedNames.contains(entryName)) {
                return;
            }
            this.visitedNames.add(entryName);
            final File file = new File(this.destination, entryName);
            if (zipEntry.isDirectory()) {
                FileUtils.forceMkdir(file);
                return;
            }
            FileUtils.forceMkdir(file.getParentFile());
            file.createNewFile();
            final ZipEntryTransformer transformer = (ZipEntryTransformer)this.entryByPath.remove(entryName);
            if (transformer == null) {
                FileUtils.copy(in, file);
            }
            else {
                this.transformIntoFile(transformer, in, zipEntry, file);
            }
        }
        
        private void transformIntoFile(final ZipEntryTransformer transformer, final InputStream entryIn, final ZipEntry zipEntry, final File destination) throws IOException {
            final PipedInputStream pipedIn = new PipedInputStream();
            final PipedOutputStream pipedOut = new PipedOutputStream(pipedIn);
            final ZipOutputStream zipOut = new ZipOutputStream(pipedOut);
            final ZipInputStream zipIn = new ZipInputStream(pipedIn);
            final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
            try {
                newFixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            transformer.transform(entryIn, zipEntry, zipOut);
                        }
                        catch (IOException e) {
                            ZipExceptionUtil.rethrow(e);
                        }
                    }
                });
                zipIn.getNextEntry();
                FileUtils.copy(zipIn, destination);
            }
            finally {
                try {
                    zipIn.closeEntry();
                }
                catch (IOException ex) {}
                newFixedThreadPool.shutdown();
                IOUtils.closeQuietly(pipedIn);
                IOUtils.closeQuietly(zipIn);
                IOUtils.closeQuietly(pipedOut);
                IOUtils.closeQuietly(zipOut);
            }
            try {
                zipIn.closeEntry();
            }
            catch (IOException ex2) {}
            newFixedThreadPool.shutdown();
            IOUtils.closeQuietly(pipedIn);
            IOUtils.closeQuietly(zipIn);
            IOUtils.closeQuietly(pipedOut);
            IOUtils.closeQuietly(zipOut);
        }
    }
}
