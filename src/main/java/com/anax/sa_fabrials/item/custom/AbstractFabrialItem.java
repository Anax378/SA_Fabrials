package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractFabrialItem extends Item {
    int capacity;
    int maxExtract;
    int maxReceive;
    int initialStormlight;
    public AbstractFabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
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

        if(!itemStack.getOrCreateTag().contains("power")){itemStack.getOrCreateTag().putInt("power", 1);}
        if(!itemStack.getOrCreateTag().contains("is_attractor")){itemStack.getOrCreateTag().putBoolean("is_attractor", true);}

        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedAndMaxStormlight[0] = handler.getStormlightStored();storedAndMaxStormlight[1] = handler.getMaxStormlightStored();});
        componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedAndMaxStormlight[0]) + "/" + Integer.toString(storedAndMaxStormlight[1])));
        if(Screen.hasShiftDown()){
            componentList.add(new TranslatableComponent("tooltip.sa_fabrials.spren").append(" " + (itemStack.getOrCreateTag().getString("spren").isEmpty() ? "none" : itemStack.getOrCreateTag().getString("spren"))));
            componentList.add(new TranslatableComponent("tooltip.sa_fabrials.power").append(" " + (itemStack.getOrCreateTag().getInt("power"))));
            componentList.add(new TranslatableComponent("tooltip.sa_fabrials.is_attractor").append(" " + (itemStack.getOrCreateTag().getBoolean("is_attractor") ? "§c+" : "§b-")));
        }else {
            componentList.add(new TranslatableComponent("tooltip.sa_fabrials.shift_for_info"));
        }

    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(capacity, maxReceive, maxExtract, initialStormlight, stack);
    }

    @Override
    public boolean canBeHurtBy(DamageSource damageSource) {
        if(damageSource.isExplosion() || damageSource.isFire() || DamageSource.LIGHTNING_BOLT.getMsgId().equals(damageSource.getMsgId())){return false;}
        return super.canBeHurtBy(damageSource);
    }
}
