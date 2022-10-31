package tk.simplexclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.ui.DraggableScreen;
import tk.simplexclient.ui.LoadingScreen;
import tk.simplexclient.ui.api.ScreenBase;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements AccessMinecraft {
    /**
     * Changes the title of the Minecraft Window
     * <p>
     * Don't call this method!
     *
     * @author CuteNyami
     * @reason I don't know
     */
    @Overwrite
    private String createTitle() {
        return "Simplex v2.1 | Minecraft 1.19.2";
    }

    /**
     * executes the stop method of the SimplexClient class
     * <p>
     * Don't call this method!
     *
     * @param ci         The Mixins CallbackInfo
     * @see SimplexClient#stop()
     */
    @Inject(method = "close", at = @At("TAIL"))
    public void stop(CallbackInfo ci) {
        new SimplexClient().stop();
    }

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(boolean bl, CallbackInfo ci) {
        if (SimplexClient.getDraggableHud().isDown()) {
            Minecraft.getInstance().setScreen(new ScreenBase(new DraggableScreen()));
        }
    }

    @Override
    @Accessor(value = "fps")
    public abstract int getFPS();
}
