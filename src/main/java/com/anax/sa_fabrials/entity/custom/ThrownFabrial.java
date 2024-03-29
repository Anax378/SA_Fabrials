package com.anax.sa_fabrials.entity.custom;

import com.anax.sa_fabrials.entity.SAEntityTypes;
import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.util.NBTHelper;
import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import static net.minecraft.world.Containers.dropItemStack;

public class ThrownFabrial extends ThrowableItemProjectile {
    private ItemStack itemStack = Items.AIR.getDefaultInstance();
    public ThrownFabrial(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownFabrial(Level level, LivingEntity entity){
        super(SAEntityTypes.THROWN_FABRIAL.get(), entity, level);
    }

    public ThrownFabrial(Level level, LivingEntity entity, ItemStack itemStack){
        super(SAEntityTypes.THROWN_FABRIAL.get(), entity, level);
        init(itemStack);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void handleEntityEvent(byte pByte) {
        if (pByte == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, itemStack);

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.put("fabrial_item", getItem().save(new CompoundTag()));
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        setItem(ItemStack.of((CompoundTag) tag.get("fabrial_item")));
        super.readAdditionalSaveData(tag);
    }

    public void init(ItemStack stack){
        itemStack = stack;
        super.setItem(stack);
    }

    public void drop(Level level, double x, double y, double z){
        dropItemStack(level, x, y, z, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        if(itemStack == null){return SAItems.THROWABLE_SMOKESTONE_FABRIAL.get();}
        return itemStack.getItem();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if(itemStack != null){
            int power = itemStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.FABRIAL_POWER);
            boolean charge = itemStack.getOrCreateTag().getBoolean(NBTHelper.NBTKeys.FABRIAL_CHARGE);
            String spren = itemStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN);
            Vec3 pos = new Vec3(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());

            int stormlight = itemStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
            int stormlightNeeded = FabrialEffects.targetBlock(pos, this.getLevel(), power, charge, this.getDeltaMovement(), blockHitResult.getDirection(), spren, true);
            if(stormlight >= stormlightNeeded){
                FabrialEffects.targetBlock(pos, this.getLevel(), power, charge, this.getDeltaMovement(), blockHitResult.getDirection(), spren, false);
                itemStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight - stormlightNeeded);
            }
        }
        this.drop(this.getLevel(), this.getX(), this.getY(), this.getZ());
        this.discard();
        super.onHitBlock(blockHitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if(itemStack != null && entityHitResult.getEntity() instanceof LivingEntity){
            int power = itemStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.FABRIAL_POWER);
            boolean charge = itemStack.getOrCreateTag().getBoolean(NBTHelper.NBTKeys.FABRIAL_CHARGE);
            String spren = itemStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN);

            int stormlight = itemStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
            int stormlightNeeded = FabrialEffects.targetEntity((LivingEntity) entityHitResult.getEntity(), this.getLevel(), power, charge, this.getDeltaMovement(), spren, false, true);

            if(stormlight >= stormlightNeeded){
                FabrialEffects.targetEntity((LivingEntity) entityHitResult.getEntity(), this.getLevel(), power, charge, this.getDeltaMovement(), spren, false, true);
                itemStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight - stormlightNeeded);
            }
        }
        this.drop(this.getLevel(), this.getX(), this.getY(), this.getZ());
        this.discard();
        super.onHitEntity(entityHitResult);
    }

}
