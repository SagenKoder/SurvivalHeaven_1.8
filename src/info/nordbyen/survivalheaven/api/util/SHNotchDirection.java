package info.nordbyen.survivalheaven.api.util;

public class SHNotchDirection
{
    private double pitch;
    private double yaw;
    
    public SHNotchDirection(final double pitch, final double yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    public double getPitch() {
        return this.pitch;
    }
    
    public double getYaw() {
        return this.yaw;
    }
}
