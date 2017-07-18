package info.nordbyen.survivalheaven.subplugins.bitly.util;

public class BitlyException extends RuntimeException
{
    private static final long serialVersionUID = 8300631062123036696L;
    
    BitlyException(final String message) {
        super(message);
    }
    
    BitlyException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
