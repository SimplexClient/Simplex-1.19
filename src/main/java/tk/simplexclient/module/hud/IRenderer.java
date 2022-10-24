package tk.simplexclient.module.hud;

public interface IRenderer extends IRenderConfig {

    float getWidth();

    float getHeight();

    void render(ScreenPosition position);

    default void renderDummy(ScreenPosition position) {
        render(position);
    }

    default boolean isEnabled() {
        return true;
    }

}
