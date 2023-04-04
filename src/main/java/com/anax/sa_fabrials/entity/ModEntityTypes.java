package com.anax.sa_fabrials.entity;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SAFabrials.MOD_ID);

    public static final RegistryObject<EntityType<ThrownFabrial>> THROWN_FABRIAL = ENTITY_TYPES.register("thrown_fabrial",  () -> EntityType.Builder.<ThrownFabrial>of((a, b) -> {return new ThrownFabrial(a, b);}, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .setShouldReceiveVelocityUpdates(true)
                            .setUpdateInterval(1)
                            .clientTrackingRange(4)
                            .build("thrown_fabrial"));


    public static void register(IEventBus eventBus){
        System.out.println("-----------------------------------------");
        System.out.println("isNull?: " + (THROWN_FABRIAL == null));
        System.out.println("ENTITY_TYPES : " + ENTITY_TYPES);
        System.out.println("ModId: " + SAFabrials.MOD_ID);
        eventBus.register(ENTITY_TYPES);
    }
}
