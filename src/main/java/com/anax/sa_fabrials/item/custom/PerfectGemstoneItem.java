package com.anax.sa_fabrials.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PerfectGemstoneItem extends Item {
    public int maximumStormlight;
    public PerfectGemstoneItem(Properties properties, int maximumStormlight){
        super(properties);
        this.maximumStormlight = maximumStormlight;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(itemStack.getOrCreateTag().getInt("sa_fabrials.stored_stormlight")) + "/" + Integer.toString(maximumStormlight)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        CompoundTag tag = pContext.getItemInHand().getOrCreateTag();
        int currentStormlight = tag.getInt("sa_fabrials.stored_stormlight");
        tag.putInt("sa_fabrials.stored_stormlight", currentStormlight+1);
        return super.useOn(pContext);
    }


}
