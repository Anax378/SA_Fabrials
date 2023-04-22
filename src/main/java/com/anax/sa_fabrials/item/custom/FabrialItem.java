package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;

public class FabrialItem extends AbstractFabrialItem {
    public FabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
        super(properties, capacity, maxExtract, maxReceive, initialStormlight);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setFire(context.getLevel(),context.getClickedPos(),context.getClickedFace(), context.getItemInHand().getOrCreateTag().getInt("power"), context.getItemInHand().getOrCreateTag().getBoolean("is_attractor"));}
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("lightning")){FabrialEffects.lightning(context.getLevel(), context.getClickLocation(), context.getItemInHand().getOrCreateTag().getInt("power"), context.getItemInHand().getOrCreateTag().getBoolean("is_attractor"));}
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("explosion")){FabrialEffects.explode(context.getLevel(), context.getClickLocation(), context.getItemInHand().getOrCreateTag().getInt("power"), context.getItemInHand().getOrCreateTag().getBoolean("is_attractor"));}
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){
        if(player.getItemInHand(interactionHand).getOrCreateTag().getString("spren").equals("wind")){
            FabrialEffects.launchEntity(player, player.getLookAngle(), player.getItemInHand(interactionHand).getOrCreateTag().getInt("power"), player.getItemInHand(interactionHand).getOrCreateTag().getBoolean("is_attractor"));
            player.getCooldowns().addCooldown(this, 5);
        }
        if(player.getItemInHand(interactionHand).getOrCreateTag().getString("spren").equals("health")){
            FabrialEffects.health(player, player.getItemInHand(interactionHand).getOrCreateTag().getInt("power"), player.getItemInHand(interactionHand).getOrCreateTag().getBoolean("is_attractor"));
            player.getCooldowns().addCooldown(this, 5);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if(itemStack.getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setEntityFire(livingEntity, itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
        if(itemStack.getOrCreateTag().getString("spren").equals("wind")){FabrialEffects.launchEntity(livingEntity, player.getLookAngle(), itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
        if(itemStack.getOrCreateTag().getString("spren").equals("health")){FabrialEffects.health(livingEntity, itemStack.getOrCreateTag().getInt("power"), itemStack.getOrCreateTag().getBoolean("is_attractor"));}
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
