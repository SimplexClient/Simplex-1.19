package tk.simplexclient.module.impl;

import lombok.Getter;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.module.hud.HudModule;
import tk.simplexclient.module.hud.ScreenPosition;

import java.awt.*;

public class TestModule extends HudModule {

    @Getter
    private float textWidth;

    @Getter
    private float textHeight;

    public TestModule() {
        super("Test");
    }

    @Override
    public void render(ScreenPosition position) {
        renderer.drawRoundedRectangle(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, new Color(0, 0, 0, 84));
        renderText(position);
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        renderer.drawRoundedRectangle(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, new Color(255, 255, 255, 70));
        renderer.drawRoundedOutline(position.getAbsoluteX() - 4, position.getAbsoluteY() - 3, getWidth(), getHeight(), 3f, 0, new Color(0, 0, 0, 160));
        renderText(position);
    }

    public void renderText(ScreenPosition position) {
        float[] size = renderer.getStringWidth("Test");
        textWidth = size[0];
        textHeight = size[1];
        renderer.drawString("Test", position.getAbsoluteX(), position.getAbsoluteY(), new Color(255, 255, 255));
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
