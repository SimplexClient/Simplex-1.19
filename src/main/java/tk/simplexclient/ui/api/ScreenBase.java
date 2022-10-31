package tk.simplexclient.ui.api;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tk.simplexclient.SimplexClient;

public class ScreenBase extends Screen {
    boolean right = false;
    boolean left = false;
    boolean middle = false;
    public ScreenBridge bridge;
    public List<UIComponent> components = null;
    public int lastW = 0, lastH = 0;

    public ScreenBase(ScreenBridge bridge) {
        super(Component.nullToEmpty(""));
        this.bridge = bridge;
    }

    @Override
    protected void init() {
        this.bridge.init();
    }

    @Override
    public void render(PoseStack arg0, int arg1, int arg2, float arg3) {
        super.renderBackground(arg0);
        if (lastW != width || lastH != height) {
            this.lastH = height;
            this.lastW = width;
            this.bridge.width = super.width;
            this.bridge.height = super.height;
            this.components = this.bridge.renderComponents();
        }

        if (components == null) this.components = this.bridge.renderComponents();

        this.bridge.render(arg0, arg1, arg2, left, right, middle);

        SimplexClient.getInstance().getRenderer().start();
        this.components.forEach(t -> t.render(arg1, arg2));
        SimplexClient.getInstance().getRenderer().end();

        super.render(arg0, arg1, arg2, arg3);
    }

    @Override
    public boolean isPauseScreen() {
        return bridge.isPauseScreen();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return bridge.canCloseWithEscape();
    }

    @Override
    public boolean mouseClicked(double arg0, double arg1, int arg2) {
        this.bridge.mouseButtonClick(arg2 != 0, true, arg0, arg1);
        if (arg2 == 0) {
            left = true;
            this.components.forEach(t -> t.onLeftMouse(true, arg0, arg1));
        } else if (arg2 == 1) {
            right = true;
            this.components.forEach(t -> t.onRightMouse(true, arg0, arg1));
        } else if (arg2 == 2) {
            middle = true;
            this.components.forEach(t -> t.onMiddleMouse(true, arg0, arg1));
        }

        return super.mouseClicked(arg0, arg1, arg2);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        this.bridge.mouseButtonClick(p_94724_ != 0, false, p_94722_, p_94723_);
        if (p_94724_ == 0) {
            left = false;
            this.components.forEach(t -> t.onLeftMouse(false, p_94722_, p_94723_));
        } else if (p_94724_ == 1) {
            right = false;
            this.components.forEach(t -> t.onRightMouse(false, p_94722_, p_94723_));
        } else if (p_94724_ == 2) {
            middle = false;
            this.components.forEach(t -> t.onMiddleMouse(false, p_94722_, p_94723_));
        }

        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        this.components.forEach(t -> t.onScroll(p_94688_ == -1 ? UIComponent.Scroll.DOWN : UIComponent.Scroll.UP, p_94686_, p_94687_));

        return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
    }

    @Override
    public void onClose() {
        this.bridge.onClose();
        super.onClose();
    }
}
