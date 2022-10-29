package tk.simplexclient.module;

import tk.simplexclient.module.impl.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public List<Module> modules = new ArrayList<>();

    public ModuleManager(){
        modules.add(new CPSModule());
        modules.add(new FPSModule());
        modules.add(new KeyStrokes());
        modules.add(new ArmorStatusModule());
        modules.add(new ItemCounterMod());
        modules.add(new TimeDisplay());
    }

    public List<Module> getEnabledModules() {
        List<Module> mods = new ArrayList<>(modules);
        mods.removeIf(module -> !module.isEnabled());
        return mods;
    }
}
