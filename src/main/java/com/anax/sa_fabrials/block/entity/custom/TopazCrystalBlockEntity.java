package com.anax.sa_fabrials.block.entity.custom;
import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TopazCrystalBlockEntity extends CrystalBlockEntity{
    public TopazCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.TOPAZ_CRYSTAL_BLOCK_ENTITY.get(), blockPos, blockState, 10000, 10000, 640000);
    }

    @Override
    Block getUserBlock() {
        return ModBlocks.TOPAZ_CRYSTAL_BLOCK.get();
    }
}
