package tk.simplexclient.module.impl;

import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;
import tk.simplexclient.utils.KeyUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CPSModule extends HUDModule {
    private List<Long> leftClicks = new ArrayList<Long>();
    private boolean wasPressedLeft;
    private long lastPressLeft;

    private List<Long> rightClicks = new ArrayList<Long>();
    private boolean wasPressedRight;
    private long lastPressRight;

    public boolean showRightClick = true; //MAKE THIS A SETTING
    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting too
    public Color textColor = new Color(-1); //Also this...

    public CPSModule() {
        super("Cps", 10, 28);
        super.getRenderable().backgroundColor = backgroundColor;
        super.getRenderable().renderBackground = true;
    }

    public String getText() {
        final boolean pressed = KeyUtils.isKeyPressed(0);
        if (pressed != this.wasPressedLeft) {
            this.wasPressedLeft = pressed;
            this.lastPressLeft = System.currentTimeMillis();
            if (pressed) {
                this.leftClicks.add(this.lastPressLeft);
            }
        }

        final boolean pressedR = KeyUtils.isKeyPressed(1);
        if (pressedR != this.wasPressedRight) {
            this.wasPressedRight = pressedR;
            this.lastPressRight = System.currentTimeMillis();
            if (pressedR) {
                this.rightClicks.add(this.lastPressRight);
            }
        }

        final int lcps = this.getLeftCPS();
        final int rcps = this.getRightCPS();

        return lcps + (showRightClick ? " : " + rcps : "");
    }

    private int getRightCPS() {
        final long time = System.currentTimeMillis();
        this.rightClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.rightClicks.size();
    }

    private int getLeftCPS() {
        final long time = System.currentTimeMillis();
        this.leftClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.leftClicks.size();
    }

    @Override
    public Renderable getRenderable() {
        if(renderable.backgroundColor != backgroundColor)
            renderable.backgroundColor = backgroundColor;

        if(client.isModMovingScreenEnabled()){
            return super.getRenderable().renderText("99 : 99", 0, 0, textColor); //Dummy
        }
        return super.getRenderable().
                renderText(getText(), 0, 0, textColor);
    }
}
