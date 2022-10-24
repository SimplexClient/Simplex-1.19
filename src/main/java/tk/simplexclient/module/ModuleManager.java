package tk.simplexclient.module;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.hud.HudModule;
import tk.simplexclient.module.hud.IRenderer;
import tk.simplexclient.module.hud.ScreenPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private final List<IRenderer> renderers = new ArrayList<>();

    private final List<HudModule> modules = new ArrayList<>();

    public void registerModules(HudModule... renderers) {
        this.renderers.addAll(Arrays.asList(renderers));
        this.modules.addAll(Arrays.asList(renderers));
    }

    public List<IRenderer> getRenderers() {
        return this.renderers;
    }

    public List<HudModule> getModules() {
        return modules;
    }

    public List<IRenderer> getEnabledRenderers() {
        return this.renderers.stream().filter(IRenderer::isEnabled).collect(Collectors.toList());
    }

    public List<HudModule> getEnabledHudModules() {
        return this.modules.stream().filter(HudModule::isEnabled).collect(Collectors.toList());
    }

    public void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) return;
        ScreenPosition position = renderer.load();

        if (position == null) {
            position = ScreenPosition.fromRelativePosition(0.5f, 0.5f);
        }

        SimplexClient.getInstance().getRenderer().start();
        {
            renderer.render(position);
        }
        SimplexClient.getInstance().getRenderer().end();
    }

}
