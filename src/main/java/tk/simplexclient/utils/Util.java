package tk.simplexclient.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {
    public static boolean isDevEnv(){
        //return Boolean.getBoolean("fabric.development") || System.getenv("fabric.development").equalsIgnoreCase("true");
        return true;
    }

    public static void blit(int x, int y, int width, int height, ResourceLocation location){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int endX = x + width, endY = y + height;

        GuiComponent.blit(new PoseStack() , x , y, 0, 0, width , height, endX, endY );
    }

    private static List<Img> usedImages = new ArrayList<>();

    @Getter @AllArgsConstructor
    static class Img { BufferedImage image; int texture; }

    public static void blit(int x, int y, int width, int height, BufferedImage img){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        /*
        set t exture takes a while cus its not in assets lolololololol
         */
        AtomicInteger texture = new AtomicInteger(0);
        usedImages.forEach(img1 -> {if(img1.image.equals(img)) texture.set(img1.texture);});

        if(texture.get() == 0) {
            int[] pixels = new int[img.getWidth() * img.getHeight()];
            img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
            ByteBuffer buffer = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 4);

            for(int h = 0; h < img.getHeight(); h++) {
                for(int w = 0; w < img.getWidth(); w++) {
                    int pixel = pixels[h * img.getWidth() + w];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            buffer.flip();

            int id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glGenerateMipmap(GL_TEXTURE_2D);
            texture.set(id);
            usedImages.add(new Img(img, id));
        }
        if(texture.get() == 0)
            throw new RuntimeException("This shouldn't happen!");

        RenderSystem._setShaderTexture(0, texture.get());

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int endX = x + width, endY = y + height;

        GuiComponent.blit(new PoseStack() , x , y, 0, 0, width , height, img.getWidth(), img.getHeight() );
    }

    public static void glScissor(float x, float y, float endX, float endY, Runnable render, boolean isWidth) {
        var width = (isWidth ? endX : endX - x);
        var height = (isWidth ? endY : endY - y);

        glEnable(GL_SCISSOR_TEST);
        var res = Minecraft.getInstance().getWindow();
        x = (float) (x * res.getGuiScale());
        height = (float) (height * res.getGuiScale());
        y = (float) (res.getScreenHeight() - (y * res.getGuiScale()) - height);
        width = (float) (width * res.getGuiScale());
        GL11.glScissor((int) x, (int) y, (int) width, (int) height);
        render.run();
        glDisable(GL_SCISSOR_TEST);
    }

    /*
        found this in arduino source code
        the value x is between in_min and in_max
        and the value gets on out_min and out_max
        IDK how to tell you this
     */
    public static double map(double x, double in_min, double in_max, double out_min, double out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
