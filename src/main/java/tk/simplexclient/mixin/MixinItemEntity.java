package tk.simplexclient.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import tk.simplexclient.utils.ItemPhysicsUtil;

/*
    I said to nyami "name a random mod that I havent made"
    nyami said "item physics"
    https://canary.discord.com/channels/871722190649835540/961927554141392916/1036002936192499782
 */
@Mixin(value = ItemEntity.class)
public class MixinItemEntity implements ItemPhysicsUtil {

    private Vec3 rotation = new Vec3(0, 0, 0);

    @Override
    public Vec3 getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    @Override
    public void addRotation(Vec3 rotation) {
        this.rotation.add(rotation);
    }

    @Override
    public void addRotation(double x, double y, double z) {
        this.rotation.add(x, y, z);
    }
}
