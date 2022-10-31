package tk.simplexclient.ui.api.impl;

import tk.simplexclient.ui.api.UIComponent;
import java.awt.*;

public abstract class ButtonComponent extends UIComponent {
    private final Color[] colors;
    private final String text;

    public ButtonComponent(int x, int y, int endX, int endY, Color[] colors, String text) {
        super(x, y, endX + x, endY + y);
        this.colors = colors;
        this.text = text;
    }

    public ButtonComponent(int x, int y, int endX, int endY, String text) {
        super(x, y, endX + x, endY + y);
        this.colors = null;
        this.text = text;
    }

    @Override
    public void render(double mouseX, double mouseY) {
        Color color = new Color(0, 0, 0, 132);
        Color fontColor = new Color(216, 216, 216);

        if (colors != null) {
            color = colors.length == 1 ? colors[0] : colors.length >= 2 ? (basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) ? colors[1] : colors[0]) : Color.WHITE;
            fontColor = colors.length == 3 ? colors[2] : colors.length == 4 ? (basicCollisionCheck(mouseX, mouseY, x, y, endX, endY) ? colors[3] : colors[2]) : Color.WHITE;
        }

        this.renderer.start();
        {
            this.renderer.drawRoundedRectangle(x, y, (float) endX - x, (float) endY - y, 5f, color);
            this.renderer.drawString(text, renderer.getIdealRenderingPosForText(text, x, y, endX, endY), fontColor);
        }
        this.renderer.end();
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if (!value && basicCollisionCheck(mouseX, mouseY, x, y, endX, endY)) {
            onClick();
        }
    }

    public abstract void onClick();

    @Override
    public void onRightMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onMiddleMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onScroll(Scroll sc, double mouseX, double mouseY) {

    }
}