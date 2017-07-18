package info.nordbyen.survivalheaven.subplugins.bitly.util;

import org.w3c.dom.*;
import info.nordbyen.survivalheaven.subplugins.bitly.util.data.*;

public interface BitlyMethod<A>
{
    A apply(final Bitly.Provider p0, final Document p1);
    
    String getName();
    
    Iterable<Pair<String, String>> getParameters();
}
