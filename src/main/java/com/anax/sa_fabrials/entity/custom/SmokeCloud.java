package com.anax.sa_fabrials.entity.custom;

import com.anax.sa_fabrials.effect.SAEffects;
import com.anax.sa_fabrials.effect.SmokeEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.phys.AABB;

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
            if(this.getLifeTime() >= 0) {
                AABB bb = new AABB(
                        this.getX() - (this.getSize() / 2f),
                        this.getY() - (this.getSize() / 2f),
                        this.getZ() - (this.getSize() / 2f),
                        this.getX() + (this.getSize() / 2f),
                        this.getY() + (this.getSize() / 2f),
                        this.getZ() + (this.getSize() / 2f)
                );
                for (Entity entity : this.getLevel().getEntities(this, bb, (entity) -> {
                    return entity instanceof LivingEntity;
                })) {
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(SAEffects.SMOKE_EFFECT.get(), 5));
                }
            }
            if (!this.isStable) {
                this.setLifeTime(this.getLifeTime() - 1);
                if (this.getLifeTime() < -20) {
                    this.discard();
                }
            }
        }
    }
}
