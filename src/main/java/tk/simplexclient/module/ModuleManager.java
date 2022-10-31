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
        modules.add(new ItemPhysicsMod());
    }

    public List<Module> getEnabledModules() {
        List<Module> mods = new ArrayList<>(modules);
        mods.removeIf(module -> !module.isEnabled());
        return mods;
    }

    public Module getModule(String name){
        for (Module module : modules) if(module.getName().equalsIgnoreCase(name)) return module;
        return null;
    }
}
