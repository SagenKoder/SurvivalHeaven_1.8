package info.nordbyen.survivalheaven.api.util.shape;

import java.util.*;

public interface Shape
{
    boolean containsPoint(final Point2Di p0);
    
    boolean containsPoint(final int p0, final int p1);
    
    ArrayList<Point2Di> getPointsInPerimeter();
    
    ArrayList<Point2Di> getPointsInArea();
}
