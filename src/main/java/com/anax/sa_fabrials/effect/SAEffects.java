package com.anax.sa_fabrials.effect;

import com.anax.sa_fabrials.SAFabrials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SAEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SAFabrials.MOD_ID);

    public static final RegistryObject<GravityMobEffect> GRAVITY = MOB_EFFECTS.register("gravity",
            () -> new GravityMobEffect(MobEffectCategory.NEUTRAL, 46874655));
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
