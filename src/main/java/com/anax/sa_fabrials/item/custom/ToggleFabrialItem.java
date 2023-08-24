package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.util.NBTHelper;
import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class ToggleFabrialItem extends AbstractFabrialItem{
    public ToggleFabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
        super(properties, capacity, maxExtract, maxReceive, initialStormlight);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        boolean isOn = pPlayer.getItemInHand(pUsedHand).getOrCreateTag().getBoolean("is_on");
        pPlayer.getItemInHand(pUsedHand).getOrCreateTag().putBoolean("is_on", !isOn);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pStack.getOrCreateTag().getBoolean("is_on") && pEntity instanceof LivingEntity){
            int stormlight = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.STORED_STORMLIGHT);
            int power = pStack.getOrCreateTag().getInt(NBTHelper.NBTKeys.FABRIAL_POWER);
            boolean charge = pStack.getOrCreateTag().getBoolean(NBTHelper.NBTKeys.FABRIAL_CHARGE);
            String spren = pStack.getOrCreateTag().getString(NBTHelper.NBTKeys.CAPTURED_SPREN);
            if(FabrialEffects.isUseOnSelf(spren, charge)){
                int stormlightNeeded = FabrialEffects.targetEntity((LivingEntity)pEntity, pLevel, power, charge, pEntity.getLookAngle(), spren, true, true);
                if(stormlight >= stormlightNeeded){
                    FabrialEffects.targetEntity((LivingEntity)pEntity, pLevel, power, charge, pEntity.getLookAngle(), spren, true, false);
                    pStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight-stormlightNeeded);
                }
            }
            else{
                Vec3 look = pEntity.getLookAngle();
                Vec3 start = pEntity.getEyePosition(1f);
                Vec3 end = pEntity.getEyePosition().add(look.normalize().scale(5));

                AABB bb = pEntity.getBoundingBox()
                        .expandTowards(look.x * 5, look.y * 5, look.z * 5)
                        .expandTowards(1, 1, 1);

                EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(pLevel, pEntity, start, end, bb, (val) -> true);

                if(entityHitResult == null || entityHitResult.getType() == HitResult.Type.MISS || !(entityHitResult.getEntity() instanceof LivingEntity)){
                    BlockHitResult blockHitResult = pLevel.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
                    if(blockHitResult.getType() == HitResult.Type.BLOCK){
                        BlockPos pos = blockHitResult.getBlockPos();
                        Vec3 position = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                        int stormlightNeeded = FabrialEffects.targetBlock(position, pLevel, power, charge, pEntity.getLookAngle(), blockHitResult.getDirection(), spren, true);
                        if(stormlight >= stormlightNeeded){
                            FabrialEffects.targetBlock(position, pLevel, power, charge, pEntity.getLookAngle(), blockHitResult.getDirection(), spren, false);
                            pStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight-stormlightNeeded);
                        }
                    }
                }else{
                    int stormlightNeeded = FabrialEffects.targetEntity((LivingEntity)entityHitResult.getEntity(), pLevel, power, charge, pEntity.getLookAngle(), spren, false, true);
                    if(stormlight >= stormlightNeeded){
                        FabrialEffects.targetEntity((LivingEntity)entityHitResult.getEntity(), pLevel, power, charge, pEntity.getLookAngle(), spren, false, false);
                        pStack.getOrCreateTag().putInt(NBTHelper.NBTKeys.STORED_STORMLIGHT, stormlight-stormlightNeeded);
                    }
                }

            }


        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getOrCreateTag().getBoolean("is_on");
    }
}
