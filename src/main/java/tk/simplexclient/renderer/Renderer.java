package tk.simplexclient.renderer;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.utils.IOUtil;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.system.MemoryUtil.*;

public class Renderer {

    @Getter
    private final long vg;

    private static final FloatBuffer bounds = BufferUtils.createFloatBuffer(4);

    static final NVGPaint paintA = NVGPaint.create();

    final ByteBuffer roboto = IOUtil.loadResource("assets/minecraft/simplex/fonts/Roboto-Regular.ttf", 1024);
    final ByteBuffer inter = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Regular.ttf", 1024);
    final ByteBuffer interBlack = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Black.ttf", 1024);
    final ByteBuffer interBold = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Bold.ttf", 1024);
    final ByteBuffer interExtraBold = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-ExtraBold.ttf", 1024);
    final ByteBuffer interExtraLight = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-ExtraLight.ttf", 1024);
    final ByteBuffer interLight = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Light.ttf", 1024);
    final ByteBuffer interMedium = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Medium.ttf", 1024);
    final ByteBuffer interSemiBold = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-SemiBold.ttf", 1024);
    final ByteBuffer interThin = IOUtil.loadResource("assets/minecraft/simplex/fonts/Inter-Thin.ttf", 1024);


    public Renderer(long vg) {
        this.vg = vg;
        NanoVG.nvgCreateFontMem(vg, "roboto", roboto, 0);

        // Inter fonts
        NanoVG.nvgCreateFontMem(vg, "inter", inter, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-black", interBlack, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-bold", interBold, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-extraBold", interExtraBold, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-extraLight", interExtraLight, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-light", interLight, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-medium", interMedium, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-semiBold", interSemiBold, 0);
        NanoVG.nvgCreateFontMem(vg, "inter-thin", interThin, 0);
    }

    public void start() {
        SimplexClient.getInstance().getGlState().backupGlState();
        NanoVG.nvgBeginFrame(vg, (float) Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), (float) Minecraft.getInstance().getWindow().getGuiScale());
    }

    public void end() {
        NanoVG.nvgEndFrame(vg);
        SimplexClient.getInstance().getGlState().restoreGlState();
    }

    public void drawRoundedRectangle(float x, float y, float width, float height, float radius, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRoundedRect(vg, x, y, width, height, radius);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRectangle(float x, float y, float width, float height, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x, y, width, height);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRoundedRectangle(int x, int y, float width, float height, float radius, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRoundedRect(vg, x, y, width, height, radius);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRectangle(int x, int y, float width, float height, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x, y, width, height);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRoundedOutline(float x, float y, float width, float height, float radius, float outlineWidth, Color color) {
        try (NVGPaint paint = NVGPaint.calloc()) {
            NanoVG.nvgBoxGradient(vg, x, y, width - outlineWidth, height - outlineWidth, radius, 0, new tk.simplexclient.renderer.Color(0, 0, 0, 0).toNVGColor(), new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor(), paint);
            NanoVG.nvgBeginPath(vg);
            NanoVG.nvgPathWinding(vg, NanoVG.NVG_SOLID);
            NanoVG.nvgRoundedRect(vg,
                    x - (outlineWidth / 2),
                    y - (outlineWidth / 2),
                    width,
                    height,
                    radius);
            NanoVG.nvgFillPaint(vg, paint);
            NanoVG.nvgFill(vg);
        }

    }

    public void drawOutline(float x, float y, float width, float height, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x, y, width, height);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRoundedRectWithShadow(float x, float y, float width, float height, float radius, float shadowSize, Color color) {
        NVGPaint shadowPaint = paintA;
        NanoVG.nvgBoxGradient(vg, x - (shadowSize / 2), y - (shadowSize / 2), width + shadowSize, height + shadowSize, radius * 2, 10, new tk.simplexclient.renderer.Color(0, 0, 0, 128).toNVGColor(), new tk.simplexclient.renderer.Color(0, 0, 0, 0).toNVGColor(), shadowPaint);
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x - 10, y - 10, width + 20, height + 30);
        NanoVG.nvgPathWinding(vg, NanoVG.NVG_HOLE);
        NanoVG.nvgFillPaint(vg, shadowPaint);
        NanoVG.nvgFill(vg);

        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRoundedRect(vg, x, y, width, height, radius);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawRectWithShadow(float x, float y, float width, float height, float shadowSize, Color color) {
        NVGPaint shadowPaint = paintA;
        NanoVG.nvgBoxGradient(vg, x - (shadowSize / 2), y - (shadowSize / 2), width + shadowSize, height + shadowSize, 0, 10, new tk.simplexclient.renderer.Color(0, 0, 0, 128).toNVGColor(), new tk.simplexclient.renderer.Color(0, 0, 0, 0).toNVGColor(), shadowPaint);
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x - 10, y - 10, width + 20, height + 30);
        NanoVG.nvgPathWinding(vg, NanoVG.NVG_HOLE);
        NanoVG.nvgFillPaint(vg, shadowPaint);
        NanoVG.nvgFill(vg);

        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgRect(vg, x, y, width, height);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawCircle(float x, float y, float size, Color color) {
        NanoVG.nvgBeginPath(vg);
        NanoVG.nvgCircle(vg, x, y, size);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nvgFill(vg);
    }

    public void drawString(String text, float x, float y, Color color) {
        drawStringScaled(text, x, y, 8.0f, color, "roboto");
    }

    public void drawString(String text, int[] pos, Color color) {
        drawStringScaled(text, pos[0], pos[1], 8.0f, color, "roboto");
    }

    public void drawString(String text, float x, float y, Color color, String font) {
        drawStringScaled(text, x, y, 1, color, font);
    }

    public void drawString(String text, int[] pos, Color color, String font) {
        drawStringScaled(text, pos[0], pos[1], 8.0f, color, font);
    }

    /**
     * Don't use this method!
     *
     * @param size Changes the font size (8.0f * size)
     */
    @Deprecated
    public void drawStringScaled(String text, float x, float y, float size, Color color) {
        drawStringScaled(text, x, y, 8.0f * size, color, "roboto");
    }

    /**
     * @param size Changes the font size
     */
    public void drawStringScaled(String text, float x, float y, float size, Color color, String font) {
        ByteBuffer byteBuffer = memUTF8(text, false);
        memFree(byteBuffer);
        NanoVG.nvgFontSize(vg, size);
        NanoVG.nvgFontFace(vg, font);
        NanoVG.nvgTextMetrics(vg, null, null, BufferUtils.createFloatBuffer(1));

        long start = memAddress(byteBuffer);
        long end = start + byteBuffer.remaining();

        NanoVG.nvgTextBounds(vg, x, y, byteBuffer, bounds);
        NanoVG.nvgFillColor(vg, new tk.simplexclient.renderer.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).toNVGColor());
        NanoVG.nnvgText(vg, x, y + (bounds.get(3) - bounds.get(1)) - 2, start, end);
    }

    public float[] getStringWidth(String text) {
        return getStringWidth(text, 8.0f);
    }

    public float[] getStringWidth(String text, float scale, String font) {
        ByteBuffer byteBuffer = memUTF8(text, false);
        memFree(byteBuffer);
        float size1 = 8.0f;

        NanoVG.nvgFontSize(vg, scale);
        NanoVG.nvgFontFace(vg, font);
        NanoVG.nvgTextMetrics(vg, null, null, BufferUtils.createFloatBuffer(1));
        NanoVG.nvgTextBounds(vg, 0, 0, byteBuffer, bounds);
        return new float[]{bounds.get(2) - bounds.get(0), (bounds.get(3) - bounds.get(1)) - 2};
    }

    public float[] getStringWidth(String text, float scale) {
        ByteBuffer byteBuffer = memUTF8(text, false);
        memFree(byteBuffer);

        NanoVG.nvgFontSize(vg, scale);
        NanoVG.nvgFontFace(vg, "roboto");
        NanoVG.nvgTextMetrics(vg, null, null, BufferUtils.createFloatBuffer(1));
        NanoVG.nvgTextBounds(vg, 0, 0, byteBuffer, bounds);
        return new float[]{bounds.get(2) - bounds.get(0), (bounds.get(3) - bounds.get(1)) - 2};
    }

    public int[] getIdealRenderingPosForText(String text, int x, int y, int endX, int endY) {
        float[] pos = getStringWidth(text);
        float afterWidth = pos[0];
        float afterHeight = pos[1];

        int tx = ((int) (((endX - x) / 2) - (afterWidth / 2)));
        int ty = ((int) (((endY - y) / 2) - (afterHeight / 2)));

        return new int[]{x + tx, y + ty};
    }
}