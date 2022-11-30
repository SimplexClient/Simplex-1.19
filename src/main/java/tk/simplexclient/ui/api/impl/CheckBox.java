package tk.simplexclient.ui.api.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import tk.simplexclient.ui.api.UIComponent;
import tk.simplexclient.utils.Util;

import java.awt.*;

public abstract class CheckBox extends UIComponent {
    public String text;
    public boolean wasOpened = false;
    public ResourceLocation toggled, nonToggled;

    public CheckBox(int x, int y, int endX, int endY, String text) {
        super(x, y, endX, endY);
        this.text = text;
        this.toggled = new ResourceLocation("simplex/textures/icons/toggledcheckbox.png");
        this.nonToggled = new ResourceLocation("simplex/textures/icons/nottoggledcheckbox.png");
    }

    @Override
    public void render(double mouseX, double mouseY) {
        //this.renderer.drawRectangle(
        //        x,
        //        y,
        //        (float) endX - x,
        //        (float) endY - y,
        //        new Color(36, 35, 35,120));
        this.renderer.drawString(text, endX, y + (((endY - y) / 2) - (this.renderer.getStringWidth(text)[1] / 2)), new Color(-1));
        this.blit(x,
                y,
                endX - x,
                endY - y,
                wasOpened ? toggled : nonToggled);
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if(!value && basicCollisionCheck(mouseX, mouseY, x, y, endX, endY)){
            onClicked(!wasOpened);
            wasOpened = !wasOpened;
        }
    }

    @Override
    public void onRightMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onMiddleMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onScroll(Scroll sc, double mouseX, double mouseY) {

    }

    public abstract void onClicked(boolean opened);

    public void blit(int x, int y, int width, int height, ResourceLocation location){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);

        GlStateManager._enableBlend();

        GuiComponent.blit(new PoseStack() , x , y, 0, 0, width , height, width, height);

        GlStateManager._disableBlend();
    }
}
