package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class FabrialItem extends AbstractFabrialItem {
    public FabrialItem(Properties properties, int capacity, int maxExtract, int maxReceive, int initialStormlight) {
        super(properties, capacity, maxExtract, maxReceive, initialStormlight);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setFire(context.getLevel(),context.getClickedPos(),context.getClickedFace(), 1);}
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("lightning")){FabrialEffects.lightning(context.getLevel(), context.getClickLocation(), 1);}
        if(context.getItemInHand().getOrCreateTag().getString("spren").equals("explosion")){FabrialEffects.explode(context.getLevel(), context.getClickLocation(), 4);}
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if(itemStack.getOrCreateTag().getString("spren").equals("fire")){FabrialEffects.setEntityFire(livingEntity, 1);}
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
