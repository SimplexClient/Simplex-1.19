package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;
import tk.simplexclient.renderer.Renderer;
import tk.simplexclient.utils.KeyUtils;

import java.awt.*;

public class KeyStrokes extends HUDModule {

    public boolean mouseKeys = true; //Make this a setting when you make the setting manager to Nyami by Betterclient
    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting too
    public Color backgroundColorPressed = new Color(150, 140, 140,156); // This one aswell
    public Color textColor = new Color(-1); //Also this...

    public KeyStrokes() {
        super("KeyStrokes", 90, 10);
    }

    @Override
    public Renderable getRenderable() {
        Renderable renderable1 = super.getRenderable();

        //Render
        render(renderable1);

        return renderable1;
    }

    public void render(Renderable r){
        boolean leftClick = KeyUtils.isKeyPressed(0);
        boolean rightClick = KeyUtils.isKeyPressed(1);
        Options op = Minecraft.getInstance().options;

        boolean w = op.keyUp.isDown();
        boolean s = op.keyDown.isDown();
        boolean d = op.keyRight.isDown();
        boolean a = op.keyLeft.isDown();

        Renderer renderer = SimplexClient.getInstance().getRenderer();

        r.fillArea(30, 0, 50, 10, w ? backgroundColorPressed : backgroundColor); //W
        r.fillArea(30, 15, 50, 25, s ? backgroundColorPressed : backgroundColor); //S
        r.fillArea(5, 15, 25, 25, a ? backgroundColorPressed : backgroundColor); //A
        r.fillArea(55, 15, 75, 25, d ? backgroundColorPressed : backgroundColor); //D

        r.renderText("W", r.getIdealRenderingPosForText("W", 30, 0, 50, 10), textColor);
        r.renderText("A", r.getIdealRenderingPosForText("A", 5, 15, 25, 25), textColor);
        r.renderText("S", r.getIdealRenderingPosForText("S", 30, 15, 50, 25), textColor);
        r.renderText("D", r.getIdealRenderingPosForText("D", 55, 15, 75, 25), textColor);

        if(mouseKeys){
            r.fillArea(5, 30, 38, 45, leftClick ? backgroundColorPressed : backgroundColor); //Left
            r.fillArea(42, 30, 75, 45, rightClick ? backgroundColorPressed : backgroundColor); //Right

            r.renderText("LMB", r.getIdealRenderingPosForText("LMB", 5, 30, 38, 45), textColor);
            r.renderText("RMB", r.getIdealRenderingPosForText("RMB", 42, 30, 75, 45), textColor);
        }
    }
}
