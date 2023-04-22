package com.anax.sa_fabrials.entity.custom;

import com.anax.sa_fabrials.entity.ModEntityTypes;
import com.anax.sa_fabrials.item.ModItems;
import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
        super(ModEntityTypes.THROWN_FABRIAL.get(), entity, level);
    }

    public ThrownFabrial(Level level, LivingEntity entity, ItemStack itemStack){
        super(ModEntityTypes.THROWN_FABRIAL.get(), entity, level);
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
        if(itemStack == null){return ModItems.THROWABLE_SMOKESTONE_FABRIAL.get();}
        return itemStack.getItem();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if(itemStack != null){
            if(itemStack.getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setFire(level, blockHitResult.getBlockPos(),blockHitResult.getDirection(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
            if(itemStack.getOrCreateTag().getString("spren").equals("lightning")){FabrialEffects.lightning(level, this.position(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
            if(itemStack.getOrCreateTag().getString("spren").equals("explosion")){FabrialEffects.explode(level, this.position(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
            drop(level, this.getX(), this.getY(), this.getZ());
        }
        this.discard();
        super.onHitBlock(blockHitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if(itemStack != null){
            if(itemStack.getOrCreateTag().getString("spren").equals("fire")){
                FabrialEffects.setEntityFire(entityHitResult.getEntity(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));
                drop(level, this.getX(), this.getY(), this.getZ());
                this.discard();
            }
            if(itemStack.getOrCreateTag().getString("spren").equals("wind")){
                FabrialEffects.launchEntity(entityHitResult.getEntity(), new Vec3(0, 1, 0), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));
                drop(level, this.getX(), this.getY(), this.getZ());
                this.discard();
            }
            if(itemStack.getOrCreateTag().getString("spren").equals("health")){
                FabrialEffects.health((LivingEntity) entityHitResult.getEntity(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));
                drop(level, this.getX(), this.getY(), this.getZ());
                this.discard();
            }

        }
        super.onHitEntity(entityHitResult);
    }

}
