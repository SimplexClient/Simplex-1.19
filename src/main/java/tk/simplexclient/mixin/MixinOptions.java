package tk.simplexclient.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;

import java.io.File;

@Mixin(Options.class)
public class MixinOptions {

    @Mutable
    @Shadow
    @Final
    public KeyMapping[] keyMappings;

    @Shadow
    @Final
    public KeyMapping[] keyHotbarSlots;

    @Shadow
    @Final
    public KeyMapping keyAttack;

    @Shadow
    @Final
    public KeyMapping keyUse;

    @Shadow
    @Final
    public KeyMapping keyUp;

    @Shadow
    @Final
    public KeyMapping keyLeft;

    @Shadow
    @Final
    public KeyMapping keyDown;

    @Shadow
    @Final
    public KeyMapping keyRight;

    @Shadow
    @Final
    public KeyMapping keyJump;

    @Shadow
    @Final
    public KeyMapping keyShift;

    @Shadow
    @Final
    public KeyMapping keySprint;

    @Shadow
    @Final
    public KeyMapping keyDrop;

    @Shadow
    @Final
    public KeyMapping keyInventory;

    @Shadow
    @Final
    public KeyMapping keyChat;

    @Shadow
    @Final
    public KeyMapping keyPlayerList;

    @Shadow
    @Final
    public KeyMapping keyPickItem;

    @Shadow
    @Final
    public KeyMapping keyCommand;

    @Shadow @Final public KeyMapping keySocialInteractions;

    @Shadow @Final public KeyMapping keyScreenshot;

    @Shadow @Final public KeyMapping keyTogglePerspective;

    @Shadow @Final public KeyMapping keySmoothCamera;

    @Shadow @Final public KeyMapping keyFullscreen;

    @Shadow @Final public KeyMapping keySpectatorOutlines;

    @Shadow @Final public KeyMapping keySwapOffhand;

    @Shadow @Final public KeyMapping keySaveHotbarActivator;

    @Shadow @Final public KeyMapping keyLoadHotbarActivator;

    @Shadow @Final public KeyMapping keyAdvancements;

    @Inject(method = "load", at = @At("HEAD"))
    public void registerKeyMappings(CallbackInfo ci) {
        this.keyMappings = ArrayUtils.addAll(keyMappings, SimplexClient.getInstance().getKeyUtils().getKeyBindings());

    }
}
