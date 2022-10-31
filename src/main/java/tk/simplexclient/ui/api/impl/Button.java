package tk.simplexclient.ui.api.impl;

import tk.simplexclient.ui.api.UIComponent;

import java.awt.*;

public abstract class Button extends UIComponent {
    private Color backgroundColor;
    private String text;

    public Button(int x, int y, int endX, int endY, boolean isWidth, Color backgroundColor, String text) {
        super(x, y, endX + (isWidth ? x : 0), endY + (isWidth ? y : 0));
        this.backgroundColor = backgroundColor;
        this.text = text;
    }

    @Override
    public void render(double mouseX, double mouseY) {
        if(basicCollisionCheck(mouseX,mouseY,x,y,endX,endY)){
            renderer.drawRoundedRectangle(x - 2, y - 2, endX - x + 4, endY - y + 4, 5f, backgroundColor);
            renderer.drawString(text, renderer.getIdealRenderingPosForText(text, x - 2, y - 2, endX + 2, endY + 2), new Color(-1));
        }else{
            renderer.drawRoundedRectangle(x, y, (float) endX - x, (float) endY - y, 5f, backgroundColor);
            renderer.drawString(text, renderer.getIdealRenderingPosForText(text, x, y, endX, endY), new Color(-1));
        }
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {
        if(!value && basicCollisionCheck(mouseX, mouseY, x, y, endX, endY)){
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
