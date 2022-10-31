package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import tk.simplexclient.SimplexClient;

public class Module {

    @Getter
    @Setter
    private boolean enabled = true;

    @Getter
    @Setter
    private String name;

    protected final Minecraft mc;
    protected final SimplexClient client;

    public Module(String name) {
        this.mc = Minecraft.getInstance();
        this.client = SimplexClient.getInstance();

        this.name = name;
    }

}
