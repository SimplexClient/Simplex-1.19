package tk.simplexclient.utils;

import net.minecraft.world.phys.Vec3;

public interface ItemPhysicsUtil {
    Vec3 getRotation();
    void setRotation(Vec3 rotation);
    void addRotation(Vec3 rotation);
    void addRotation(double x, double y, double z);
}