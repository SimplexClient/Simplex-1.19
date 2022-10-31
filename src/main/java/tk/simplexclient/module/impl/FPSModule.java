package tk.simplexclient.module.impl;

import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;
import java.util.ArrayList;

public class FPSModule extends HUDModule {

    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting too
    public Color textColor = new Color(-1); //Also this...
    public boolean smoothFps = true; // ALSO THIIIS

    private ArrayList<Long> fps = new ArrayList<>();

    public FPSModule() {
        super("FPS", 10, 10);
        super.getRenderable().backgroundColor = backgroundColor;
        super.getRenderable().renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
        if(renderable.backgroundColor != backgroundColor)
            renderable.backgroundColor = backgroundColor;

        if(smoothFps){
            fps.add(System.currentTimeMillis());
            fps.removeIf(aLong -> aLong + 1000L < System.currentTimeMillis());
        }

         return super.getRenderable()
               .renderText((smoothFps ? fps.size() : AccessMinecraft.getMinecraft().getFPS()) + " FPS", 0, 0, textColor);
    }
}
