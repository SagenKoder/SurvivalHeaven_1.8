package info.nordbyen.Ziputils.transform;

public class ZipEntryTransformerEntry
{
    private final String path;
    private final ZipEntryTransformer transformer;
    
    public ZipEntryTransformerEntry(final String path, final ZipEntryTransformer transformer) {
        this.path = path;
        this.transformer = transformer;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public ZipEntryTransformer getTransformer() {
        return this.transformer;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.path) + "=" + this.transformer;
    }
}
