package com.anax.sa_fabrials.entity;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>>
            ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SAFabrials.MOD_ID);


    public static final RegistryObject<EntityType<ThrownFabrial>> THROWN_FABRIAL =
            ENTITIES.register("thrown_fabrial",
            () -> EntityType.Builder.<ThrownFabrial>of(ThrownFabrial::new,
                    MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(10)
                    .build("thrown_fabrial"));


    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
