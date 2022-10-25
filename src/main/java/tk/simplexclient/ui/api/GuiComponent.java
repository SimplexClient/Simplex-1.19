package tk.simplexclient.ui.api;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.renderer.Renderer;

public abstract class GuiComponent {
    public int
            x,
            y,
            endX,
            endY;
    public Renderer vr;

    public int startX, startY, startEndX, startEndY;

    public GuiComponent(int x, int y, int endX, int endY) {
        this.vr = SimplexClient.getInstance().getRenderer();
        this.x = x;
        this.y = y;
        this.endX = endX;
        this.endY = endY;

        this.startX = x;
        this.startY = y;
        this.startEndX = endX;
        this.startEndY = endY;
    }

    public abstract void render(double mouseX, double mouseY);
    public abstract void onLeftMouse(boolean value, double mouseX, double mouseY);
    public abstract void onRightMouse(boolean value, double mouseX, double mouseY);
    public abstract void onMiddleMouse(boolean value, double mouseX, double mouseY);
    public abstract void onScroll(Scroll sc, double mouseX, double mouseY);

    public boolean basicCollisionCheck(double mouseX, double mouseY, int x, int y, int endX, int endY) {
        return mouseX >= x & mouseX <= endX & mouseY >= y & mouseY <= endY;
    }

    public enum Scroll{
        UP,
        DOWN
    }
}
