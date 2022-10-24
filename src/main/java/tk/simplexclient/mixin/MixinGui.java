package tk.simplexclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.hud.IRenderer;
import tk.simplexclient.ui.DraggableScreen;

@Mixin(Gui.class)
public class MixinGui {

    /**
     * executes the start method of the SimplexClient class
     * <p>
     * Don't call this method!
     *
     * @param minecraft    The Minecraft Class
     * @param itemRenderer The ItemRenderer
     * @param ci           The Mixins CallbackInfo
     * @see SimplexClient#start()
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void start(Minecraft minecraft, ItemRenderer itemRenderer, CallbackInfo ci) {
        new SimplexClient().start();
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(PoseStack poseStack, float f, CallbackInfo ci) {
        for (IRenderer renderer : SimplexClient.getInstance().getModuleManager().getEnabledRenderers()) {
            if (Minecraft.getInstance().screen instanceof DraggableScreen) return;
            SimplexClient.getInstance().getModuleManager().callRenderer(renderer);
        }
    }

}
