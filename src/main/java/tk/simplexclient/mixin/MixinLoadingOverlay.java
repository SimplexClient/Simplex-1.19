package tk.simplexclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.ui.LoadingScreen;

import java.util.function.Consumer;

@Mixin(value = LoadingOverlay.class)
public class MixinLoadingOverlay {

    @Final
    @Shadow
    private ReloadInstance reload;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Minecraft minecraft, ReloadInstance reloadInstance, Consumer consumer, boolean bl, CallbackInfo ci){
        LoadingScreen.setLoadingScreen(new LoadingScreen());
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(PoseStack poseStack, int mouseX, int mouseY, float particalTicks, CallbackInfo ci) {
        LoadingScreen.getLoadingScreen().render(poseStack, mouseX, mouseY, reload.getActualProgress());
    }
}
