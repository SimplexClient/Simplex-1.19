package tk.simplexclient.module.impl;

import lombok.Getter;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.module.hud.HudModule;
import tk.simplexclient.module.hud.ScreenPosition;

import java.awt.*;

public class FPSModule extends HudModule {

    @Getter
    private float textWidth;

    @Getter
    private float textHeight;

    public FPSModule() {
        super("FPS");
    }

    @Override
    public void render(ScreenPosition position) {
        renderer.drawRoundedRectangle(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, new Color(0, 0, 0, 84));
        //renderer.drawRoundedRectangle(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 10*25, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 11*2, getWidth(), getHeight(), 3f, new Color(0, 0, 0, 84));
        renderFPS(position);
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        renderer.drawRoundedRectangle(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, new Color(255, 255, 255, 70));
        renderer.drawRoundedOutline(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, 0, new Color(0, 0, 0, 160));
        renderFPS(position);
    }

    public void renderFPS(ScreenPosition position) {
        float[] size = renderer.getStringWidth(AccessMinecraft.getMinecraft().getFPS() + " FPS");
        textWidth = size[0];
        textHeight = size[1];
        renderer.drawString(AccessMinecraft.getMinecraft().getFPS() + " FPS", position.getAbsoluteX(), position.getAbsoluteY(), new Color(255, 255, 255));
    }

    @Override
    public float getWidth() {
        return textWidth + 8;
    }

    @Override
    public float getHeight() {
        return textHeight + 6;
    }

    @Override
    public void save(ScreenPosition position) {
        this.position = position;
        client.getModuleConfig().saveModuleConfig(this, position);
    }

    @Override
    public ScreenPosition load() {
        return client.getModuleConfig().getScreenPosition(this);
    }
}
