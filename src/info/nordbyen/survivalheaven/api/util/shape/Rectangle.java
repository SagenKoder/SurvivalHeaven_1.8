package info.nordbyen.survivalheaven.api.util.shape;

public class Rectangle
{
    Point2Di bottom;
    Point2Di top;
    
    public Rectangle(final int x1, final int z1, final int x2, final int z2) {
        final int xMin = Math.min(x1, x2);
        final int xMax = Math.max(x1, x2);
        final int zMin = Math.min(z1, z2);
        final int zMax = Math.max(z1, z2);
        this.bottom = new Point2Di(xMin, zMin);
        this.top = new Point2Di(xMax, zMax);
    }
    
    public Rectangle(final Point2Di p1, final Point2Di p2) {
        final int xMin = Math.min(p1.getX(), p2.getX());
        final int xMax = Math.max(p1.getX(), p2.getX());
        final int zMin = Math.min(p1.getZ(), p2.getZ());
        final int zMax = Math.max(p1.getZ(), p2.getZ());
        this.bottom = new Point2Di(xMin, zMin);
        this.top = new Point2Di(xMax, zMax);
    }
    
    public Point2Di getBottom() {
        return this.bottom;
    }
    
    public Point2Di getTop() {
        return this.top;
    }
}
