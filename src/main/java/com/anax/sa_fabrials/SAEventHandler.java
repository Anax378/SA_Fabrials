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
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
    }
}
