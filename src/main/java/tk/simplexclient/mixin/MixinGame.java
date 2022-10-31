package tk.simplexclient.mixin;

import net.minecraft.client.Game;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;

@Mixin(Game.class)
public class MixinGame {

    /**
     * executes the init method of the SimplexClient class
     * <p>
     * Don't call this method!
     *
     * @param minecraft The Minecraft class
     * @param ci         The Mixins CallbackInfo
     * @see SimplexClient#init()
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(Minecraft minecraft, CallbackInfo ci) {
        new SimplexClient().init();
    }

}
