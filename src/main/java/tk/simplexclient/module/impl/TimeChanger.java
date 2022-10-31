package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.RenderEvent;
import tk.simplexclient.module.Module;

public class TimeChanger extends Module {
    private int time = 0; //Make this a setting nyami

    public TimeChanger() {
        super("Time Changer");
    }

    @EventTarget
    public void onRender(RenderEvent e){
        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.setDayTime(time);
    }
}
