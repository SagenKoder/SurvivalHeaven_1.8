package info.nordbyen.survivalheaven.subplugins.bitly.util;

import java.util.*;

class ParameterMap extends AbstractCollection<Map.Entry<String, List<String>>>
{
    private final Map<String, List<String>> parameters;
    
    ParameterMap() {
        this.parameters = new HashMap<String, List<String>>();
    }
    
    public void add(final String name, final String value) {
        List values = this.parameters.get(name);
        if (values == null) {
            values = new ArrayList();
        }
        values.add(value);
        this.parameters.put(name, values);
    }
    
    public List<String> get(final String name) {
        return this.parameters.get(name);
    }
    
    @Override
    public Iterator<Map.Entry<String, List<String>>> iterator() {
        return this.parameters.entrySet().iterator();
    }
    
    @Override
    public int size() {
        return this.parameters.size();
    }
    
    @Override
    public String toString() {
        return "ParameterMap [parameters=" + this.parameters + "]";
    }
}
