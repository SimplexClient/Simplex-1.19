package tk.simplexclient.module.hud;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.Module;
import tk.simplexclient.module.hud.IRenderer;
import tk.simplexclient.module.hud.ScreenPosition;
import tk.simplexclient.renderer.Renderer;

public abstract class HudModule extends Module implements IRenderer {

    protected Renderer renderer;

    public ScreenPosition position;

    public HudModule(String name) {
        super(name);
        renderer = SimplexClient.getInstance().getRenderer();
    }

    public final int getLineOffset(ScreenPosition position, int lineNum) {
        return (int) (position.getAbsoluteY() + getLineOffset(lineNum));
    }

    //TODO: add function
    private int getLineOffset(int lineNum) {
        return 0;
    }

}
