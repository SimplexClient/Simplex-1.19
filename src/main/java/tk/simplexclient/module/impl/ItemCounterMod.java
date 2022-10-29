package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;

public class ItemCounterMod extends HUDModule {
    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting
    public Color textColor = new Color(-1); //Also this...

    public ItemCounterMod() {
        super("Item Counter", 50, 40);
        super.getRenderable().backgroundColor = backgroundColor;
        super.getRenderable().renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
        Renderable renderable1 = this.renderable;

        if(SimplexClient.getInstance().isModMovingScreenEnabled()){
            renderDummy(renderable1);
        }else{
            render(renderable1);
        }

        return renderable1;
    }

    public void render(Renderable r){
        ItemStack stack = Minecraft.getInstance().player.getInventory().getSelected();

        int count = 0;

        for(ItemStack stack1 : Minecraft.getInstance().player.getInventory().items)
        {
            if(stack1.getItem().equals(stack.getItem())) count += stack1.getCount();
        }

        r.renderItemStack(0, 0, stack, count, textColor);
    }

    public void renderDummy(Renderable r){
        r.renderItemStack(0, 0, new ItemStack(Items.DRIED_KELP_BLOCK), 69, textColor);
    }
}
