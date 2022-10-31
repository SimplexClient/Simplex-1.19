package tk.simplexclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.nanovg.NanoVGGL3;
import tk.simplexclient.module.ModuleManager;
import tk.simplexclient.module.config.ModuleConfig;
import tk.simplexclient.renderer.GLState;
import tk.simplexclient.renderer.Renderer;
import tk.simplexclient.ui.DraggableScreen;
import tk.simplexclient.ui.MainMenuScreen;
import tk.simplexclient.ui.api.ScreenBase;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.utils.KeyUtils;

public class SimplexClient {

    @Getter
    private long vg;

    @Getter
    private GLState glState;

    @Getter
    private Renderer renderer;

    @Getter
    private ModuleConfig moduleConfig;

    @Getter
    private ModuleManager moduleManager;

    @Getter
    private static KeyUtils keyUtils;

    @Getter
    private static SimplexClient instance;

    @Getter
    private static KeyMapping draggableHud;

    @Getter
    @Setter
    private boolean isInHud = false;

    @Getter
    private ScreenBridge mainMenu;

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public void init() {
        keyUtils = new KeyUtils();

        // KeyMappings
        draggableHud = new KeyMapping("Draggable Hud", InputConstants.KEY_RSHIFT, "SimplexClient");
        keyUtils.registerKeyBind(draggableHud);
    }

    /**
     * Initializes the Main classes of the Client
     */
    public void start() {
        instance = this;
        vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);

        mainMenu = new MainMenuScreen();
        glState = new GLState();
        renderer = new Renderer(vg);
        moduleManager = new ModuleManager();
        moduleConfig = new ModuleConfig();
    }

    /**
     * Stops the Client
     */
    public void stop() {
        System.out.println("Saving module config...");
        moduleConfig.saveModuleConfigs();
    }

    public boolean isModMovingScreenEnabled(){
        return Minecraft.getInstance().screen instanceof ScreenBase && ((ScreenBase) Minecraft.getInstance().screen).bridge instanceof DraggableScreen;
    }
}
