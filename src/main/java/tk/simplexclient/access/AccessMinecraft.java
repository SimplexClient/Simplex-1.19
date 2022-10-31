package tk.simplexclient.access;

import net.minecraft.client.Minecraft;

public interface AccessMinecraft {

    int getFPS();

    static AccessMinecraft getMinecraft() {
        return (AccessMinecraft) Minecraft.getInstance();
    }

}
