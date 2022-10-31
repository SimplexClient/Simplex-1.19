package tk.simplexclient.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;
import tk.simplexclient.renderer.Renderer;
import tk.simplexclient.ui.api.UIComponent;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.ui.api.impl.CheckBox;
import tk.simplexclient.utils.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DraggableScreen extends ScreenBridge {
    public int moseX, moseY;
    public Renderable movingMod;
    boolean lastTickHolding = false;
    public List<HUDModule> mods;
    public boolean showSnapLines = false;
    private boolean showDividerLines = false;

    public DraggableScreen() {
        this.mods = new ArrayList<>();
        SimplexClient.getInstance().getModuleManager().modules.forEach(t -> {
            if (t instanceof HUDModule)
                mods.add((HUDModule) t);
        });

    }
    
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, boolean leftClick, boolean rightClick, boolean middleClick) {
        SimplexClient.getInstance().getRenderer().start();
        for (HUDModule hud : mods) {
            hud.render();
        }

        if (leftClick && lastTickHolding) {
            int finalX = mouseX - moseX;
            int finalY = mouseY - moseY;

            for (HUDModule hud : mods) {
                Renderable rend = hud.getRenderable();
                    if (rend.equals(movingMod) || !hud.isEnabled())
                        continue;

                    if (mouseX >= (rend.x - 5) & mouseX <= (rend.x + rend.getWidth() + 5)) {
                        if(showSnapLines){
                            boolean shouldRenderplusHeight = (rend.y < movingMod.y);
                            int width = ((rend.x + rend.width - (rend.width / 2)) + 1) - ((rend.x + rend.width - (rend.width / 2)) - 1);
                            int height = (movingMod.y + (shouldRenderplusHeight ? 0 : rend.height)) - ((rend.y) + (shouldRenderplusHeight ? rend.height : 0));

                            SimplexClient.getInstance().getRenderer().drawRoundedRectangle((rend.x + rend.width - (rend.width / 2)) - 1,
                                    (rend.y) + (shouldRenderplusHeight ? rend.height : 0),
                                    width,
                                    height, 1, new Color(255, 255, 255));
                        }
                        finalX = rend.x;
                    }
                    if (mouseY >= (rend.y - 5) & mouseY <= (rend.y + rend.getHeight() + 5)) {
                        if(showSnapLines){
                            boolean shouldRenderplusWidth = (rend.x < movingMod.x);
                            int width = (movingMod.x + (shouldRenderplusWidth ? 0 : movingMod.width)) - (rend.x + (shouldRenderplusWidth ? rend.width : 0));
                            int height = (rend.y + rend.height - (rend.height / 2) + 1) - (rend.y + rend.height - (rend.height / 2) - 1);

                            SimplexClient.getInstance().getRenderer().drawRoundedRectangle(rend.x + (shouldRenderplusWidth ? rend.width : 0),
                                    rend.y + rend.height - (rend.height / 2) - 1,
                                    width,
                                    height, 1, new Color(255, 255, 255));
                        }
                        finalY = rend.y;
                    }
                }

            HUDModule module = (HUDModule) movingMod.owner;
            module.setPosition(new int[] {finalX, finalY});
        }
        if (!leftClick && lastTickHolding) {
            lastTickHolding = false;
            movingMod = null;
        }

        if(Util.isDevEnv()){
            renderDebugMenu();
        }

        SimplexClient.getInstance().getRenderer().end();
    }

    @Override
    public void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY) {
        if (!button && value && !lastTickHolding) {
            if(this.getClickedModule(mouseX, mouseY) != null) {
                Renderable rend = this.getClickedModule(mouseX, mouseY);
                this.movingMod = rend;
                this.moseX = (int) (mouseX - rend.x);
                this.moseY = (int) (mouseY - rend.y);
                this.lastTickHolding = true;
            }
        }
    }

    public Renderable getClickedModule(double mouseX, double mouseY) {
        if (mouseX == 0.0 && mouseY == 0.0)
            return null;

        for (HUDModule mod : this.mods) {
            Renderable m = mod.getRenderable();
            if (mouseX >= m.x & mouseX <= (m.x + m.getWidth()) & mouseY >= m.y & mouseY <= (m.y + m.getHeight())
                    & mod.isEnabled()) {
                return m;
            }
        }

        return null;
    }

    @Override
    public List<UIComponent> renderComponents() {
        List<UIComponent> comps = super.renderComponents();

        if(Util.isDevEnv()){
            comps.add(new CheckBox(width - 97, 25, width - 77, 40, "Render Snapping Lines") {
                @Override
                public void onClicked(boolean opened) {
                    DraggableScreen.this.showSnapLines = opened;
                }
            });

            comps.add(new CheckBox(width - 97, 45, width - 77, 60, "Render Divider Lines") {
                @Override
                public void onClicked(boolean opened) {
                    DraggableScreen.this.showDividerLines = opened;
                }
            });
        }

        return comps;
    }

    public void renderDebugMenu() {
        if(this.showDividerLines){
            SimplexClient.getInstance().getRenderer().drawRectangle(0, (float) height / 2 - 0.5f, width, 1, Color.WHITE);
            SimplexClient.getInstance().getRenderer().drawRectangle((float) width / 2 - 0.5f, 0, 1, height, Color.WHITE);
        }
        Renderer renderer = SimplexClient.getInstance().getRenderer();

        //Render
        renderer.drawRoundedRectangle(width - 100, 2, 98, 100, 3, new Color(255,255,255,70));
        renderer.drawString("Debug Menu", width - 50 - (renderer.getStringWidth("Debug Menu")[0] / 2), 10, new Color(-1));
    }
}
