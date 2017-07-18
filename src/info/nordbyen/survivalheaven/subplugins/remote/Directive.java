package info.nordbyen.survivalheaven.subplugins.remote;

public enum Directive
{
    INTERACTIVE("INTERACTIVE", 0, ""), 
    NOLOG("NOLOG", 1, "NOLOG");
    
    public final String qualifier;
    
    public static Directive toDirective(final String raw) {
        Directive[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Directive d = values[i];
            if (d.toString().equalsIgnoreCase(raw)) {
                return d;
            }
        }
        return null;
    }
    
    private Directive(final String s, final int n, final String qualifier) {
        this.qualifier = qualifier;
    }
    
    @Override
    public String toString() {
        return this.qualifier;
    }
}
