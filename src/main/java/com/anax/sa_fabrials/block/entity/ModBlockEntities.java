package com.anax.sa_fabrials.block.entity;
import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.custom.StormlightPipeBlockEntity;
import com.anax.sa_fabrials.block.entity.custom.TopazCrystalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SAFabrials.MOD_ID);

    public static final RegistryObject<BlockEntityType<TopazCrystalBlockEntity>> TOPAZ_CRYSTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("topaz_crystal_block_entity", () ->
                    BlockEntityType.Builder.of(TopazCrystalBlockEntity::new,
                            ModBlocks.TOPAZ_CRYSTAL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<StormlightPipeBlockEntity>> STORMLIGHT_PIPE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("stormlight_block_entity", () ->
                    BlockEntityType.Builder.of(StormlightPipeBlockEntity::new,
                            ModBlocks.STORMLIGHT_PIPE_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
