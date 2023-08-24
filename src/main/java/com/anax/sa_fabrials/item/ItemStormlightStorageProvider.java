package com.anax.sa_fabrials.item;

import com.anax.sa_fabrials.util.NBTHelper;
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
        if(!tag.contains(NBTHelper.NBTKeys.MAX_STORMLIGHT)){tag.putInt(NBTHelper.NBTKeys.MAX_STORMLIGHT, capacity);}
        if(!tag.contains(NBTHelper.NBTKeys.MAX_RECEIVE)){ tag.putInt(NBTHelper.NBTKeys.MAX_RECEIVE, maxReceive);}
        if(!tag.contains(NBTHelper.NBTKeys.MAX_EXTRACT)){ tag.putInt(NBTHelper.NBTKeys.MAX_EXTRACT, maxExtract);}
        if(!tag.contains(NBTHelper.NBTKeys.STORED_STORMLIGHT)){ tag.putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight);}else{
            this.stormlight = tag.getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
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
        int energyReceived = Math.min(Math.min(maxReceive, this.maxReceive), this.getMaxStormlightStored() - this.stormlight);
        if (!simulate) {
            this.stormlight += energyReceived;
        }
        stack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight);
        return energyReceived;
    }

    @Override
    public int extractStormlight(int maxExtract, boolean simulate) {
        updateStormlight();
        int energyExtracted = Math.min(Math.min(maxExtract, this.maxExtract), this.stormlight);
        if (!simulate) {
            this.stormlight -= energyExtracted;
        }
        stack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight);
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
    public boolean canExtract(Direction side) {
        return maxExtract > 0 && stormlight > 0;
    }

    @Override
    public boolean canReceive(Direction side) {
        return maxReceive>0&&stormlight<capacity;
    }

    public void updateStormlight(){
        this.stormlight = stack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
    }
}
