package tk.simplexclient.module.impl;

import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;
import java.util.List;

public class TestModule extends HUDModule {

    public TestModule() {
        super("Test", 50, 10);
        renderable.backgroundColor = new Color(0, 0, 0, 84);
        renderable.renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
        return super.getRenderable()
                .renderText("Test", 0, 0, new Color(255, 255, 255));
    }
}
