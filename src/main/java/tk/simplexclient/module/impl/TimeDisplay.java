package tk.simplexclient.module.impl;

import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDisplay extends HUDModule {
    private final DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting
    public Color textColor = new Color(-1); //Also this...

    public TimeDisplay() {
        super("Time Display", 90, 50);
        super.getRenderable().backgroundColor = backgroundColor;
        super.getRenderable().renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
        return super.getRenderable().
                renderText(dateFormat.format(new Date()), 0, 0, textColor);
    }
}
