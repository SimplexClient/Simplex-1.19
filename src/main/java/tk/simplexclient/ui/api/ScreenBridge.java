package tk.simplexclient.ui.api;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ScreenBridge {
    public int width, height;

    public List<UIComponent> components;

    public void init() {}

    public abstract void render(PoseStack poseStack, int mouseX, int mouseY, boolean leftClick, boolean rightClick, boolean middleClick);

    public abstract void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY);

    public boolean isPauseScreen() {
        return true;
    }

    public List<UIComponent> renderComponents() {
        return new ArrayList<>();
    }


    public boolean canCloseWithEscape() {
        return true;
    }

    public void onClose() {}
}
