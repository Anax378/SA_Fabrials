package com.anax.sa_fabrials.block.entity.custom;
import com.anax.sa_fabrials.block.SABlocks;
import com.anax.sa_fabrials.block.entity.SABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TopazCrystalBlockEntity extends CrystalBlockEntity{
    public TopazCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SABlockEntities.TOPAZ_CRYSTAL_BLOCK_ENTITY.get(), blockPos, blockState, 10000, 10000, 640000);
    }

    @Override
    Block getUserBlock() {
        return SABlocks.TOPAZ_CRYSTAL_BLOCK.get();
    }

    @Override
    Item getItem() {
        return SABlocks.TOPAZ_CRYSTAL_BLOCK_ITEM.get();
    }

}
