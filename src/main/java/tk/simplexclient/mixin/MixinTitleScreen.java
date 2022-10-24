package tk.simplexclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {

    protected MixinTitleScreen() {
        super(Component.translatable("narrator.screen.title"));
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(PoseStack poseStack, int i, int j, float f1, CallbackInfo ci) {
        drawString(poseStack, this.font, "SimplexClient 1.19 Test", 2, this.height - 20, -1);
    }

}
