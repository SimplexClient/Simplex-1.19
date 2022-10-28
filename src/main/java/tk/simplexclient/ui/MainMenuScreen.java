package tk.simplexclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.ui.api.impl.Button;

import java.awt.*;
import java.util.List;

public class MainMenuScreen extends ScreenBridge {

    @Override
    public void render(int mouseX, int mouseY, boolean leftClick, boolean rightClick, boolean middleClick) {
        /*
        render main menu
         */

        //Render Background
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation("simplex/textures/bg.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GuiComponent.blit(new PoseStack(),  -21 + mouseX / 90, mouseY * -1 / 90, 0, 0, this.width + 20, this.height + 20, (this.width + 21), (this.height + 20));
    }

    @Override
    public void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY) {

    }

    @Override
    public List<tk.simplexclient.ui.api.GuiComponent> renderComponents() {
        List<tk.simplexclient.ui.api.GuiComponent> comps = super.renderComponents();
        Minecraft minecraft = Minecraft.getInstance();
        comps.add(new Button(width / 2 - 30, height / 2 - 30, 60, 20, true, new Color(0,0,0,84), "Singleplayer") {
            @Override
            public void onClick() {
                minecraft.setScreen(new SelectWorldScreen(minecraft.screen));
            }
        });

        comps.add(new Button(width / 2 - 30, height / 2 - 5, 60, 20, true, new Color(0,0,0,84), "Multiplayer") {
            @Override
            public void onClick() {
                Screen screen = new JoinMultiplayerScreen(minecraft.screen);
                minecraft.setScreen((Screen)screen);
            }
        });

        comps.add(new Button(width / 2 - 30, height / 2 + 20, 60, 20, true, new Color(0,0,0,84), "Realms") {
            @Override
            public void onClick() {
                minecraft.setScreen(new RealmsMainScreen(minecraft.screen));
            }
        });

        comps.add(new Button(5, 5, 30, 10, true, new Color(0,0,0,0), "Options") {
            @Override
            public void onClick() {
                minecraft.setScreen(new OptionsScreen(minecraft.screen, minecraft.options));
            }
        });

        comps.add(new Button(width - 35, 5, 30, 10, true, new Color(0,0,0,0), "Quit") {
            @Override
            public void onClick() {
                minecraft.stop();
            }
        });

        return comps;
    }
}
