package tk.simplexclient.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.ui.api.ScreenBridge;
import tk.simplexclient.ui.desktop.Desktop;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {
    public ScreenBridge bridge;

    public MixinTitleScreen() {
        super(net.minecraft.network.chat.Component.nullToEmpty(""));
        this.bridge = SimplexClient.getInstance().getMainMenu();
    }

    /**
     * @author betterclient
     * @reason placeholder main menu
     */
    @Overwrite
    public void init(){
       minecraft.setScreen(new Desktop(true));
       if(this.bridge == null)
           this.bridge = SimplexClient.getInstance().getMainMenu();
       //minecraft.setScreen(new ScreenBase(this.bridge));
    }
}
