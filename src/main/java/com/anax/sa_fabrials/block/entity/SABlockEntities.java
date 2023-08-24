package com.anax.sa_fabrials.block.entity;
import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.block.SABlocks;
import com.anax.sa_fabrials.block.entity.custom.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SABlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SAFabrials.MOD_ID);

    public static final RegistryObject<BlockEntityType<TopazCrystalBlockEntity>> TOPAZ_CRYSTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("topaz_crystal_block_entity", () ->
                    BlockEntityType.Builder.of(TopazCrystalBlockEntity::new,
                            SABlocks.TOPAZ_CRYSTAL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<StormlightPipeBlockEntity>> STORMLIGHT_PIPE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("stormlight_block_entity", () ->
                    BlockEntityType.Builder.of(StormlightPipeBlockEntity::new,
                            SABlocks.STORMLIGHT_PIPE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SprenCatchingStationBlockEntity>> SPREN_CATCHING_STATION_ENTITY =
            BLOCK_ENTITIES.register("spren_catching_station_block_entity", () ->
                    BlockEntityType.Builder.of(SprenCatchingStationBlockEntity::new,
                            SABlocks.SPREN_CATCHING_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<ArtifabriansStationBlockEntity>> ARTIFABRIANS_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("artifabrians_station_block_entity", () ->
                    BlockEntityType.Builder.of(ArtifabriansStationBlockEntity::new,
                            SABlocks.ARTIFABRIANS_STATION_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ConjoinedRedstoneLampBlockEntity>> CONJOINED_REDSTONE_LAMP_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("conjoined_redstone_lamp_block_entity", () ->
                    BlockEntityType.Builder.of(ConjoinedRedstoneLampBlockEntity::new,
                            SABlocks.ARTIFABRIANS_STATION_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
