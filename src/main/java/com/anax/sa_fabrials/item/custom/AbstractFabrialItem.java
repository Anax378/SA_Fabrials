package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.util.NBTHelper;
import com.anax.sa_fabrials.util.fabrial.FabrialClassification;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import leaf.cosmere.api.IHasGemType;
import leaf.cosmere.api.Roshar;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractFabrialItem extends Item implements FabrialClassification.IHasGemType {
    int capacity;
    int maxExtract;
    int maxReceive;
    int initialStormlight;
    FabrialClassification.GemType gemType;
    public AbstractFabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight, FabrialClassification.GemType gemType) {
        super(properties);
        this.capacity = capacity;
        this.initialStormlight = initialStormlight;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        this.gemType = gemType;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        int[] storedAndMaxStormlight = {0, 0};

        if(!itemStack.getOrCreateTag().contains(NBTHelper.NBTKeys.FABRIAL_POWER)){itemStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.FABRIAL_POWER, 1);}
        if(!itemStack.getOrCreateTag().contains(NBTHelper.NBTKeys.FABRIAL_CHARGE)){itemStack.getOrCreateTag().putBoolean(NBTHelper.NBTKeys.FABRIAL_CHARGE, true);}

        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedAndMaxStormlight[0] = handler.getStormlightStored();storedAndMaxStormlight[1] = handler.getMaxStormlightStored();});
        componentList.add(Component.translatable("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedAndMaxStormlight[0]) + "/" + Integer.toString(storedAndMaxStormlight[1])));
        if(Screen.hasShiftDown()){
            componentList.add(Component.translatable("tooltip.sa_fabrials.spren").append(" " + (itemStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN).isEmpty() ? "none" : itemStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN))));
            componentList.add(Component.translatable("tooltip.sa_fabrials.power").append(" " + (itemStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.FABRIAL_POWER))));
            componentList.add(Component.translatable("tooltip.sa_fabrials.is_attractor").append(" " + (itemStack.getOrCreateTag().getBoolean(NBTHelper.NBTKeys.FABRIAL_CHARGE) ? "§c+" : "§b-")));
        }else {
            componentList.add(Component.translatable("tooltip.sa_fabrials.shift_for_info"));
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
        return Mth.hsvToRgb(normalized / 3.0F, 1.0F, 1.0F);

    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public FabrialClassification.GemType getGemType() {
        return gemType;
    }
}
