package tk.simplexclient.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.utils.KeyUtils;

import java.util.Map;

@Mixin(KeyMapping.class)
public class MixinKeyMapping {

    @Mutable
    @Shadow @Final private static Map<String, Integer> CATEGORY_SORT_ORDER;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        CATEGORY_SORT_ORDER.clear();
        SimplexClient.getKeyUtils().setDefaultCategorySortOrder();
        CATEGORY_SORT_ORDER = SimplexClient.getKeyUtils().getCategorySortOrder();
    }

}
