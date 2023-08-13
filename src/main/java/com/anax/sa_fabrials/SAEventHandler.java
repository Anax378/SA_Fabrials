package com.anax.sa_fabrials;

import com.anax.sa_fabrials.item.SAItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SAEventHandler {
/*

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(event.getAmount() <= 0){return;}
        for(ItemStack stack : entity.getArmorSlots()){
            if(stack.is(SAItems.SHARDPLATE_CHESTPLATE.get())){
                if(SAItems.SHARDPLATE_CHESTPLATE.get().caAbsorbAll(Math.round(event.getAmount()), stack)){
                    event.setCanceled(true);
                }
            }
        }

    }
*/
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        if(event.getAmount() <= 0){return;}
        for(ItemStack stack : entity.getArmorSlots()){
            if(stack.is(SAItems.SHARDPLATE_CHESTPLATE.get())){
                float damage = SAItems.SHARDPLATE_CHESTPLATE.get().damageAfterAbsorption(Math.round(event.getAmount()), stack);
                event.setAmount(damage);
            }
        }
    }
}
