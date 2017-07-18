package info.nordbyen.Ziputils;

public class ZipBreakException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public ZipBreakException() {
    }
    
    public ZipBreakException(final Exception e) {
        super(e);
    }
    
    public ZipBreakException(final String msg) {
        super(msg);
    }
}
