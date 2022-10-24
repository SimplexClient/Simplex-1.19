package tk.simplexclient.utils;

import lombok.Getter;
import net.minecraft.client.KeyMapping;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KeyUtils {

    @Getter
    private KeyMapping[] keyBindings;

    @Getter
    private final Map<String, Integer> categorySortOrder;

    public KeyUtils() {
        this.keyBindings = new KeyMapping[]{};
        this.categorySortOrder = new HashMap<>();
    }

    public void setDefaultCategorySortOrder() {
        categorySortOrder.put("key.categories.movement", 1);
        categorySortOrder.put("key.categories.gameplay", 2);
        categorySortOrder.put("SimplexClient", 3);
        categorySortOrder.put("key.categories.inventory", 4);
        categorySortOrder.put("key.categories.creative", 5);
        categorySortOrder.put("key.categories.multiplayer", 6);
        categorySortOrder.put("key.categories.ui", 7);
        categorySortOrder.put("key.categories.misc", 8);
    }

    public void registerKeyBind(KeyMapping key) {
        keyBindings = ArrayUtils.add(keyBindings, key);
        //Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, key);
    }

    public void unregisterKeyBind(KeyMapping key) {
        if (Arrays.asList(keyBindings).contains(key))
            keyBindings = ArrayUtils.remove(keyBindings, Arrays.asList(keyBindings).indexOf(key));
    }

}
