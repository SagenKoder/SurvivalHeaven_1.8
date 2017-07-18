package info.nordbyen.survivalheaven.person;

public class Vektor
{
    private final int x;
    private final int y;
    private final int z;
    
    public Vektor(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
}
