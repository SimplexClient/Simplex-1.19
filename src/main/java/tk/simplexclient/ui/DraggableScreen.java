package tk.simplexclient.ui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.hud.IRenderConfig;
import tk.simplexclient.module.hud.IRenderer;
import tk.simplexclient.module.hud.ScreenPosition;
import tk.simplexclient.renderer.Renderer;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;

public class DraggableScreen extends Screen {

    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    private Renderer simplexRenderer;

    public DraggableScreen() {
        super(Component.translatable("Simplex draggable screen"));

        simplexRenderer = SimplexClient.getInstance().getRenderer();

        for (IRenderer renderer : SimplexClient.getInstance().getModuleManager().getEnabledRenderers()) {
            if (!renderer.isEnabled()) continue;
            ScreenPosition position = renderer.load();

            if (position == null) {
                position = ScreenPosition.fromRelativePosition(0.5f, 0.5f);
            }

            adjustBounds(renderer, position);
            this.renderers.put(renderer, position);
        }

    }

    @Override
    protected void init() {
        renderers.forEach(this::adjustBounds);
        Minecraft.getInstance().options.hideGui = true;
        SimplexClient.getInstance().setInHud(true);
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float f) {
        simplexRenderer.start();
        {
            for (IRenderer renderer : renderers.keySet()) {
                ScreenPosition position = renderers.get(renderer);
                renderer.renderDummy(position);

            }

            simplexRenderer.drawRectangle(0, (float) height / 2 - 0.5f, width, 1, Color.WHITE);
            simplexRenderer.drawRectangle((float) width / 2 - 0.5f, 0, 1, height, Color.WHITE);

            selectedRenderer.ifPresent(renderer -> {
                ScreenPosition position = renderers.get(renderer);

                float finalX, finalY;

                for (IRenderer renderer1 : renderers.keySet()) {
                    ScreenPosition position1 = renderers.get(renderer1);

                    if (renderer1 != renderer) {
                        if (mouseX >= (position1.getAbsoluteX() - 5) & mouseX <= (position1.getAbsoluteX() + renderer.getWidth() + 5)) {
                            finalX = position.getAbsoluteY() == position1.getAbsoluteY() ? position1.getAbsoluteX() + renderer1.getWidth() : position1.getAbsoluteX();
                            position.setAbsolute(finalX, position.getAbsoluteY());
                        }

                        if (mouseY >= (position1.getAbsoluteY() - 5) & mouseY <= (position1.getAbsoluteY() + renderer.getHeight() + 5)) {
                            finalY = position.getAbsoluteX() == position1.getAbsoluteX() ? position1.getAbsoluteY() + renderer1.getHeight() : position1.getAbsoluteY();
                            position.setAbsolute(position.getAbsoluteX(), finalY);
                        }
                    }

                }

                position.setAbsolute(mouseX + position.getAbsoluteX() - prevX, mouseY + position.getAbsoluteY() - prevY);

                adjustBounds(renderer, position);

                prevX = mouseX;
                prevY = mouseY;
            });
        }
        simplexRenderer.end();

        super.render(poseStack, mouseX, mouseY, f);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().options.hideGui = false;
        SimplexClient.getInstance().setInHud(false);
        renderers.forEach(IRenderConfig::save);
        super.onClose();
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (i == InputConstants.KEY_RSHIFT) {
            this.onClose();
        }
        return super.keyPressed(i, j, k);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        this.prevX = (int) x;
        this.prevY = (int) y;

        selectedRenderer =
                SimplexClient
                        .getInstance()
                        .getModuleManager()
                        .getRenderers()
                        .stream()
                        .filter(renderer ->
                                renderer.isEnabled()
                                        && x >= renderers.get(renderer).getAbsoluteX() - 2.5
                                        && x <= renderers.get(renderer).getAbsoluteX() - 2.5 + (renderer.getWidth() + 1)
                                        && y >= renderers.get(renderer).getAbsoluteY() - 2.5
                                        && y <= renderers.get(renderer).getAbsoluteY() - 2.5 + renderer.getHeight() + 1)
                        .findFirst();
        return super.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double d, double e, int i) {
        selectedRenderer = Optional.empty();
        return super.mouseReleased(d, e, i);
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition position) {
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        float absoluteX = Math.max(4, Math.min(position.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth() + 4, 0)));
        float absoluteY = Math.max(3, Math.min(position.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight() + 3, 0)));

        position.setAbsolute(absoluteX, absoluteY);
    }

}
