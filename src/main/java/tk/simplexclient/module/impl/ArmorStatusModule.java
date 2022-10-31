package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tk.simplexclient.module.HUDModule;
import tk.simplexclient.renderer.Renderable;

import java.awt.*;

public class ArmorStatusModule extends HUDModule {
    public boolean leftToRight = false; //MAKE THIS A SETTING
    public boolean renderDurability = false; // THIS ONE TOO
    public Color backgroundColor = new Color(0,0,0,84); // Make this a setting too

    public ArmorStatusModule() {
        super("Armor Status", 10, 90);
        super.getRenderable().backgroundColor = backgroundColor;
        super.getRenderable().renderBackground = true;
    }

    @Override
    public Renderable getRenderable() {
        Renderable rend = super.getRenderable();

        if(!client.isModMovingScreenEnabled())
            render(rend);
        else
            renderDummy(rend);

        return rend;
    }

    private void render(Renderable r){
        if(r.backgroundColor != backgroundColor)
            r.backgroundColor = backgroundColor;

        assert Minecraft.getInstance().player != null;
        ItemStack helm = Minecraft.getInstance().player.getInventory().getArmor(3);
        ItemStack chest = Minecraft.getInstance().player.getInventory().getArmor(2);
        ItemStack legging = Minecraft.getInstance().player.getInventory().getArmor(1);
        ItemStack boots = Minecraft.getInstance().player.getInventory().getArmor(0);

        if(leftToRight){
            r.renderItemStack(60, 0, helm, renderDurability);
            r.renderItemStack(40, 0, chest, renderDurability);
            r.renderItemStack(20, 0, legging, renderDurability);
            r.renderItemStack(0, 0, boots, renderDurability);
        }else{
            r.renderItemStack(0, 0, helm, renderDurability);
            r.renderItemStack(0, 20, chest, renderDurability);
            r.renderItemStack(0, 40, legging, renderDurability);
            r.renderItemStack(0, 60, boots, renderDurability);
        }
    }

    private void renderDummy(Renderable r){
        if(leftToRight){
            r.renderItemStack(60, 0, new ItemStack(Items.DIAMOND_HELMET), renderDurability);
            r.renderItemStack(40, 0, new ItemStack(Items.DIAMOND_CHESTPLATE), renderDurability);
            r.renderItemStack(20, 0, new ItemStack(Items.DIAMOND_LEGGINGS), renderDurability);
            r.renderItemStack(0, 0, new ItemStack(Items.DIAMOND_BOOTS), renderDurability);
        }else{
            r.renderItemStack(0, 0, new ItemStack(Items.DIAMOND_HELMET), renderDurability);
            r.renderItemStack(0, 20, new ItemStack(Items.DIAMOND_CHESTPLATE), renderDurability);
            r.renderItemStack(0, 40, new ItemStack(Items.DIAMOND_LEGGINGS), renderDurability);
            r.renderItemStack(0, 60, new ItemStack(Items.DIAMOND_BOOTS), renderDurability);
        }
    }
}
