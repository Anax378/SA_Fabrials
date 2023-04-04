package com.anax.sa_fabrials.entity.custom;

import com.anax.sa_fabrials.entity.ModEntityTypes;
import com.anax.sa_fabrials.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import static net.minecraft.world.Containers.dropItemStack;

public class ThrownFabrial extends ThrowableItemProjectile {
    ItemStack itemStack;
    public ThrownFabrial(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        init(ModItems.THROWABLE_FABRIAL.get().getDefaultInstance());
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
    public void playerTouch(Player player) {
        drop(player.getLevel(), player.getX(), player.getY(), player.getZ());
        super.playerTouch(player);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THROWABLE_FABRIAL.get();
    }
}
