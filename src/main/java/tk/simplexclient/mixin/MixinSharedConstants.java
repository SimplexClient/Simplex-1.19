package tk.simplexclient.mixin;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/*
    @author betterclient
    @reason faster bootup (https://github.com/CorgiTaco/lazydfu)
 */
@Mixin(value = SharedConstants.class)
public class MixinSharedConstants {
    /**
     * @author Andrew Steinborn
     * @reason Disables any possibility of enabling DFU "optimizations"
     */
    @Overwrite
    public static void enableDataFixerOptimizations() {

    }
}
