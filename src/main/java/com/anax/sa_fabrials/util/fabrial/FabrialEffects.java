package com.anax.sa_fabrials.util.fabrial;

import com.anax.sa_fabrials.effect.SAEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.commands.arguments.EntityArgument.getEntities;

public class FabrialEffects {
    public static SprenManifestation WIND_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetEntity(Entity entity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
            if(!(entity instanceof LivingEntity)){return 0;}
            if(!simulate){
                direction = direction.normalize().scale(power);
                if(!charge){direction = direction.reverse();}
                entity.lerpMotion(direction.x, direction.y, direction.z);
            }
            return 800*power;
        }

        @Override
        public String getSprenName() {
            return "wind";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return true;
        }
    };
    public static SprenManifestation HEALTH_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetEntity(Entity pentity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
        if(!(pentity instanceof LivingEntity)){return 0;}
        LivingEntity entity = (LivingEntity)pentity;

        if (charge) {
            if(entity.getHealth() >= entity.getMaxHealth()){return 0;}
            if(!simulate){entity.heal(power * 2);}
            return 1000*power;
        }
        else {
            if(entity.isDamageSourceBlocked(DamageSource.MAGIC) || entity.isInvulnerable()){
                return 0;
            }
            else{
                if (!simulate) {
                    entity.hurt(DamageSource.MAGIC, power * 2);
                }
                return 1000*power;
            }

            }
        }
        @Override
        public String getSprenName() {
            return "health";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return charge;
        }
    };
    public static SprenManifestation FIRE_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, boolean simulate) {
            BlockPos position = new BlockPos(pos);
            if(level.getBlockState(position).is(Blocks.TNT)){
                if(!simulate) {
                    TntBlock.explode(level, position);
                    level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
                }
                return 150;
            }
            BlockState blockstate = level.getBlockState(position);
            if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
                BlockPos blockpos1 = position.relative(side);
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, side)) {
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                    if(!simulate) {level.setBlock(blockpos1, blockstate1, 11);}
                    return 150;
                }
                else{
                    return 0;
                }
            } else {
                if(level.getBlockState(position).getValue(BlockStateProperties.LIT)){
                    return 0;
                }
                if(!simulate){
                    level.setBlock(position, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                }
                return 150;
            }
        }

        @Override
        public int targetEntity(Entity entity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
            if(!(entity instanceof LivingEntity)){return 0;}
            if(charge){
                if (entity instanceof Creeper){
                    if(!simulate){
                        ((Creeper)entity).ignite();
                    }
                    return 150;
                }
                if(!entity.isOnFire()) {
                    if (!simulate) {
                        entity.setRemainingFireTicks(Math.round(power * 20));
                    }
                    return 150 + 20*power;
                }else{
                    return 0;
                }
            }else{
                if(!simulate){
                    entity.clearFire();
                }
            }
            return 150;
        }

        @Override
        public String getSprenName() {
            return "fire";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return false;
        }
    };
    public static SprenManifestation LIGHTNING_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, boolean simulate) {
            if(charge){
                if (!level.isClientSide()) {
                    if(!simulate) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        lightningBolt.setPos(new Vec3(pos.x(), pos.y(), pos.z()));
                        level.addFreshEntity(lightningBolt);
                    }
                    return 2000;
                }
            }
            return 0;
        }

        @Override
        public int targetEntity(Entity entity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
            if(charge){return this.targetBlock(entity.position(), level, power, charge, direction, Direction.UP, simulate);}
            else{
                if(entity instanceof LightningBolt){
                    return 500*power;
                }
            }
            return 0;

        }

        @Override
        public String getSprenName() {
            return "lightning";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return false;
        }
    };
    public static SprenManifestation EXPLOSION_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, boolean simulate) {
            if(!simulate){
                if(!level.isClientSide()) {
                    level.explode(null, pos.x(), pos.y(), pos.z(), power, Explosion.BlockInteraction.BREAK);
                }
            }
            return 500*power;
        }

        @Override
        public int targetEntity(Entity entity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
            if(!(entity instanceof LivingEntity)){return 0;}
            return this.targetBlock(entity.position(), level, power, charge, direction, Direction.UP, simulate);
        }

        @Override
        public String getSprenName() {
            return "explosion";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return false;
        }
    };

    public static SprenManifestation GRAVITY_MANIFESTATION = new SprenManifestation(){
        @Override
        public int targetEntity(Entity pEntity, Level level, int power, boolean charge, Vec3 direction, boolean simulate) {
            if(pEntity instanceof LivingEntity){
                if(((LivingEntity) pEntity).hasEffect(SAEffects.GRAVITY.get()) && ((LivingEntity) pEntity).getEffect(SAEffects.GRAVITY.get()).getDuration() > 5){return 0;}
                    if(!simulate){
                        ((LivingEntity) pEntity).addEffect(new MobEffectInstance(SAEffects.GRAVITY.get(), 20, (power+1)*(charge?1:-1), true, false, true));
                    }
                return power;
            }
            return 0;
        }
        @Override
        public String getSprenName() {
            return "gravity";
        }
        @Override
        public boolean isUseOnSelf(boolean charge) {
            return true;
        }
    };

    public static SprenManifestation ICE_MANIFESTATION = new SprenManifestation(){
        @Override
        public String getSprenName() {
            return "ice";
        }

        @Override
        public boolean isUseOnSelf(boolean charge) {
            return false;
        }

        @Override
        public int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, boolean simulate) {
            if(charge){
                BlockPos placePos = new BlockPos(pos);
                placePos = placePos.relative(side);
                if(level.getBlockState(placePos).getMaterial().isReplaceable()){
                    if(!simulate){
                        level.setBlock(placePos, Blocks.ICE.defaultBlockState(), 11);
                    }
                    return 500;
                }
            }
            return 0;
        }
    };

    public static HashMap<String, SprenManifestation> sprenManifestationsMap = new HashMap<>();

    static {
        sprenManifestationsMap.put(WIND_MANIFESTATION.getSprenName(), WIND_MANIFESTATION);
        sprenManifestationsMap.put(HEALTH_MANIFESTATION.getSprenName(), HEALTH_MANIFESTATION);
        sprenManifestationsMap.put(FIRE_MANIFESTATION.getSprenName(), FIRE_MANIFESTATION);
        sprenManifestationsMap.put(LIGHTNING_MANIFESTATION.getSprenName(), LIGHTNING_MANIFESTATION);
        sprenManifestationsMap.put(EXPLOSION_MANIFESTATION.getSprenName(), EXPLOSION_MANIFESTATION);
        sprenManifestationsMap.put(GRAVITY_MANIFESTATION.getSprenName(), GRAVITY_MANIFESTATION);
        sprenManifestationsMap.put(ICE_MANIFESTATION.getSprenName(), ICE_MANIFESTATION);
    }

    public static int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, String spren, boolean simulate){
        if(sprenManifestationsMap.containsKey(spren)){
            return sprenManifestationsMap.get(spren).targetBlock(pos, level, power, charge, direction, side, simulate);
        }
        return 0;
    }

    public static int targetEntity(Entity entity, Level level, int power, boolean charge, Vec3 direction, String spren, boolean isUsingOnSelf, boolean simulate){
        if(sprenManifestationsMap.containsKey(spren)){
            if(isUsingOnSelf && !sprenManifestationsMap.get(spren).isUseOnSelf(charge)){return 0;}
            return sprenManifestationsMap.get(spren).targetEntity(entity, level, power, charge, direction, simulate);
        }
        return 0;
    }

    public static boolean isUseOnSelf(String spren, boolean charge){
        if(sprenManifestationsMap.containsKey(spren)){
            return sprenManifestationsMap.get(spren).isUseOnSelf(charge);
        }
        return false;
    }






}
