package tk.simplexclient.module.config;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.json.JsonFile;
import tk.simplexclient.module.hud.HudModule;
import tk.simplexclient.module.hud.ScreenPosition;

import java.util.ArrayList;

public class ModuleConfig extends JsonFile {

    public ModuleConfig() {
        super(SimplexClient.GSON, "simplex/modules/config.json");
        if (!exists()) {
            create();
            append("config-version", "0");
            save();
        }
        load();
        if (!get("config-version", String.class).equals("0")) {
            getFile().delete();
            create();
            append("config-version", "0");
            save();
        }
    }

    public void saveModuleConfig(HudModule module, ScreenPosition position) {
        ArrayList<Float> pos = new ArrayList<>();
        pos.add(position.getRelativeX());
        pos.add(position.getRelativeY());
        append(module.getName().toLowerCase(), new Module(module.isEnabled(), pos));
        save();
    }

    public ScreenPosition getScreenPosition(HudModule module) {
        Module mod = get(module.getName().toLowerCase(), Module.class);
        return mod == null ? ScreenPosition.fromRelativePosition(0, 0) : ScreenPosition.fromRelativePosition(mod.getPos().get(0), mod.getPos().get(1));
    }
}
