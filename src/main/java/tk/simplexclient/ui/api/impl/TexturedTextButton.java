package tk.simplexclient.ui.api.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.renderer.Renderer;
import tk.simplexclient.ui.api.UIComponent;

import java.awt.*;

public abstract class TexturedTextButton extends UIComponent {
    public final String text;
    public final ResourceLocation icon;
    public final int textureSize;
    public final boolean renderBackground;
    public final float textSize;
    public final boolean useMinus;

    public TexturedTextButton(int x, int y, String text, ResourceLocation icon, int textureSize, boolean renderBackground, float textSize, boolean useMinus) {
        super(ConstructorGetter.get(false, useMinus, x), ConstructorGetter.get(true, useMinus, y), ConstructorGetter.getEnd(false, text, textureSize, textSize, ConstructorGetter.get(false, useMinus, x)), ConstructorGetter.getEnd(true, text, textureSize, textSize, ConstructorGetter.get(true, useMinus, y)));
        this.text = text;
        this.icon = icon;
        this.textureSize = textureSize;
        this.renderBackground = renderBackground;
        this.textSize = textSize * 8f;
        this.useMinus = useMinus;
    }

    private static class ConstructorGetter {
        public static int getEnd(boolean xory, String t, int ts, float textSiz, int nm) {
            int abc = ts+nm;

            if (!xory) {
                abc += 5 + SimplexClient.getInstance().getRenderer().getStringWidth(t, textSiz * 8f, "roboto")[0];
            }

            return abc;
        }

        public static int get(boolean xory, boolean useMinus, int normalVal) {
            if(!xory && useMinus) {
                assert Minecraft.getInstance().screen != null;
                normalVal = (Minecraft.getInstance().screen.width / 2) - normalVal;
            }
            if(xory && useMinus) {
                assert Minecraft.getInstance().screen != null;
                normalVal = (Minecraft.getInstance().screen.height / 2) - normalVal;
            }

            return normalVal;
        }
    }

    public abstract void onClick(int button);

    @Override
    public void render(double mouseX, double mouseY) {
        if(this.renderBackground) {
           renderer.drawRoundedRectangle(x, y, endX - x, endY - y, 3f, new Color(0,0,0,120));
        }

        renderer.end();
        GL11.glEnable(GL11.GL_BLEND);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.icon);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GuiComponent.blit(new PoseStack(), x, y, 0, 0, textureSize, textureSize, textureSize, textureSize);
        GL11.glDisable(GL11.GL_BLEND);
        renderer.start();

        int heightDividedByTwo = (((endY - y)/2));
        renderer.drawStringScaled(text, x+textureSize+5, y + heightDividedByTwo - (renderer.getStringWidth(text, textSize)[1] / 2), textSize, new Color(-1), "roboto");
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if(this.basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) && !value) {
            this.onClick(0);
        }
    }

    @Override
    public void onRightMouse(boolean value, double mouseX, double mouseY) {
        if(this.basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) && !value) {
            this.onClick(1);
        }
    }

    @Override
    public void onMiddleMouse(boolean value, double mouseX, double mouseY) {
        if(this.basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) && !value) {
            this.onClick(2);
        }
    }

    @Override
    public void onScroll(Scroll sc, double mouseX, double mouseY) {

    }
}
