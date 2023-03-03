package com.anax.sa_fabrials.item;

import com.anax.sa_fabrials.util.stormlight.IStormlightStorage;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStormlightStorageProvider implements ICapabilityProvider, IStormlightStorage {
    final int capacity;
    final int maxReceive;
    final int maxExtract;
    private int stormlight;
    private final ItemStack stack;
    LazyOptional<ItemStormlightStorageProvider> lazyOptional = LazyOptional.of(()->this);
    public ItemStormlightStorageProvider(int capacity, int maxReceive, int maxExtract, int stormlight, ItemStack stack){
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.stormlight = stormlight;

        this.stack = stack;

        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains("stormlight_capacity")){tag.putInt("stormlight_capacity", capacity);}
        if(!tag.contains("stormlight_maxReceive")){ tag.putInt("stormlight_maxReceive", maxReceive);}
        if(!tag.contains("stormlight_maxExtract")){ tag.putInt("stormlight_maxExtract", maxExtract);}
        if(!tag.contains("stormlight")){ tag.putInt("stormlight", stormlight);}else{
            this.stormlight = tag.getInt("stormlight");
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == StormlightStorage.STORMLIGHT_STORAGE) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public int receiveStormlight(int maxReceive, boolean simulate) {
        updateStormlight();
        int energyReceived = Math.min(maxReceive, this.getMaxStormlightStored() - this.stormlight);
        if (!simulate) {
            this.stormlight += energyReceived;
        }
        stack.getOrCreateTag().putInt("stormlight", stormlight);
        return energyReceived;
    }

    @Override
    public int extractStormlight(int maxExtract, boolean simulate) {
        updateStormlight();
        int energyExtracted = Math.min(maxExtract, this.stormlight);
        if (!simulate) {
            this.stormlight -= energyExtracted;
        }
        stack.getOrCreateTag().putInt("stormlight", stormlight);
        return energyExtracted;
    }

    @Override
    public int getStormlightStored() {
        return stormlight;
    }

    @Override
    public int getMaxStormlightStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0 && stormlight > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive>0&&stormlight<capacity;
    }

    public void updateStormlight(){
        this.stormlight = stack.getOrCreateTag().getInt("stormlight");
    }
}
