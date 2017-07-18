package info.nordbyen.survivalheaven.subplugins.bitly.util.data;

public final class Pair<A, B>
{
    private final A one;
    private final B two;
    
    public static <A, B> Pair<A, B> p(final A one, final B two) {
        return new Pair<A, B>(one, two);
    }
    
    private Pair(final A one, final B two) {
        this.one = one;
        this.two = two;
    }
    
    public A getOne() {
        return this.one;
    }
    
    public B getTwo() {
        return this.two;
    }
    
    @Override
    public String toString() {
        return "Pair [one=" + this.one + ", two=" + this.two + "]";
    }
}
