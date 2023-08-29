package com.anax.sa_fabrials.entity;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.entity.custom.SmokeCloud;
import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import com.anax.sa_fabrials.item.SAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class SAEntityTypes {

    public static final DeferredRegister<EntityType<?>>
            ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SAFabrials.MOD_ID);


    public static final RegistryObject<EntityType<ThrownFabrial>> THROWN_FABRIAL =
            ENTITIES.register("thrown_fabrial",
            () -> EntityType.Builder.<ThrownFabrial>of(ThrownFabrial::new,
                    MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(10)
                    .build("thrown_fabrial"));

    public static final RegistryObject<EntityType<SmokeCloud>> SMOKE_CLOUD =
            ENTITIES.register("smoke_cloud",
                    () -> EntityType.Builder.of(SmokeCloud::new,
                            MobCategory.MISC)
                            .sized(0.1f, 0.1f)
                            .build(SAFabrials.MOD_ID + ":smoke_cloud")
            );


    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
