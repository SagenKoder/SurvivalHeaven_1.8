package info.nordbyen.survivalheaven.api.util.shape;

import java.util.*;

public class Polygon2D implements Shape
{
    ArrayList<Point2Di> vertices;
    
    public Polygon2D() {
        this.vertices = new ArrayList<Point2Di>();
    }
    
    public Polygon2D(final ArrayList<Point2Di> vertices) {
        this.vertices = new ArrayList<Point2Di>();
        this.vertices = vertices;
    }
    
    public void addVertex(final Point2Di p) {
        this.vertices.add(p);
    }
    
    @Override
    public boolean containsPoint(final Point2Di p) {
        return false;
    }
    
    @Override
    public boolean containsPoint(final int x, final int z) {
        return false;
    }
    
    @Override
    public ArrayList<Point2Di> getPointsInPerimeter() {
        return null;
    }
    
    @Override
    public ArrayList<Point2Di> getPointsInArea() {
        return null;
    }
}
