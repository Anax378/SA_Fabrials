package com.anax.sa_fabrials.util.stormlight;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.jetbrains.annotations.Nullable;

public abstract class StormlightStorage implements IStormlightStorage{
    int capacity;
    int maxExtract;
    int maxReceive;
    int stormlight;

    public static Capability<IStormlightStorage> STORMLIGHT_STORAGE = CapabilityManager.get(new CapabilityToken<IStormlightStorage>(){});

    public StormlightStorage(int capacity, int maxReceive, int maxExtract, int stormlight){
        this.capacity = capacity;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        this.stormlight = stormlight;
    }

    public abstract void onChanged();
    public int receiveStormlight(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(maxReceive, this.getMaxStormlightStored() - this.stormlight);
        if (!simulate) {
            this.stormlight += energyReceived;
            this.onChanged();
        }
        return energyReceived;
    }
    public int extractStormlight(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(maxExtract, this.stormlight);
        if (!simulate) {
            this.stormlight -= energyExtracted;
            this.onChanged();
        }
        return energyExtracted;
    }
    public int getStormlightStored() {
        return stormlight;
    }
    public int getMaxStormlightStored() {
        return capacity;
    }
    public boolean canExtract(@Nullable Direction direction) {
        return maxExtract > 0;
    }
    public boolean canReceive(@Nullable Direction direction) {
        return maxReceive > 0;
    }

    public void loadFromNBT(CompoundTag tag){
        this.stormlight = tag.getInt("sa_fabrials.stormlight");
        this.capacity = tag.getInt("sa_fabrials.capacity");
        this.maxReceive = tag.getInt("sa_fabrials.maxReceive");
        this.maxExtract = tag.getInt("sa_fabrials.maxExtract");
    }


    public CompoundTag saveOnNBT(){
        CompoundTag tag = new CompoundTag();
        tag.putInt("sa_fabrials.stormlight", stormlight);
        tag.putInt("sa_fabrials.capacity", capacity);
        tag.putInt("sa_fabrials.maxReceive", maxReceive);
        tag.putInt("sa_fabrials.maxExtract", maxExtract);

        return tag;
    }
}
