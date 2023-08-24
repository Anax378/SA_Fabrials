package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.NBTHelper;
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
        if(!itemStack.getOrCreateTag().contains(NBTHelper.NBTKeys.CAPTURED_SPREN)){itemStack.getOrCreateTag().putString(NBTHelper.NBTKeys.CAPTURED_SPREN, "none");}
        final int[] storedStormlight = {0};
        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedStormlight[0] = handler.getStormlightStored();});
        componentList.add(Component.translatable("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedStormlight[0]) + "/" + Integer.toString(capacity)));
        componentList.add(Component.translatable("tooltip.sa_fabrials.spren").append(" " + itemStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        return super.useOn(pContext);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        int stormlight = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
        int maxStormlight = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.MAX_STORMLIGHT);
        return (int) Math.round( ((float)stormlight/(float)maxStormlight)*13.0);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        int stormlight = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
        int maxStormlight = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.MAX_STORMLIGHT);
        float normalized = (float)stormlight/(float)maxStormlight;
        int red = (int)Math.round((1.0-normalized)*255);
        int green = (int)Math.round(normalized*255);
        return (red << 16) | (green << 8);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(capacity, maxReceive, maxExtract, initialStormlight, stack);
    }


}
