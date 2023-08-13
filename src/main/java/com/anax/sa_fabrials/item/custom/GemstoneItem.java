package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GemstoneItem extends Item {
    int capacity;
    int maxReceive;
    int maxExtract;
    int initialStormlight;
    public GemstoneItem(Properties properties, int capacity, int maxReceive, int maxExtract){
        super(properties);
        this.capacity = capacity;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        this.initialStormlight = 0;
    }
    public int getCapacity(){return capacity;}

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        if(!itemStack.getOrCreateTag().contains("spren")){itemStack.getOrCreateTag().putString("spren", "none");}
        final int[] storedStormlight = {0};
        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedStormlight[0] = handler.getStormlightStored();});
        componentList.add(Component.translatable("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedStormlight[0]) + "/" + Integer.toString(capacity)));
        componentList.add(Component.translatable("tooltip.sa_fabrials.spren").append(" " + itemStack.getOrCreateTag().getString("spren")));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        return super.useOn(pContext);
    }



    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(capacity, maxReceive, maxExtract, initialStormlight, stack);
    }


}
