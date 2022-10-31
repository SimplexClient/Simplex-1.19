package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventBus;

public class Module {

    @Getter
    private boolean enabled;

    @Getter
    @Setter
    private String name;

    protected final Minecraft mc;
    protected final SimplexClient client;

    public Module(String name) {
        this.mc = Minecraft.getInstance();
        this.client = SimplexClient.getInstance();

        this.name = name;
        this.setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        if(enabled)
            EventBus.getInstance().subscribe(this);
        else
            EventBus.getInstance().unSubscribe(this);

        this.enabled = enabled;
    }
}
