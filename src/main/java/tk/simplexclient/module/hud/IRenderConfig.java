package tk.simplexclient.module.hud;

public interface IRenderConfig {

    void save(ScreenPosition position);

    ScreenPosition load();

}
