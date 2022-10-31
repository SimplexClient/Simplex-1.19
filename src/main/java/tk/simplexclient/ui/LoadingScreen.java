package tk.simplexclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.utils.Util;

import java.awt.*;

public class LoadingScreen {
    @Setter
    @Getter
    private static LoadingScreen loadingScreen = new LoadingScreen();

    private Minecraft minecraft = Minecraft.getInstance();

    private String progText = "Simplex Client";

    private int lastAdd = 0;
    private int lastAdder = 0;

    public void render(PoseStack poseStack, int mouseX, int mouseY, float progressbar) {
        int width = this.minecraft.getWindow().getGuiScaledWidth();
        int height = this.minecraft.getWindow().getGuiScaledHeight();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation("simplex/textures/loadingbg.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GuiComponent.blit(poseStack,  -21 + mouseX / 90, mouseY * -1 / 90, 0, 0, width + 20, height + 20, (width + 21), (height + 20));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation("simplex/textures/logo.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GuiComponent.blit(poseStack,  width / 2 - 25, 50, 0, 0, 50, 50, 50, 50);

        int add = (int) Util.map(progressbar, 0, 1, 0, 100);
        int adder = (int) Util.map(progressbar, 0, 1, 0, width - 10);

        if(adder != lastAdd){
            lastAdd+=5;
        }

        if(add != lastAdder){
            lastAdder++;
        }

        SimplexClient.getInstance().getRenderer().start();
        {
            Color percentageColor;

            if(lastAdder >= 25 && lastAdder <= 70){
                percentageColor = new Color(255, 255, 0);
            }
            else if(lastAdder > 70) {
                percentageColor = new Color(0, 255, 0);
            }else{
                percentageColor = new Color(255,0,0);
            }


            SimplexClient.getInstance().getRenderer().drawRoundedRectangle(5, height - 25, lastAdd, 20, 5,  percentageColor);

            int sx = (int) (SimplexClient.getInstance().getRenderer().getStringWidth(progText)[0] / 2);

            SimplexClient.getInstance().getRenderer().drawString(progText, width / 2 - sx, 40, new Color(-1));
            int sx2 = (int) (SimplexClient.getInstance().getRenderer().getStringWidth(lastAdder + "%")[0] / 2);

            SimplexClient.getInstance().getRenderer().drawString(lastAdder + "%", width / 2 - sx2, height - 40, percentageColor);
        }
        SimplexClient.getInstance().getRenderer().end();
    }
}
