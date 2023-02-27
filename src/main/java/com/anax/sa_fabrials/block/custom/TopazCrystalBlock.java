package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.entity.custom.CrystalBlockEntity;
import com.anax.sa_fabrials.block.entity.custom.TopazCrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;

public class TopazCrystalBlock extends CrystalBlock{


    public TopazCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    BlockEntity getNewBlockENtity(BlockPos blockPos, BlockState blockState) {
        return new TopazCrystalBlockEntity(blockPos, blockState);
    }

    @Override
    boolean isMyType(BlockEntity entity) {
        System.out.println("isMyType: " + (entity instanceof TopazCrystalBlockEntity));
        return entity instanceof TopazCrystalBlockEntity;
    }

    @Override
    void openGui(ServerPlayer pPlayer, BlockEntity entity ,BlockPos pPos) {
        NetworkHooks.openGui(pPlayer,(TopazCrystalBlockEntity) entity, pPos);
    }

    @Override
    <T extends BlockEntity> BlockEntityTicker<T> buildCreateTickHelper(BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TOPAZ_CRYSTAL_BLOCK_ENTITY.get(), TopazCrystalBlockEntity::tick);
    }
}
