package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class FabrialEffects {
    public static void explode(Level level,Vec3 position, float power){
        if(!level.isClientSide()) {
            level.explode(null, position.x, position.y, position.z, power, Explosion.BlockInteraction.BREAK);
        }
    }

    public static void lightning(Level level, Vec3 position, float power){
        if(!level.isClientSide()) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setPos(position);
            level.addFreshEntity(lightningBolt);
        }
    }

    public static void setFire(Level level, BlockPos position, Direction direction, float power){
        if(level.getBlockState(position).is(Blocks.TNT)){
            TntBlock.explode(level, position);
            level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
        BlockState blockstate = level.getBlockState(position);
        if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
            BlockPos blockpos1 = position.relative(direction);
            if (BaseFireBlock.canBePlacedAt(level, blockpos1, direction)) {
                BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                level.setBlock(blockpos1, blockstate1, 11);
            }
        } else {
            level.setBlock(position, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
        }

    }

    public static void setEntityFire(Entity entity, float power){
        if (entity instanceof Creeper){((Creeper)entity).ignite();return;}
        entity.setRemainingFireTicks(Math.round(power*20));
    }




}
