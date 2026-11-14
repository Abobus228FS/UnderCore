package under.verse.core;

import java.util.concurrent.ConcurrentHashMap;
import under.verse.core.block.Block;
import under.verse.core.World;

public class Location {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private static final World world = World.w;
    
    public Location(double x, double y, double z) {
        this(x, y, z, 0.0f, 0.0f);
    }
    public Location(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public void set(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public void add(float yaw, float pitch) {
        this.yaw += yaw;
        this.pitch += pitch;
    }
    public void add(double x, double y, double z, float yaw, float pitch) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.yaw += yaw;
        this.pitch += pitch;
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public Block getBlock() { return world.getBlockAt((int) x, (int) y, (int) z); }
    public World getWorld() { return world; }
    
    @Override
    public String toString() {
        return "Location{x=" + x + ",y=" + y + ",z=" + z + "}";
    }
}
