package tk.simplexclient.mixin;

import net.minecraft.client.sounds.SoundEngine;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundEngine.class)
public class MixinSoundEngine {
    @Redirect(method = "reload", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", remap = false))
    public void ignoreLoggerCall(Logger l, String s, Object o) { }
}
