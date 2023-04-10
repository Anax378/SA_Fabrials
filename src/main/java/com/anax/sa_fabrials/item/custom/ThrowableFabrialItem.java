package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

public class ThrowableFabrialItem extends AbstractFabrialItem {
    public ThrowableFabrialItem(Properties properties, int capacity, int maxExtract,int maxReceive, int initialStormlight) {
        super(properties, capacity, maxExtract, maxReceive, initialStormlight);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if(!level.isClientSide()){
            ThrownFabrial thrownFabrial = new ThrownFabrial(level, player, itemStack.copy());
            thrownFabrial.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownFabrial);
        }
        player.setItemInHand(interactionHand, ItemStack.EMPTY);
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }


}
