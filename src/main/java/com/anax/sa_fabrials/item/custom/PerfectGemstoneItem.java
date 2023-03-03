package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PerfectGemstoneItem extends Item {
    int capacity;
    int maxReceive;
    int maxExtract;
    int initialStormlight;
    public PerfectGemstoneItem(Properties properties, int capacity){
        super(properties);
        this.capacity = capacity;
        maxExtract = 500;
        maxReceive = 500;
        initialStormlight = 500;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        final int[] storedStormlight = {0};
        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedStormlight[0] = handler.getStormlightStored();});
        componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedStormlight[0]) + "/" + Integer.toString(capacity)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        pContext.getItemInHand().getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {handler.receiveStormlight(1, false);});
        return super.useOn(pContext);
    }



    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(capacity, maxReceive, maxExtract, initialStormlight, stack);
    }


}
