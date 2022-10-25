package tk.simplexclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.SimplexClient;

import java.util.Objects;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Shadow private boolean panoramicMode;

    @Shadow public abstract void resetProjectionMatrix(Matrix4f matrix4f);

    @Shadow public abstract Matrix4f getProjectionMatrix(double d);

    @Shadow protected abstract double getFov(Camera camera, float f, boolean bl);

    @Shadow protected abstract void bobHurt(PoseStack poseStack, float f);

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void bobView(PoseStack poseStack, float f);

    @Shadow public abstract LightTexture lightTexture();

    @Shadow @Final public ItemInHandRenderer itemInHandRenderer;

    @Shadow @Final private LightTexture lightTexture;

    @Shadow @Final private RenderBuffers renderBuffers;

    /**
     * ok
     */
    @Overwrite
    private void renderItemInHand(PoseStack poseStack, Camera camera, float f) {
        if (!this.panoramicMode) {
            this.resetProjectionMatrix(this.getProjectionMatrix(this.getFov(camera, f, false)));
            PoseStack.Pose pose = poseStack.last();
            pose.pose().setIdentity();
            pose.normal().setIdentity();
            poseStack.pushPose();
            this.bobHurt(poseStack, f);
            if (this.minecraft.options.bobView().get()) {
                this.bobView(poseStack, f);
            }

            boolean bl = this.minecraft.getCameraEntity() instanceof LivingEntity && ((LivingEntity)this.minecraft.getCameraEntity()).isSleeping();
            if ((this.minecraft.options.getCameraType().isFirstPerson() && !bl && !this.minecraft.options.hideGui && Objects.requireNonNull(this.minecraft.gameMode).getPlayerMode() != GameType.SPECTATOR) || SimplexClient.getInstance().isInHud()) {
                this.lightTexture().turnOnLightLayer();
                this.itemInHandRenderer.renderHandsWithItems(f, poseStack, this.renderBuffers.bufferSource(), this.minecraft.player, this.minecraft.getEntityRenderDispatcher().getPackedLightCoords(this.minecraft.player, f));
                this.lightTexture.turnOffLightLayer();
            }

            poseStack.popPose();
            if (this.minecraft.options.getCameraType().isFirstPerson() && !bl) {
                ScreenEffectRenderer.renderScreenEffect(this.minecraft, poseStack);
                this.bobHurt(poseStack, f);
            }

            if (this.minecraft.options.bobView().get()) {
                this.bobView(poseStack, f);
            }

        }
    }

}
