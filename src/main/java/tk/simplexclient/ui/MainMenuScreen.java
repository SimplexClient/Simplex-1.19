package tk.simplexclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.renderer.Renderer;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.ui.api.UIComponent;
import tk.simplexclient.ui.api.impl.TexturedButton;
import tk.simplexclient.ui.api.impl.TexturedTextButton;
import tk.simplexclient.utils.Util;

import java.awt.*;
import java.util.List;

public class MainMenuScreen extends ScreenBridge {

    public boolean shouldRenderTextComps = false;
    public boolean isGrowing = false;
    public int growingWidth = 0;

    @Override
    public void init() {
        this.cock = new TexturedTextButton(0,0,"ABC", null,0,false,0,false) { @Override public void onClick(int button) { } };
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

            float w = 160;
            float h = 75;
            float x = (float) this.width / 2 - w / 2;
            float y = (float) this.height / 2 - h / 2;
            if(this.shouldRenderTextComps) {
                if(this.isGrowing) {
                    this.growingWidth+=4;
                    this.reloadComponents();

                    if(this.growingWidth >= 160) {
                        this.isGrowing = false;
                        this.reloadComponents();
                    }

                    SimplexClient.getInstance().getRenderer().drawRoundedRectangle(x + (w / 2) - (growingWidth /2), y, growingWidth, h, 7f, new Color(42, 44, 43, 51));
                } else {
                    SimplexClient.getInstance().getRenderer().drawRoundedRectangle(x, y, w, h, 7f, new Color(42, 44, 43, 51));
                }
            } else {
                if(this.isGrowing) {
                    this.growingWidth-=4;
                    this.reloadComponents();

                    if(this.growingWidth == 20) {
                        this.isGrowing = false;
                    }
                    SimplexClient.getInstance().getRenderer().drawRoundedRectangle(x + (w / 2) - (growingWidth /2), y, growingWidth, h, 7f, new Color(42, 44, 43, 51));
                } else {
                    SimplexClient.getInstance().getRenderer().drawRoundedRectangle(x + (w / 2 - 10), y, 20, h, 7f, new Color(42, 44, 43, 51));
                }
            }
            SimplexClient.getInstance().getRenderer().drawRectangle((float) width / 2 - 0.25f, (float) this.height / 2 - 25, 0.5f, 50, new Color(89, 89, 89));

            Renderer renderer = SimplexClient.getInstance().getRenderer();

            float[] abc = renderer.getStringWidth("Minecraft Realms");
            renderer.drawString("Minecraft Realms", 5, 5, new Color(-1));
            //renderer.drawOutline(0, 0, abc[0] + 10, abc[1] + 10, new Color(-1));

            float[] abc1 = renderer.getStringWidth("Quit");
            renderer.drawString("Quit", width - abc1[0] - 5, 5, new Color(-1));
            //renderer.drawOutline(width - abc1[0] - 10, 0, abc1[0] + 10, abc1[1] + 10, new Color(-1));
        }
        SimplexClient.getInstance().getRenderer().end();
    }

    public UIComponent cock = null;

    @Override
    public void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY) {
        float w = 160;
        float h = 75;
        float x = (float) this.width / 2 - w / 2;
        float y = (float) this.height / 2 - h / 2;

        if(this.cock.basicCollisionCheck(mouseX, mouseY, (int) (x + (w / 2 - 10)), (int) y, (int) (x + (w / 2 - 10) + 20), (int) (y + h)) && !value) {
            this.shouldRenderTextComps = !this.shouldRenderTextComps;
            this.isGrowing = true;
            this.reloadComponents();
        }

        Renderer renderer = SimplexClient.getInstance().getRenderer();

        float[] abc = renderer.getStringWidth("Minecraft Realms");
        if(this.cock.basicCollisionCheck(mouseX, mouseY, 0, 0, (int) (abc[0] + 10), (int) (abc[1] + 10))) {
            Minecraft.getInstance().setScreen(new RealmsMainScreen(Minecraft.getInstance().screen));
        }

        float[] abc1 = renderer.getStringWidth("Quit");
        if(this.cock.basicCollisionCheck(mouseX, mouseY, (int) (width - abc1[0] - 10), 0, (int) (abc1[0] + 10 + width - abc1[0] - 10), (int) (abc1[1] + 10))) {
            Minecraft.getInstance().stop();
        }
    }

    @Override
    public List<UIComponent> renderComponents() {
        List<UIComponent> components = super.renderComponents();

        if(this.shouldRenderTextComps && this.growingWidth > 115) {
            components.add(new TexturedTextButton(60,20,"Home", new ResourceLocation("simplex/textures/icons/home.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        int ignored = 0; // lol
                    }
                }
            });

            components.add(new TexturedTextButton(60,0,"Settings", new ResourceLocation("simplex/textures/icons/settings.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        Minecraft.getInstance().setScreen(new OptionsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options));
                    }
                }
            });

            components.add(new TexturedTextButton(60,-20,"Modules", new ResourceLocation("simplex/textures/icons/modules.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        int ignored = 0; // lol
                    }
                }
            });



            components.add(new TexturedTextButton(-20,20,"Friends", new ResourceLocation("simplex/textures/icons/friends.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        int ignored = 0; // lol
                    }
                }
            });

            components.add(new TexturedTextButton(-20,0,"Messages", new ResourceLocation("simplex/textures/icons/messages.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        int ignored = 0; // lol
                    }
                }
            });

            components.add(new TexturedTextButton(-20,-20,"Account", new ResourceLocation("simplex/textures/icons/account.png"), 8, false, 0.75f, true) {
                @Override
                public void onClick(int button) {
                    if(button == 0) {
                        int ignored = 0; // lol
                    }
                }
            });
        }

        float w = 160;
        float x = (float) this.width / 2 - w / 2;
        int leftSide = (int) (x + (w / 2) - (growingWidth /2));
        int rightSide = leftSide + growingWidth;

        components.add(new TexturedButton(leftSide - 36, height / 2 - 16, leftSide - 4, height / 2 + 16, new ResourceLocation("simplex/textures/icons/singleplayer.png"), "Singleplayer", 1) {
            @Override
            public void onClick() {
                Minecraft.getInstance().setScreen(new SelectWorldScreen(Minecraft.getInstance().screen));
            }
        });

        components.add(new TexturedButton(rightSide + 4, height / 2 - 16, rightSide + 36, height / 2 + 16, new ResourceLocation("simplex/textures/icons/multiplayer.png"), "Multiplayer", 1) {
            @Override
            public void onClick() {
                Minecraft.getInstance().setScreen(new JoinMultiplayerScreen(Minecraft.getInstance().screen));
            }
        });

        return components;
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
