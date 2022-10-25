package tk.simplexclient.module.config;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.json.JsonFile;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.module.ModuleManager;

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

    public void saveModuleConfigs(){
        for(tk.simplexclient.module.Module module : SimplexClient.getInstance().getModuleManager().modules){
            if(module instanceof HUDModule) saveModuleConfig((HUDModule) module, getScreenPosition((HUDModule) module));
        }
    }

    public void saveModuleConfig(HUDModule module, int[] position) {
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(position[0]);
        pos.add(position[1]);
        append(module.getName().toLowerCase(), new Module(module.isEnabled(), pos));
        save();
    }



    public int[] getScreenPosition(HUDModule module) {
        return new int[] {module.getX(), module.getY()};
    }

    @Override
    public void loadFile() {
        for(tk.simplexclient.module.Module mod : SimplexClient.getInstance().getModuleManager().modules){
            if(mod instanceof HUDModule){
                int[] pos = new int[] {0 , 0};
                getDocument().get(mod.getName().toLowerCase(), Module.class);
                loadModuleConfig((HUDModule) mod, pos);
            }
        }
    }


    public void loadModuleConfig(HUDModule module, int[] position){
        module.setPosition(position);
    }
}
