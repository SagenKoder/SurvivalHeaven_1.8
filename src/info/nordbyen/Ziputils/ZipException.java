package info.nordbyen.Ziputils;

public class ZipException extends RuntimeException
{
    private static final long serialVersionUID = 5571111112619349468L;
    
    public ZipException(final Exception e) {
        super(e);
    }
    
    public ZipException(final String msg) {
        super(msg);
    }
    
    public ZipException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
