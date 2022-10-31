package tk.simplexclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.utils.ItemPhysicsUtil;

import java.util.Random;

/*
    I said to nyami "name a random mod that I havent made"
    nyami said "item physics"
    https://canary.discord.com/channels/871722190649835540/961927554141392916/1036002936192499782
 */
@Mixin(value = ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer extends EntityRenderer<ItemEntity> {
    @Shadow @Final private RandomSource random;
    @Shadow
    @Final
    private ItemRenderer itemRenderer;
    @Shadow protected abstract int getRenderAmount(ItemStack stack);

    private MixinItemEntityRenderer(EntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void onConstructor(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.shadowRadius = 0;
    }

    @Inject(at = @At("HEAD"), method = "render*", cancellable = true)
    private void render(ItemEntity dropped, float f, float partialTicks, PoseStack matrix, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo callback) {
        if(SimplexClient.getInstance().getModuleManager().getModule("Item Physics").isEnabled()){
            ItemStack itemStack = dropped.getItem();

            // setup seed for random rotation
            int seed = itemStack.isEmpty() ? 187 : Item.getId(itemStack.getItem()) + itemStack.getDamageValue();
            this.random.setSeed(seed);

            matrix.pushPose();
            BakedModel bakedModel = this.itemRenderer.getModel(itemStack, dropped.level, null, seed);
            boolean hasDepthInGui = bakedModel.isGui3d();

            // decide how many item layers to render
            int renderCount = this.getRenderAmount(itemStack);

            // Helper for manipulating data on the current ItemEntity
            ItemPhysicsUtil rotator = (ItemPhysicsUtil) dropped;

            // Certain BlockItems (Grass Block, Jukebox, Dirt, Ladders) are fine being rotated 180 degrees like standard items.
            // Other BlockItems (Carpet, Slab) do not like being rotated and should stay flat.
            // To determine whether a block should be flat or rotated, we check the collision box height.
            // Anything that takes up more than half a block vertically is rotated.
            boolean renderBlockFlat = false;
            if(dropped.getItem().getItem() instanceof BlockItem && !(dropped.getItem().getItem() instanceof ItemNameBlockItem)) {
                Block b = ((BlockItem) dropped.getItem().getItem()).getBlock();
                VoxelShape shape = b.getShape(b.defaultBlockState(), dropped.level, dropped.getOnPos(), CollisionContext.empty());

                // Only blocks with a collision box of <.5 should be rendered flat
                if(shape.max(Direction.Axis.Y) <= .5) {
                    renderBlockFlat = true;
                }
            }

            // Make full blocks flush with ground
            Item item = dropped.getItem().getItem();
            if(item instanceof BlockItem && !(item instanceof ItemNameBlockItem) && !renderBlockFlat) {
                // make blocks flush with the ground
                matrix.translate(0, -0.06, 0);
            }

            // Give all non-flat items a 90* spin
            if(!renderBlockFlat) {
                matrix.translate(0, .185, .0);
                matrix.mulPose(Vector3f.XP.rotation(1.571F));
                matrix.translate(0, -.185, -.0);
            }

            // Item is flying through air
            boolean isAboveWater = dropped.level.getBlockState(dropped.getOnPos()).getFluidState().getType().is(FluidTags.WATER);
            if(!dropped.isOnGround() && (!dropped.isInWater() && !isAboveWater)) {
                float rotation = ((float) dropped.getAge() + partialTicks) / 20.0F + dropped.getBbHeight(); // calculate rotation based on age and ticks

                // 90* items/blocks (non-flat) get spin on Z axis, flat items/blocks get spin on Y axis
                if(!renderBlockFlat) {
                    // rotate renderer
                    matrix.translate(0, .185, .0);
                    matrix.mulPose(Vector3f.ZP.rotation(rotation));
                    matrix.translate(0, -.185, .0);

                    // save rotation in entity
                    rotator.setRotation(new Vec3(0, 0, rotation));
                } else {
                    // rotate renderer
                    matrix.mulPose(Vector3f.YP.rotation(rotation));

                    // save rotation in entity
                    rotator.setRotation(new Vec3(0, rotation, 0));

                    // Translate down to become flush with floor
                    matrix.translate(0, -.065, 0);
                }

                // Carrots/Potatoes/Redstone/other crops in air need vertical offset
                if(dropped.getItem().getItem() instanceof ItemNameBlockItem) {
                    matrix.translate(0, 0, .195);
                }

                else if (!(dropped.getItem().getItem() instanceof BlockItem)) {
                    // Translate down to become flush with floor
                    matrix.translate(0, 0, .195);
                }
            }

            // Carrots/Potatoes/Redstone/other crops on ground
            else if(dropped.getItem().getItem() instanceof ItemNameBlockItem){
                matrix.translate(0, .185, .0);
                matrix.mulPose(Vector3f.ZP.rotation((float) rotator.getRotation().z));
                matrix.translate(0, -.185, .0);

                // Translate down to become flush with floor
                matrix.translate(0, 0, .195);
            }

            // Ladders/Slabs/Carpet and other short blocks on ground
            else if(renderBlockFlat) {
                matrix.mulPose(Vector3f.YP.rotation((float) rotator.getRotation().y));

                // Translate down to become flush with floor
                matrix.translate(0, -.065, 0);
            }


            // Normal blocks/items on ground
            else {
                // Translate normal items down to become flush with floor
                if (!(dropped.getItem().getItem() instanceof BlockItem)) {
                    matrix.translate(0, 0, .195);
                }

                matrix.translate(0, .185, .0);
                matrix.mulPose(Vector3f.ZP.rotation((float) rotator.getRotation().z));
                matrix.translate(0, -.185, .0);
            }

            // special-case soul sand
            if(dropped.level.getBlockState(dropped.blockPosition()).getBlock().equals(Blocks.SOUL_SAND)) {
                matrix.translate(0, 0, -.1);
            }

            // special-case skulls
            if(dropped.getItem().getItem() instanceof BlockItem) {
                if(((BlockItem) dropped.getItem().getItem()).getBlock() instanceof SkullBlock) {
                    matrix.translate(0, .11, 0);
                }
            }

            float scaleX = bakedModel.getTransforms().ground.scale.x();
            float scaleY = bakedModel.getTransforms().ground.scale.y();
            float scaleZ = bakedModel.getTransforms().ground.scale.z();

            float x;
            float y;
            if (!hasDepthInGui) {
                float r = -0.0F * (float)(renderCount) * 0.5F * scaleX;
                x = -0.0F * (float)(renderCount) * 0.5F * scaleY;
                y = -0.09375F * (float)(renderCount) * 0.5F * scaleZ;
                matrix.translate(r, x, y);
            }

            // render each item in the stack on the ground (higher stack count == more items displayed)
            for(int u = 0; u < renderCount; ++u) {
                matrix.pushPose();

                // random positioning for rendered items, is especially seen in 64 block stacks on the ground
                if (u > 0) {
                    if (hasDepthInGui) {
                        x = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        y = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float z = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        matrix.translate(x, y, z);
                    } else {
                        x = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        y = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        matrix.translate(x, y, 0.0D);
                        matrix.mulPose(Vector3f.ZP.rotation(this.random.nextFloat()));
                    }
                }

                // render item
                this.itemRenderer.render(itemStack, ItemTransforms.TransformType.GROUND, false, matrix, vertexConsumerProvider, i, OverlayTexture.NO_OVERLAY, bakedModel);

                // end
                matrix.popPose();

                // translate based on scale, which gies vertical layering to high stack count items
                if (!hasDepthInGui) {
                    matrix.translate(0.0F * scaleX, 0.0F * scaleY, 0.0625F * scaleZ);
                }
            }

            // end
            matrix.popPose();
            super.render(dropped, f, partialTicks, matrix, vertexConsumerProvider, i);
            callback.cancel();
        }
    }
}
