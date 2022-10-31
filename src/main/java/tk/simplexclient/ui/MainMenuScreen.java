package tk.simplexclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.nanovg.NanoVG;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.ui.api.UIComponent;
import tk.simplexclient.ui.api.impl.ButtonComponent;

import java.awt.*;
import java.util.List;

public class MainMenuScreen extends ScreenBridge {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, boolean leftClick, boolean rightClick, boolean middleClick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation("simplex/textures/background.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GuiComponent.blit(poseStack, 0, 0, 0, 0, width, height, width, height);

        SimplexClient.getInstance().getRenderer().start();
        {
            float[] size = SimplexClient.getInstance().getRenderer().getStringWidth("Simplex", 15);
            SimplexClient.getInstance().getRenderer().drawStringScaled("Simplex", (float) this.width / 2 - (size[0] / 2), height / 2f - 65, 15, new Color(191, 191, 191), "inter");

            NanoVG.nvgEndFrame(SimplexClient.getInstance().getVg());
            NanoVG.nvgBeginFrame(SimplexClient.getInstance().getVg(), (float) Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), (float) Minecraft.getInstance().getWindow().getGuiScale());

            float w = 160;
            float h = 75;
            float x = (float) this.width / 2 - w / 2;
            float y = (float) this.height / 2 - h / 2;

            SimplexClient.getInstance().getRenderer().drawRoundedRectangle(x, y, w, h, 7f, new Color(42, 44, 43, 51));
            SimplexClient.getInstance().getRenderer().drawRectangle((float) width / 2 - 0.25f, (float) this.height / 2 - 25, 0.5f, 50, new Color(89, 89, 89));
        }
        SimplexClient.getInstance().getRenderer().end();
    }

    @Override
    public void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY) {

    }

    @Override
    public List<UIComponent> renderComponents() {
        List<UIComponent> components = super.renderComponents();

        /*
        components.add(new ButtonComponent(width / 2, height / 2, 60, 20,
                new Color[]{
                        new Color(202, 202, 202),
                        new Color(102, 102, 102),
                }, "Singleplayer") {
            @Override
            public void onClick() {
                Minecraft.getInstance().setScreen(new SelectWorldScreen(Minecraft.getInstance().screen));
            }
        });

        components.add(new ButtonComponent(width / 2, height / 2 + 20, 60, 20,
                new Color[]{
                        new Color(202, 202, 202),
                        new Color(102, 102, 102),
                }, "Settings") {
            @Override
            public void onClick() {
                Minecraft.getInstance().setScreen(new OptionsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options));
            }
        });
         */

        return components;
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
