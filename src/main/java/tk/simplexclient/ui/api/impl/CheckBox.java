package tk.simplexclient.ui.api.impl;

import tk.simplexclient.ui.api.UIComponent;

import java.awt.*;

public abstract class CheckBox extends UIComponent {
    public String text;
    public boolean wasOpened = false;

    public CheckBox(int x, int y, int endX, int endY, String text) {
        super(x, y, endX, endY);
        this.text = text;
    }

    @Override
    public void render(double mouseX, double mouseY) {
        this.renderer.drawRectangle(
                x,
                y,
                (float) endX - x,
                (float) endY - y,
                new Color(36, 35, 35,120));
        this.renderer.drawString(text, endX, y + (((endY - y) / 2) - (this.renderer.getStringWidth(text)[1] / 2)), new Color(-1));
        this.renderer.drawString(wasOpened ? "+" : "X", x, y + (((endY - y) / 2) - (this.renderer.getStringWidth(wasOpened ? "+" : "X")[1] / 2)), new Color(-1));
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if(!value && basicCollisionCheck(mouseX, mouseY, x, y, endX, endY)){
            onClicked(!wasOpened);
            wasOpened = !wasOpened;
        }
    }

    @Override
    public void onRightMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onMiddleMouse(boolean value, double mouseX, double mouseY) {

    }

    @Override
    public void onScroll(Scroll sc, double mouseX, double mouseY) {

    }

    public abstract void onClicked(boolean opened);
}
