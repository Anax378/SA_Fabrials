package com.anax.sa_fabrials.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

public class SmokeCloud extends Entity {

    static final EntityDataAccessor<Integer> LIFETIME_TICKS_LEFT = SynchedEntityData.defineId(SmokeCloud.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(SmokeCloud.class, EntityDataSerializers.FLOAT);
    boolean isStable;
    public SmokeCloud(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public int getLifeTime(){
        return this.entityData.get(LIFETIME_TICKS_LEFT);
    };

    public void setLifeTime(int value){
        this.entityData.set(LIFETIME_TICKS_LEFT, value);
    }

    public float getSize(){
        return this.entityData.get(SIZE);
    }

    public void setSize(float size){
        this.entityData.set(SIZE, size);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(LIFETIME_TICKS_LEFT, 200);
        this.entityData.define(SIZE, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        setSize(pCompound.getFloat("size"));
        setLifeTime(pCompound.getInt("lifeTimeTicks"));
        this.isStable = pCompound.getBoolean("isStable");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
       pCompound.putInt("lifeTimeTicks", getLifeTime());
       pCompound.putFloat("size", getSize());
       pCompound.putBoolean("isStable", isStable);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void tick() {
        if(!this.getLevel().isClientSide()) {
            if (!this.isStable) {
                this.setLifeTime(this.getLifeTime() - 1);
                if (this.getLifeTime() < -20) {
                    this.discard();
                }
            }
        }
    }
}
