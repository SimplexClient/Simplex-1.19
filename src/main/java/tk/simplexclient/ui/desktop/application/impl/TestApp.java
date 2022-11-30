package tk.simplexclient.ui.desktop.application.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import tk.simplexclient.ui.desktop.application.App;
import tk.simplexclient.ui.desktop.application.EnumAppButtons;
import tk.simplexclient.ui.desktop.application.EnumAppPosition;
import tk.simplexclient.utils.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class TestApp extends App {
    public TestApp() {
        super("Rendering Test App", EnumAppPosition.CENTER, EnumAppButtons.CLOSE_MINIMIZE_MAXIMIZE, 200, 150);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY) {
        renderer.start();
        {
            renderer.drawRectangle(x, y + (float) (height / 2) - 0.5f, width, 1, Color.WHITE);
            renderer.drawRectangle(x + (float) (width / 2) - 0.5f, y, 1, height, Color.WHITE);

            renderer.drawRectangle(x + (width / 2), y + (height / 2), 20, 20, Color.WHITE);
        }
        renderer.end();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) { }
}
