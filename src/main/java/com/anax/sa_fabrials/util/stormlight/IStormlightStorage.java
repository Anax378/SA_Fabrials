package com.anax.sa_fabrials.util.stormlight;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

public interface IStormlightStorage {
    public int receiveStormlight(int maxReceive, boolean simulate);
    public int extractStormlight(int maxExtract, boolean simulate);
    public int getStormlightStored();
    public int getMaxStormlightStored();
    public boolean canExtract(Direction side);
    public boolean canReceive(Direction side);
}
