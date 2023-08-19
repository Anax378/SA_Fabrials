package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

public class FabrialEffects {
    public static void explode(Level level,Vec3 position, float power, boolean charge){
        if(!level.isClientSide()) {
            level.explode(null, position.x, position.y, position.z, power, Explosion.BlockInteraction.BREAK);
        }
    }

    public static void lightning(Level level, Vec3 position, float power, boolean charge){
        if(!level.isClientSide()) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setPos(position);
            level.addFreshEntity(lightningBolt);
        }
    }

    public static void setFire(Level level, BlockPos position, Direction direction, float power, boolean charge){
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

    public static void setEntityFire(Entity entity, float power, boolean charge){
        if(charge){
            if (entity instanceof Creeper){((Creeper)entity).ignite();return;}
            entity.setRemainingFireTicks(Math.round(power*20));
        }else{
            entity.clearFire();
        }
    }

    public static void launchEntity(Entity entity, Vec3 direction, float power, boolean charge){
        direction = direction.normalize().scale(power);
        if(!charge){direction = direction.reverse();}
        entity.lerpMotion(direction.x, direction.y, direction.z);
    }

    public static void health(LivingEntity entity, float power, boolean charge){
        if(!charge){
            entity.hurt(DamageSource.MAGIC, power*2);
        }
        if(charge){
            entity.heal(power*2);
        }
    }
    public static SprenManifestation WIND_MANIFESTATION = new SprenManifestation(){
        @Override
        public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {
            direction = direction.normalize().scale(power);
            if(!charge){direction = direction.reverse();}
            entity.lerpMotion(direction.x, direction.y, direction.z);
        }

        @Override
        public String getSprenName() {
            return "wind";
        }

        @Override
        public boolean isUseOnSelf() {
            return true;
        }
    };
    public static SprenManifestation HEALTH_MANIFESTATION = new SprenManifestation(){
        @Override
        public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {
            if(charge){
                entity.heal(power*2);
            }
            else{
                entity.hurt(DamageSource.MAGIC, power*2);
            }
        }
        @Override
        public String getSprenName() {
            return "health";
        }

        @Override
        public boolean isUseOnSelf() {
            return true;
        }
    };
    public static SprenManifestation FIRE_MANIFESTATION = new SprenManifestation(){
        @Override
        public void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side) {
            BlockPos position = new BlockPos(pos);
            if(level.getBlockState(position).is(Blocks.TNT)){
                TntBlock.explode(level, position);
                level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
                return;
            }
            BlockState blockstate = level.getBlockState(position);
            if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
                BlockPos blockpos1 = position.relative(side);
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, side)) {
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                    level.setBlock(blockpos1, blockstate1, 11);
                }
            } else {
                level.setBlock(position, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            }
        }

        @Override
        public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {
            if(charge){
                if (entity instanceof Creeper){((Creeper)entity).ignite();return;}
                entity.setRemainingFireTicks(Math.round(power*20));
            }else{
                entity.clearFire();
            }
        }

        @Override
        public String getSprenName() {
            return "fire";
        }

        @Override
        public boolean isUseOnSelf() {
            return false;
        }
    };
    public static SprenManifestation LIGHTNING_MANIFESTATION = new SprenManifestation(){
        @Override
        public void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side) {
            if(!level.isClientSide()) {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setPos(new Vec3(pos.x(), pos.y(), pos.z()));
                level.addFreshEntity(lightningBolt);
            }
        }

        @Override
        public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {
            this.targetBlock(entity.position(), level, power, charge, direction, Direction.UP);
        }

        @Override
        public String getSprenName() {
            return "lightning";
        }

        @Override
        public boolean isUseOnSelf() {
            return false;
        }
    };
    public static SprenManifestation EXPLOSION_MANIFESTATION = new SprenManifestation(){
        @Override
        public void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side) {
            if(!level.isClientSide()) {
                level.explode(null, pos.x(), pos.y(), pos.z(), power, Explosion.BlockInteraction.BREAK);
            }
        }

        @Override
        public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {
            this.targetBlock(entity.position(), level, power, charge, direction, Direction.UP);
        }

        @Override
        public String getSprenName() {
            return "explosion";
        }

        @Override
        public boolean isUseOnSelf() {
            return false;
        }
    };

    public static HashMap<String, SprenManifestation> sprenManifestationsMap = new HashMap<>();

    static {
        sprenManifestationsMap.put(WIND_MANIFESTATION.getSprenName(), WIND_MANIFESTATION);
        sprenManifestationsMap.put(HEALTH_MANIFESTATION.getSprenName(), HEALTH_MANIFESTATION);
        sprenManifestationsMap.put(FIRE_MANIFESTATION.getSprenName(), FIRE_MANIFESTATION);
        sprenManifestationsMap.put(LIGHTNING_MANIFESTATION.getSprenName(), LIGHTNING_MANIFESTATION);
        sprenManifestationsMap.put(EXPLOSION_MANIFESTATION.getSprenName(), EXPLOSION_MANIFESTATION);
    }

    public static void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, String spren){
        if(sprenManifestationsMap.containsKey(spren)){
            sprenManifestationsMap.get(spren).targetBlock(pos, level, power, charge, direction, side);
        }
    }

    public static void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction, String spren, boolean isUsingOnSelf){
        if(sprenManifestationsMap.containsKey(spren)){
            if(isUsingOnSelf && !sprenManifestationsMap.get(spren).isUseOnSelf()){return;}
            sprenManifestationsMap.get(spren).targetEntity(entity, level, power, charge, direction);
        }
    }






}
