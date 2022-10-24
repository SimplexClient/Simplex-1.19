package tk.simplexclient.module.hud;

import net.minecraft.client.Minecraft;

public class ScreenPosition {

    private static final Minecraft mc = Minecraft.getInstance();

    private float x, y;

    public ScreenPosition(float x, float y) {
        setRelative(x, y);
    }

    public static ScreenPosition fromRelativePosition(float x, float y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }

    public float getAbsoluteX() {
        return x * mc.getWindow().getGuiScaledWidth();
    }

    public float getAbsoluteY() {
        return (y * mc.getWindow().getGuiScaledHeight());
    }

    public float getRelativeX() {
        return x;
    }

    public float getRelativeY() {
        return y;
    }

    public void setAbsolute(float x, float y) {
        this.x = x / mc.getWindow().getGuiScaledWidth();
        this.y = y / mc.getWindow().getGuiScaledHeight();
    }

    public void setRelative(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
