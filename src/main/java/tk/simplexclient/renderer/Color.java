package tk.simplexclient.renderer;

import lombok.Getter;
import org.lwjgl.nanovg.NVGColor;

public class Color {

    @Getter
    private final int r, g, b, a;

    private final NVGColor color = NVGColor.create();

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    private NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        return color;
    }

    public NVGColor toNVGColor() {
        return rgba(r, g, b, a, color);
    }

    public int getRGB() {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }
}
