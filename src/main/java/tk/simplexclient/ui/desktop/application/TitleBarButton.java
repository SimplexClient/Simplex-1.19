package tk.simplexclient.ui.desktop.application;

import lombok.Getter;
import tk.simplexclient.ui.api.UIComponent;

import java.awt.*;

public abstract class TitleBarButton extends UIComponent {

    @Getter
    private final String name;

    @Getter
    private final Color color;

    @Getter
    private final Color hoverColor;

    public boolean hovered;

    public TitleBarButton(String name, int x, int y, int size, Color color, Color hoverColor) {
        super(x, y, size, size);
        this.name = name;
        this.color = color;
        this.hoverColor = hoverColor;
    }

    @Override
    public void render(double mouseX, double mouseY) {
        renderer.start();
        {
            hovered = mouseX >= x && mouseX <= x + endX && mouseY >= y && mouseY <= y + endY;
            Color buttonColor = hovered ? hoverColor : color;
            renderer.drawCircle(x, y, endX, buttonColor);
        }
        renderer.end();
    }

    @Override
    public void onLeftMouse(boolean value, double mouseX, double mouseY) {

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

    public abstract void onClick();
}
