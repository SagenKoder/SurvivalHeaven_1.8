package info.nordbyen.survivalheaven.api.util.shape;

public class Point3Di
{
    private int x;
    private int y;
    private int z;
    
    public Point3Di(final int x, final int y, final int z) {
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
