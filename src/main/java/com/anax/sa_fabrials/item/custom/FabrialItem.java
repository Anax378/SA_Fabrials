package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FabrialItem extends Item {
    int capacity;
    int maxExtract;
    int maxReceive;
    int initialStormlight;
    public FabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
        super(properties);
        this.capacity = capacity;
        this.initialStormlight = initialStormlight;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        int[] storedAndMaxStormlight = {0, 0};
        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedAndMaxStormlight[0] = handler.getStormlightStored();storedAndMaxStormlight[1] = handler.getMaxStormlightStored();});
        componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedAndMaxStormlight[0]) + "/" + Integer.toString(storedAndMaxStormlight[1])));
    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(capacity, maxReceive, maxExtract, initialStormlight, stack);
    }
}
