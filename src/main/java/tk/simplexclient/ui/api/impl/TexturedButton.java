package tk.simplexclient.ui.api.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.ui.api.UIComponent;

import java.awt.*;
import java.text.DecimalFormat;

public abstract class TexturedButton extends UIComponent {
    public final ResourceLocation icon;
    public final String text;
    public final float textSize;
    public int ticks = 0;

    public TexturedButton(int x, int y, int endX, int endY, ResourceLocation icon, String text, float textSize) {
        super(x, y, endX, endY);
        this.icon = icon;
        this.text = text;
        this.textSize = textSize*8f;
    }

    public abstract void onClick();

    @Override
    public void render(double mouseX, double mouseY) {
        renderer.end();
        if(basicCollisionCheck(mouseX, mouseY, x, y, endX, endY)) {
            blit(x - ticks, y - ticks, (endX - x) + (ticks * 2), (endY - y) + (ticks * 2), icon);

            this.renderer.start();
            this.renderer.drawString(text, x + ((((endX - x) + 15) / 2) - (this.renderer.getStringWidth(text)[0] / 2)), endY + 10, new Color(-1));
            this.renderer.end();

            if(ticks != 10)
                ticks++;
        }else {
            blit(x - ticks, y - ticks, endX - x + (ticks * 2), endY - y + (ticks * 2), icon);
            if(ticks != 0)
                ticks--;
        }
        renderer.start();
    }

    public void blit(int x, int y, int width, int height, ResourceLocation location){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);

        GlStateManager._enableBlend();

        GuiComponent.blit(new PoseStack() , x , y, 0, 0, width , height, width, height);

        GlStateManager._disableBlend();
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if(this.basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) && !value) this.onClick();
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
}
