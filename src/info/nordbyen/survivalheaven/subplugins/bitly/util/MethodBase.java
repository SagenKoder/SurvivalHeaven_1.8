package info.nordbyen.survivalheaven.subplugins.bitly.util;

import info.nordbyen.survivalheaven.subplugins.bitly.util.data.*;
import java.util.*;

public abstract class MethodBase<A> implements BitlyMethod<A>
{
    private final String name;
    private final Iterable<Pair<String, String>> parameters;
    
    public MethodBase(final String name, final Iterable<Pair<String, String>> parameters) {
        this.name = name;
        this.parameters = parameters;
    }
    
    public MethodBase(final String name, final Pair<String, String>[] parameters) {
        this(name, Arrays.asList(parameters));
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Iterable<Pair<String, String>> getParameters() {
        return this.parameters;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getSimpleName()) + " [name=" + this.name + ", parameters=" + this.parameters + "]";
    }
}
