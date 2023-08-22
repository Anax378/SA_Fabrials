package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PulseFabrialItem extends AbstractFabrialItem {
    public PulseFabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
        super(properties, capacity, maxExtract, maxReceive, initialStormlight);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        int power = context.getItemInHand().getOrCreateTag().getInt("power");
        boolean charge = context.getItemInHand().getOrCreateTag().getBoolean("is_attractor");
        String spren = context.getItemInHand().getOrCreateTag().getString("spren");
        Vec3 direction = context.getPlayer() == null ? null : context.getPlayer().getLookAngle();
        Vec3 pos = new Vec3(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ());

        int stormlight = context.getItemInHand().getOrCreateTag().getInt("stormlight");

        int stormlightNeeded = FabrialEffects.targetBlock(pos, context.getLevel(), power, charge, direction == null ? Vec3.directionFromRotation(0, context.getRotation()) : direction, context.getClickedFace(), spren, true);
        if(stormlight >= stormlightNeeded){
            FabrialEffects.targetBlock(pos, context.getLevel(), power, charge, direction == null ? Vec3.directionFromRotation(0, context.getRotation()) : direction, context.getClickedFace(), spren, false);
            context.getItemInHand().getOrCreateTag().putInt("stormlight", stormlight-stormlightNeeded);
        }

        return super.useOn(context);


    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){
        int power = player.getItemInHand(interactionHand).getOrCreateTag().getInt("power");
        boolean charge = player.getItemInHand(interactionHand).getOrCreateTag().getBoolean("is_attractor");
        String spren = player.getItemInHand(interactionHand).getOrCreateTag().getString("spren");

        int stormlight = player.getItemInHand(interactionHand).getOrCreateTag().getInt("stormlight");
        int stormlightNeeded = FabrialEffects.targetEntity(player, level, power, charge, player.getLookAngle(), spren, true, true);
        if(stormlight >= stormlightNeeded){
            FabrialEffects.targetEntity(player, level, power, charge, player.getLookAngle(), spren, true, false);
            player.getItemInHand(interactionHand).getOrCreateTag().putInt("stormlight", stormlight - stormlightNeeded);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        int power = player.getItemInHand(interactionHand).getOrCreateTag().getInt("power");
        boolean charge = player.getItemInHand(interactionHand).getOrCreateTag().getBoolean("is_attractor");
        String spren = player.getItemInHand(interactionHand).getOrCreateTag().getString("spren");

        int stormlight  = player.getItemInHand(interactionHand).getOrCreateTag().getInt("stormlight");
        int stormlightNeeded = FabrialEffects.targetEntity(livingEntity, player.getLevel(), power, charge, player.getLookAngle(), spren, false, true);
        if(stormlight >= stormlightNeeded){
            FabrialEffects.targetEntity(livingEntity, player.getLevel(), power, charge, player.getLookAngle(), spren, false, false);
            player.getItemInHand(interactionHand).getOrCreateTag().putInt("stormlight", stormlight - stormlightNeeded);
        }

        return InteractionResult.CONSUME;
    }
}
