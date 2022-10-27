package tk.simplexclient.module;

import tk.simplexclient.module.impl.ArmorStatusModule;
import tk.simplexclient.module.impl.CPSModule;
import tk.simplexclient.module.impl.FPSModule;
import tk.simplexclient.module.impl.KeyStrokes;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public List<Module> modules = new ArrayList<>();

    public ModuleManager(){
        modules.add(new CPSModule());
        modules.add(new FPSModule());
        modules.add(new KeyStrokes());
        modules.add(new ArmorStatusModule());
    }

    public List<Module> getEnabledModules() {
        List<Module> mods = new ArrayList<>(modules);
        mods.removeIf(module -> !module.isEnabled());
        return mods;
    }
}
