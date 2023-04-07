package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class FabrialEffects {
    public static void explode(Level level, Entity entity, float power){
        if(!level.isClientSide()) {
            level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), power, Explosion.BlockInteraction.BREAK);
        }
    }

    public static void lightning(Level level, Entity entity, Vec3 position, float power){
        if(!level.isClientSide()) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setPos(position);
            level.addFreshEntity(lightningBolt);
        }
    }

    public static void setFire(Level level, Entity entity, BlockPos position, float power){
        if(!level.isClientSide()){
            if(level.getBlockState(position).is(Blocks.AIR)) {
                level.setBlock(position, Blocks.FIRE.defaultBlockState(), 3);
            }
        }
    }




}
