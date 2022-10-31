package tk.simplexclient.utils;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
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


    public static boolean isKeyPressed(int key){
        if(key == 0){ //Mouse Keys
            return Minecraft.getInstance().mouseHandler.isLeftPressed();
        } else if(key == 1){
            return Minecraft.getInstance().mouseHandler.isRightPressed();
        } else if(key == 2){
            return Minecraft.getInstance().mouseHandler.isMiddlePressed();
        } else{ //Keyboard
            return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
        }
    }

    public static double mouseX(){
        return Minecraft.getInstance().mouseHandler.xpos();
    }

    public static double mouseY(){
        return Minecraft.getInstance().mouseHandler.ypos();
    }
}
