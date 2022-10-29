package tk.simplexclient.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;

import java.awt.Color;

public class Renderable {
    @Getter
    public int x, y;
    @Getter
    public int width = 0, height = 0;
    public boolean renderBackground = false;
    public Runnable render = () -> {};
    public java.awt.Color backgroundColor;
    public Object owner;
    private static Renderer vr = SimplexClient.getInstance().getRenderer();

    public Renderable(int x, int y, Object owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public Renderable reset(){
        this.render = () -> {};
        return this;
    }

    public Renderable renderText(String text, int x, int y, java.awt.Color color) {
        return this.renderText(text, x, y, color, 1);
    }

    public Renderable renderText(String text, int[] pos, java.awt.Color color) {
        return this.renderText(text, pos[0], pos[1], color, 1);
    }

    public Renderable fillArea(int startX, int startY, int endX, int endY, java.awt.Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.drawRoundedRectangle(this.getX() + startX, this.getY() + startY, (endX - startX), (endY - startY), 3, color);
            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };


        return this;
    }

    public void render() {
        if(this.renderBackground) {
            vr.drawRoundedRectangle(x - 3, y - 3, width + 6, height + 6, 3 , this.backgroundColor);
        }

        this.width = 0;
        this.height = 0;

        this.render.run();
    }

    public void renderWithXY(int x, int y) {
        int xx = this.x;
        int yy = this.y;

        this.x = x;
        this.y = y;

        this.render();

        this.x = xx;
        this.y = yy;
    }

    public Renderable renderText(String text, int x, int y, Color color, float scale) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.drawStringScaled(text, this.getX() + x, this.getY() + y, scale, color);
            float[] size = vr.getStringWidth(text, scale);
            int endX = (int) (x + size[0]);
            int endY = (int) (y + size[1]);
            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };



        return this;
    }

    public int[] getIdealRenderingPosForText(String w, int x1, int y1, int endX, int endY) {
        int[] pos = SimplexClient.getInstance().getRenderer().getIdealRenderingPosForText(w, this.getX() + x1, this.getY() + y1, this.getX() + endX, this.getY() + endY);

        pos[0] = pos[0] - this.getX();
        pos[1] = pos[1] - this.getY();

        return pos;
    }

    public Renderable renderItemStack(int x, int y, @NotNull  ItemStack is, boolean renderText) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            int endX = x + 16;
            int endY = y + 16;

            if(is.getItem().canBeDepleted() && renderText) {
                double damage = ((is.getMaxDamage() - is.getDamageValue()) / (double) is.getMaxDamage()) * 100;
                double renderDamage = ((is.getMaxDamage() - is.getDamageValue()) / (double) is.getMaxDamage()) * 16;
                Color damageColor;

                if(damage >= 25 && damage <= 70){
                    damageColor = new Color(255, 255, 0);
                }
                else if(damage > 70) {
                    damageColor = new Color(0, 255, 0);
                }else{
                    damageColor = new Color(255,0,0);
                }

                vr.drawRoundedRectangle(this.getX() + x, this.getY() + endY, (float) (renderDamage), 2, 1, damageColor);
                endY += 6;
            }

            vr.end();

            GL11.glEnable(GL11.GL_BLEND);

            Minecraft.getInstance().getItemRenderer().renderGuiItem(is, this.getX() + x, this.getY() + y);

            GL11.glDisable(GL11.GL_BLEND);

            vr.start();

            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };

        return this;
    }

    public Renderable renderItemStack(int x, int y, @NotNull  ItemStack is, int count, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            int endX = x + 16;
            int endY = y + 16;

            if(is.isStackable()){ //Render Amount
                String text = count + "";
                vr.drawString(text, this.getX() + endX - 3, this.getY() + endY- 3, color);
                float[] width = vr.getStringWidth(text);

                endX = (int) ((endX - 3) + width[0]);
                endY = (int) ((endY - 3) + width[1]);
            }

            vr.end();

            GL11.glEnable(GL11.GL_BLEND);

            Minecraft.getInstance().getItemRenderer().renderGuiItem(is, this.getX() + x, this.getY() + y);

            GL11.glDisable(GL11.GL_BLEND);

            vr.start();

            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };

        return this;
    }
}
