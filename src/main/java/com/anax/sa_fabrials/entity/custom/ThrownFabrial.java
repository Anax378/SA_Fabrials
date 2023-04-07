package com.anax.sa_fabrials.entity.custom;

import com.anax.sa_fabrials.entity.ModEntityTypes;
import com.anax.sa_fabrials.item.ModItems;
import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import static net.minecraft.world.Containers.dropItemStack;

public class ThrownFabrial extends ThrowableItemProjectile {
    ItemStack itemStack;
    public ThrownFabrial(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        init(ModItems.THROWABLE_FABRIAL.get().getDefaultInstance());
    }

    public ThrownFabrial(Level level, LivingEntity entity){
        super(ModEntityTypes.THROWN_FABRIAL.get(), entity, level);
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

    public void init(ItemStack stack){
        this.itemStack = stack;
    }

    public void drop(Level level, double x, double y, double z){
        dropItemStack(level, x, y, z, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THROWABLE_FABRIAL.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if(itemStack.getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setFire(level, this, this.getOnPos(), 1);}
        if(itemStack.getOrCreateTag().getString("spren").equals("lightning")){FabrialEffects.lightning(level, this, this.position(), 1);}
        if(itemStack.getOrCreateTag().getString("spren").equals("explosion")){FabrialEffects.explode(level, this, 4);}
        drop(level, this.getX(), this.getY(), this.getZ());
        this.discard();
        super.onHitBlock(blockHitResult);
    }
}
