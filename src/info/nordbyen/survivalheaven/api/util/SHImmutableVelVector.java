package info.nordbyen.survivalheaven.api.util;

import org.bukkit.util.*;
import java.util.*;

public class SHImmutableVelVector implements Cloneable
{
    private static final double epsilon = 1.0E-6;
    private static final SHImmutableVelVector ZERO;
    private final double x;
    private final double y;
    private final double z;
    private final double length;
    private final double lengthSquared;
    
    static {
        ZERO = new SHImmutableVelVector(0.0, 0.0, 0.0);
    }
    
    public SHImmutableVelVector(final double pitch, final double yaw) {
        final double velY = -Math.sin(Math.toRadians(pitch));
        final double xz = Math.cos(Math.toRadians(yaw));
        final double velX = -xz * Math.sin(Math.toRadians(yaw));
        final double velZ = xz * Math.cos(Math.toRadians(yaw));
        this.x = velX;
        this.y = velY;
        this.z = velZ;
        this.lengthSquared = this.x * this.x + this.y * this.y + this.z * this.z;
        this.length = Math.sqrt(this.lengthSquared);
    }
    
    public SHImmutableVelVector(final SHImmutableVelVector vector) {
        this(vector.x(), vector.y(), vector.z());
    }
    
    public SHImmutableVelVector(final Vector vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public SHImmutableVelVector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.lengthSquared = x * x + y * y + z * z;
        this.length = Math.sqrt(this.lengthSquared);
    }
    
    public double x() {
        return this.x;
    }
    
    public double y() {
        return this.y;
    }
    
    public double z() {
        return this.z;
    }
    
    public double length() {
        return this.length;
    }
    
    public double lengthSquared() {
        return this.lengthSquared;
    }
    
    public SHImmutableVelVector midpoint(final SHImmutableVelVector other) {
        final double x = (this.x + other.x) / 2.0;
        final double y = (this.y + other.y) / 2.0;
        final double z = (this.z + other.z) / 2.0;
        return new SHImmutableVelVector(x, y, z);
    }
    
    public SHImmutableVelVector multiply(final double m) {
        final double x = this.x * m;
        final double y = this.y * m;
        final double z = this.z * m;
        return new SHImmutableVelVector(x, y, z);
    }
    
    public double dot(final SHImmutableVelVector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }
    
    public SHImmutableVelVector crossProduct(final SHImmutableVelVector other) {
        final double x = this.y * other.z - other.y * this.z;
        final double y = this.z * other.x - other.z * this.x;
        final double z = this.x * other.y - other.x * this.y;
        return new SHImmutableVelVector(x, y, z);
    }
    
    public SHImmutableVelVector normalize() {
        final double x = this.x / this.length();
        final double y = this.y / this.length();
        final double z = this.z / this.length();
        return new SHImmutableVelVector(x, y, z);
    }
    
    public SHImmutableVelVector clone() {
        try {
            return (SHImmutableVelVector)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof SHImmutableVelVector)) {
            return false;
        }
        final SHImmutableVelVector other = (SHImmutableVelVector)obj;
        return Math.abs(this.x - other.x) < 1.0E-6 && Math.abs(this.y - other.y) < 1.0E-6 && Math.abs(this.z - other.z) < 1.0E-6 && this.getClass().equals(obj.getClass());
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        return hash;
    }
    
    @Override
    public String toString() {
        return String.valueOf(SHImmutableVelVector.class.getName()) + "[" + this.x + "," + this.y + "," + this.z + "]";
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("x", this.x());
        result.put("y", this.y());
        result.put("z", this.z());
        return result;
    }
    
    public static double getEpsilon() {
        return 1.0E-6;
    }
    
    public static SHImmutableVelVector zero() {
        return SHImmutableVelVector.ZERO;
    }
    
    public static SHImmutableVelVector deserialize(final Map<String, Object> args) {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        if (args.containsKey("x")) {
            x = args.get("x");
        }
        if (args.containsKey("y")) {
            y = args.get("y");
        }
        if (args.containsKey("z")) {
            z = args.get("z");
        }
        return new SHImmutableVelVector(x, y, z);
    }
}
