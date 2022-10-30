package tk.simplexclient.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class Util {
    public static boolean isDevEnv(){
        return Boolean.getBoolean("fabric.development") || System.getenv("fabric.development").equalsIgnoreCase("true");
    }

    public static void blit(int x, int y, int width, int height, ResourceLocation location){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int endX = x + width, endY = y + height;

        GuiComponent.blit(new PoseStack() , x , y, 0, 0, width , height, endX, endY );
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
