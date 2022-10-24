package tk.simplexclient.module;

import lombok.Getter;
import tk.simplexclient.module.impl.FPSModule;
import tk.simplexclient.module.impl.TestModule;

@Getter
public class ModInstances {

    private static FPSModule fpsModule;

    private static TestModule testModule;


    public static void register(ModuleManager manager) {
        fpsModule = new FPSModule();
        testModule = new TestModule();
        manager.registerModules(fpsModule, testModule);
    }

}
