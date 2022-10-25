package tk.simplexclient.renderer;

import lombok.Getter;
import tk.simplexclient.SimplexClient;

import java.awt.Color;

public class Renderable {
    @Getter
    public int x, y;
    @Getter
    public int width = 0, height = 0;
    public boolean renderBackground = false;
    public Runnable render = () -> {};
    public java.awt.Color backgroundColor;
    public Object owner;
    private static Renderer vr = SimplexClient.getInstance().getRenderer();

    public Renderable(int x, int y, Object owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public Renderable reset(){
        this.render = () -> {};
        return this;
    }

    public Renderable renderText(String text, int x, int y, java.awt.Color color) {
        return this.renderText(text, x, y, color, 1);
    }

    public Renderable fillArea(int startX, int startY, int endX, int endY, java.awt.Color color) {
        Runnable rrrr = render;
        render = () -> {
            rrrr.run();
            vr.drawRoundedRectangle(this.getX() + startX, this.getY() + startY, this.getX() + endX, this.getY() + endY, 3, color);
            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };


        return this;
    }

    public void render() {
        if(this.renderBackground) {
            vr.drawRoundedRectangle(x - 3, y - 3, width + 6, height + 6, 3 , this.backgroundColor);
        }

        this.width = 0;
        this.height = 0;

        this.render.run();
    }

    public void renderWithXY(int x, int y) {
        int xx = this.x;
        int yy = this.y;

        this.x = x;
        this.y = y;

        this.render();

        this.x = xx;
        this.y = yy;
    }

    public Renderable renderText(String text, int x, int y, Color color, float scale) {
        Runnable rrrr = render;
        render = () -> {
            rrrr.run();
            vr.drawStringScaled(text, this.getX() + x, this.getY() + y, scale, color);
            float[] size = vr.getStringWidth(text, scale);
            int endX = (int) (x + size[0]);
            int endY = (int) (y + size[1]);
            if(endX > width) {
                width = endX;
            }
            if(endY > height) {
                height = endY;
            }
        };



        return this;
    }
}
