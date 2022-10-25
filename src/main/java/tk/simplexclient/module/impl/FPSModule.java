package tk.simplexclient.module.impl;

import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;

public class FPSModule extends HUDModule {

    public FPSModule() {
        super("FPS", 10, 10);
        super.getRenderable().backgroundColor = new Color(0, 0, 0, 84);
        super.getRenderable().renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
         return super.getRenderable()
               .renderText(AccessMinecraft.getMinecraft().getFPS() + " FPS", 0, 0, new Color(255, 255, 255));
    }
}
